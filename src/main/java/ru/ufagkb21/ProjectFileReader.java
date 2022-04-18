package ru.ufagkb21;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProjectFileReader {

    private String excelNameFileRead;
    private List<Person> persons = new ArrayList<Person>();
    private int number;
    private String lastName, firstName, dateOfBirth, passportNumber;
    private String dateResult = "";
    private String timeResult;
    private int numberReport, numberProduction;
    private Workbook workbook;
    private String dateCurrent = new SimpleDateFormat("yyyy").format(new Date());


    public ProjectFileReader(String excelNameFileRead) throws FileNotFoundException {
        this.excelNameFileRead = excelNameFileRead;
    }

    //проверить у файла расширение на соответсвие с *.xlsx

    /** читаем из excel документа данные */
    public List<Person> excelFileRead () {

        try (FileInputStream fileInputStream = new FileInputStream(excelNameFileRead)) {
            workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);


            // Проходим по всем строкам с листа sheet
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

                // порядковый номер - N (getIndexColumn(sheet, "N") - поиск индекса столбца
                Cell cellInt = sheet.getRow(i).getCell(getIndexColumn(sheet, "N"));
                number = (int)cellInt.getNumericCellValue();
                System.out.println("Пациент за номером: " + number);

                // Фамилия клиента - lastname
                lastName = getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "lastname"))).toUpperCase().trim();
                System.out.println("Фамилия клиента: " + lastName);

                // Имя клиента - name
                firstName = getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "name"))).toUpperCase().trim();
                System.out.println("зовут клиента: " + firstName);

                // Дата рождения - dateBirth
                String[]  dateOfBirthSplit = getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "dateBirth"))).split("\\D");
                if (dateOfBirthSplit[0].length() < 2)
                    dateOfBirthSplit[0] = "0" + dateOfBirthSplit[0];
                if (dateOfBirthSplit[1].length() < 2)
                    dateOfBirthSplit[1] = "0" + dateOfBirthSplit[1];
                if ((dateOfBirthSplit[2].length() == 2) && (Integer.parseInt(dateOfBirthSplit[2]) > 41))
                    dateOfBirthSplit[2] = "19" + dateOfBirthSplit[2];
                else if ((dateOfBirthSplit[2].length() == 2) && (Integer.parseInt(dateOfBirthSplit[2]) < 21))
                    dateOfBirthSplit[2] = "20" + dateOfBirthSplit[2];
                dateOfBirth = dateOfBirthSplit[0] + "." + dateOfBirthSplit[1] + "." + dateOfBirthSplit[2];
                System.out.println("Данные о дате рождения считаны - " + dateOfBirth);

                // Номер паспорта - passport
                passportNumber = getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "passport")));
                if ((passportNumber != null) && (passportNumber.length() != 9)) {
                    ColorPrint.cpRed.println("У пациента " + lastName + " Проверте данные паспорта, количество цифр не соответсвуют стандарту загранпаспорта"); //в log
                    passportNumber = null;
                }
                System.out.println("Данные о паспорте - " + passportNumber);


                // дата исследования - dateResult
                String[] dateResultSplitTest = getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "dateResult"))).split("\\D+");
                String[]dateResultSplit = new String[3];
                int numberI = 0;
                for (int j = 0; j < dateResultSplitTest.length; j ++) {
                    if (dateResultSplitTest[j].matches("^\\d{1,4}$")) {
                        dateResultSplit[numberI] = dateResultSplitTest[j];
                        numberI++;
                    }
                }
                if (dateResultSplit[0].length() < 2)
                    dateResultSplit[0] = "0" + dateResultSplit[0];
                if (dateResultSplit[1].length() < 2)
                    dateResultSplit[1] = "0" + dateResultSplit[1];
                if (((dateResultSplit[2] != dateCurrent) && (dateResultSplit[2].length() == 2) && dateResultSplit[2].matches("^2[1-3]$"))) {
                    dateResultSplit[2] = "20" + dateResultSplit[2];
                }
                if (!(dateResultSplit[2].equals(dateCurrent))) {
                    ColorPrint.cpRed.println("Проверь дату результата - год не соотвествует текущему " + dateCurrent); //в log
                }
                String testDateResult = "";
                if (!(dateResultSplit[0].equals("00") && !(dateResultSplit[1].equals("00")))) {
                    testDateResult = dateResultSplit[0] + "." + dateResultSplit[1] + "." + dateResultSplit[2];
                }
                boolean test = testDateResult.matches("^[0-3][0-9]\\.[0-1][1-9]\\.202[1-2]$");
                if (test) {
                    dateResult = testDateResult;
                } else {
                    System.out.println("Тест:  = " + test + " проблема с датой результата"); //в log
                }

                // Номер резульатат исследования - numberReport
                if (getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "numberReport"))) == null) {
                    numberReport = 0;
                } else {
                    numberReport = Integer.parseInt(getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "numberReport"))));
                }

                // Номер пробы исследования - numberProduction
                if (getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "numberProduction"))) == null) {
                    numberProduction = 0;
                } else {
                    numberProduction = Integer.parseInt(getCellText(sheet.getRow(i).getCell(getIndexColumn(sheet, "numberProduction"))));
                }
                ColorPrint.cpRed.println("Дата исследования: " + dateResult + " # Номер пробы: " + numberReport  + " # Номер постановки: " + numberProduction);

                // Создаем список людей
                persons.add(new Person(number, lastName, firstName, dateOfBirth, passportNumber, dateResult, numberReport, numberProduction));
            }

            ColorPrint.cpYellow.println("Данные с файла "+ excelNameFileRead + " считаны и список сформирован" + "\n------------------------------------------------------" );
        } catch (NullPointerException nullPointerException) {
            ColorPrint.cpGreen.println("Больше строк нет! (throw NullPointerException)");
        } catch (FileNotFoundException fileNotFoundException) {
            ColorPrint.cpRed.println("Файл для чтения не найден");
        }
        catch (IOException io) {
            io.printStackTrace();
        }
        return persons;
    }


    /** Give index Column */
    public int getIndexColumn (Sheet sheet, String nameColumn) {
        int indexColumn = 0;
        Row firstRow = sheet.getRow(0);
        String columnValue;
        try {
            for (int i = 0; i < firstRow.getLastCellNum(); i++) {
            columnValue = firstRow.getCell(i).getStringCellValue();
            if (columnValue.equals(nameColumn)) {
                indexColumn = i;
                break;
            }
        }
        } catch (NullPointerException npe) {
            ColorPrint.cpRed.println("Столбец с названием " + nameColumn + " не найден");
        }
        return indexColumn;
    }


    /** Getting a string format from a cell  */
    public static String getCellText (Cell cell) {
        String textCell = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        switch (cell.getCellType()) {
            case STRING:
                textCell = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    textCell = sdf.format(cell.getDateCellValue());
                } else {
                    textCell = String.valueOf((int) cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                textCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                textCell = null;
        }
        return textCell;
    }
}
