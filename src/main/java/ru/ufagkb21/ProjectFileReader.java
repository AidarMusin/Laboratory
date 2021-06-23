package ru.ufagkb21;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    ColoredPrinter cpRed = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.RED).build();
    ColoredPrinter cpYellow = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.YELLOW).build();




    public ProjectFileReader(String excelNameFileRead) {
        this.excelNameFileRead = excelNameFileRead;
    }

    //проверить у файла расширение на соответсвие с *.xlsx


    /** читаем из excel документа данные */
    public List<Person> excelFileRead () {

        try (FileInputStream fileInputStream = new FileInputStream(excelNameFileRead)) {

            workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            /**  идея - найти  по названию колонки индекс колонки и привязать по типу **/


            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

                Cell cellInt = sheet.getRow(i).getCell(0);
                number = (int)cellInt.getNumericCellValue();

                lastName = getCellText(sheet.getRow(i).getCell(1)).toUpperCase();
                firstName = getCellText(sheet.getRow(i).getCell(2)).toUpperCase();

                String[]  dateOfBirthSplit = getCellText(sheet.getRow(i).getCell(3)).split("\\D");
                if (dateOfBirthSplit[0].length() < 2)
                    dateOfBirthSplit[0] = "0" + dateOfBirthSplit[0];
                if (dateOfBirthSplit[1].length() < 2)
                    dateOfBirthSplit[1] = "0" + dateOfBirthSplit[1];
                if ((dateOfBirthSplit[2].length() == 2) && (Integer.parseInt(dateOfBirthSplit[2]) > 41))
                    dateOfBirthSplit[2] = "19" + dateOfBirthSplit[2];
                else if ((dateOfBirthSplit[2].length() == 2) && (Integer.parseInt(dateOfBirthSplit[2]) < 21))
                    dateOfBirthSplit[2] = "20" + dateOfBirthSplit[2];
                dateOfBirth = dateOfBirthSplit[0] + "." + dateOfBirthSplit[1] + "." + dateOfBirthSplit[2];

                passportNumber = getCellText(sheet.getRow(i).getCell(4));
                if ((passportNumber != null) && (passportNumber.length() != 9)) {
                    cpRed.println("У пациента " + lastName + " Проверте данные паспорта, количество цифр не соответсвуют стандарту загранпаспорта"); //в log
                    passportNumber = null;
                }

                // дата результата исследования
                String[] dateResultSplitTest = getCellText(sheet.getRow(i).getCell(5)).split("\\D+");
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
                    System.out.println("Проверь дату результата - год не соотвествует текущему " + dateCurrent); //в log
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


                // Номер резульатат исследования
                if (getCellText(sheet.getRow(i).getCell(7)) == null) {
                    numberReport = 0;
                } else {
                    numberReport = Integer.parseInt(getCellText(sheet.getRow(i).getCell(7)));
                }

                // Номер пробы исследования
                if (getCellText(sheet.getRow(i).getCell(8)) == null) {
                    numberProduction = 0;
                } else {
                    numberProduction = Integer.parseInt(getCellText(sheet.getRow(i).getCell(8)));
                }



                // Время выдачи результата - заглушка
                switch (numberProduction)  {
                    case 1 : timeResult = "15:10";
                        break;
                    case 2 : timeResult = "15:30";
                        break;
                    case 3 : timeResult = "15:50";
                        break;
                    case 4 : timeResult = "16:20";
                        break;
                    default: timeResult = "16:40";
                    break;
                }


                // Создаем список людей
                persons.add(new Person(number, lastName, firstName, dateOfBirth, passportNumber, dateResult, timeResult, numberReport, numberProduction));
            }
        } catch (NullPointerException npe) {
            System.out.println("Больше строк нет! (throw NullPointerException)");
        } catch (IOException io) {
            io.printStackTrace();
        }
        cpYellow.println("Данные с файла "+ excelNameFileRead + " считаны и список сформирован" + "\n------------------------------------------------------" );
        return persons;

    }

    /** Give index Column */
    public static int[] getIndexColumn () {
        return null;

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
