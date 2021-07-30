package ru.ufagkb21;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectFileWriter {
    private static int counterRedaction = 1;
    private List<Person> persons;
    private File fileExel;
    private File fileTxt;
    private String fileNameWriteExcel;
    private Date dateNow = new Date();
    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("-yyyy.MM.dd-HH.mm.ss");
    private String logoName = "gkb21.png";




    public ProjectFileWriter (List<Person> persons, String nameFileExcelWrite, String readyFileName) {
        this.persons = persons;
        this.fileExel = new File(nameFileExcelWrite);
        this.fileTxt = new File(readyFileName + formatForDateNow.format(dateNow) + ".txt");
        this.fileNameWriteExcel = readyFileName + formatForDateNow.format(dateNow) + ".xlsx";
    }


    /** Вставка полученных QR кодоав в форму */
    public void exelImagesInsert (Code code) {
        try (FileInputStream fileInputStream = new FileInputStream(fileExel)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            try (InputStream inputStream = new FileInputStream(code.getFileQrCode());
                 InputStream inputLogoStream = new FileInputStream(logoName)) {

                byte[] bytes = IOUtils.toByteArray(inputStream);  //Get the contents of an InputStream as a byte[].
                byte[] bytesLogo = IOUtils.toByteArray(inputLogoStream);  //Get the contents of an logoStream as a byte[].
                int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);  //Adds a picture to the workbook
                int pictureIdxLogo = workbook.addPicture(bytesLogo, Workbook.PICTURE_TYPE_PNG);  //Adds a picture to the workbook
                CreationHelper helper = workbook.getCreationHelper(); //Creates the top-level drawing patriarch.
                CreationHelper helperLogo = workbook.getCreationHelper(); //Creates the top-level drawing patriarch.
                Drawing drawing = sheet.createDrawingPatriarch();  //Create an anchor that is attached to the worksheet
                Drawing drawingLogo = sheet.createDrawingPatriarch();  //Create an anchor that is attached to the worksheet
                ClientAnchor anchor = helper.createClientAnchor(); //create an anchor with upper left cell _and_ bottom right cell
                ClientAnchor anchorLogo = helperLogo.createClientAnchor(); //create an anchor with upper left cell _and_ bottom right cell

                anchor.setCol1(code.getCoordinates(0)); //Column B 1
                anchor.setRow1(code.getCoordinates(1)); //Row 3 2
                anchor.setCol2(code.getCoordinates(2)); //Column C 2
                anchor.setRow2(code.getCoordinates(3)); //Row 7 6

                anchorLogo.setCol1((code.getCoordinates(0) + 2 )); //Column D 3
                anchorLogo.setRow1((code.getCoordinates(1) - 1 )); //Row 2 1
                anchorLogo.setCol2((code.getCoordinates(2) + 2 )); //Column E 4
                anchorLogo.setRow2((code.getCoordinates(3) - 4)); //Row 3 2

                Picture pict = drawing.createPicture(anchor, pictureIdx);  //Creates a picture
                Picture pictLogo = drawingLogo.createPicture(anchorLogo, pictureIdxLogo);  //Creates a picture

            } catch (IOException io) {
                io.printStackTrace();
            }
            FileOutputStream fos = new FileOutputStream(fileExel);
            workbook.write(fos);
            fos.close();
        } catch (IOException ioException) {
            ColorPrint.cpRed.println("Ошибка чтения файла " + fileExel + " Возможно объем данных слишком велик.");
        }
        ColorPrint.cpYellow.println("QR коды добавлены в Excel файл c именем " + fileExel.getName());
    }


    /** Запись в excel форму данные */
    public void excelFileWrite () {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileExel);

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheetOriginal = workbook.getSheetAt(0);
            List<Row> rowList = new ArrayList<>();
            for (Row row : sheetOriginal) {
                rowList.add(row);
            }
            ColorPrint.cpCyan.println("Количество людей в списке на исследование - " + persons.size());

            // Укладываем в соответствующие ячейки данные
            int columnNumber = 1;
            try {
                for (int i = 0; i < persons.size(); i++) {
                    rowList.get(0).getCell(columnNumber).setCellValue("Ufa City Clinical Hospital No.21 Clinical  laboratory");
                    rowList.get(1).getCell(columnNumber ).setCellValue(persons.get(i).getReport());
                    rowList.get(2).getCell(columnNumber + 1).setCellValue("Patient:");
                    rowList.get(3).getCell(columnNumber + 1).setCellValue(persons.get(i).getPatient());
                    rowList.get(4).getCell(columnNumber + 1).setCellValue(persons.get(i).getDateOfBirth());
                    rowList.get(5).getCell(columnNumber + 1).setCellValue(persons.get(i).getPassportNumber());
                    rowList.get(6).getCell(columnNumber ).setCellValue("Department: Bacteriological laboratory");
                    rowList.get(7).getCell(columnNumber ).setCellValue("Clinical diagnosis: examination Studies of a smear from the pharynx, nose");
                    rowList.get(8).getCell(columnNumber ).setCellValue("The result of the study: ");
                    rowList.get(9).getCell(columnNumber ).setCellValue("COVID-19 PCR testing results");
                    rowList.get(10).getCell(columnNumber ).setCellValue("SARS-CoV2 RNA – NOT DETECTED");
                    rowList.get(11).getCell(columnNumber ).setCellValue(persons.get(i).getProductionNumber());
                    rowList.get(12).getCell(columnNumber).setCellValue("Date and time of biomaterial sampling:");
                    rowList.get(13).getCell(columnNumber).setCellValue(persons.get(i).getSamplingDateTime());
                    rowList.get(14).getCell(columnNumber).setCellValue("Result ready date and time:");
                    rowList.get(15).getCell(columnNumber).setCellValue(persons.get(i).getResultReadyDateTime());
                    rowList.get(17).getCell(columnNumber ).setCellValue("Signature:");
                    i++;
                    if (i == persons.size()) {
                        break;
                    }
                    rowList.get(20).getCell(columnNumber).setCellValue("Ufa City Clinical Hospital No.21 Clinical  laboratory");
                    rowList.get(21).getCell(columnNumber).setCellValue(persons.get(i).getReport());
                    rowList.get(22).getCell(columnNumber + 1).setCellValue("Patient:");
                    rowList.get(23).getCell(columnNumber + 1).setCellValue(persons.get(i).getPatient());
                    rowList.get(24).getCell(columnNumber + 1).setCellValue(persons.get(i).getDateOfBirth());
                    rowList.get(25).getCell(columnNumber + 1).setCellValue(persons.get(i).getPassportNumber());
                    rowList.get(26).getCell(columnNumber ).setCellValue("Department: Bacteriological laboratory");
                    rowList.get(27).getCell(columnNumber ).setCellValue("Clinical diagnosis: examination Studies of a smear from the pharynx, nose");
                    rowList.get(28).getCell(columnNumber ).setCellValue("The result of the study: ");
                    rowList.get(29).getCell(columnNumber ).setCellValue("COVID-19 PCR testing results");
                    rowList.get(30).getCell(columnNumber ).setCellValue("SARS-CoV2 RNA – NOT DETECTED");
                    rowList.get(31).getCell(columnNumber ).setCellValue(persons.get(i).getProductionNumber());
                    rowList.get(32).getCell(columnNumber).setCellValue("Date and time of biomaterial sampling:");
                    rowList.get(33).getCell(columnNumber).setCellValue(persons.get(i).getSamplingDateTime());
                    rowList.get(34).getCell(columnNumber).setCellValue("Result ready date and time:");
                    rowList.get(35).getCell(columnNumber).setCellValue(persons.get(i).getResultReadyDateTime());
                    rowList.get(37).getCell(columnNumber ).setCellValue("Signature:");
                    columnNumber = columnNumber + 5;
                }

            } catch (IndexOutOfBoundsException inExc) {
                ColorPrint.cpRed.println("Какие то проблемы при обработке списка");
            }
            fileInputStream.close();

            FileOutputStream fos = new FileOutputStream(fileNameWriteExcel);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException fne) {
            ColorPrint.cpRed.println("файл не найден. Возможно открыт, проверь и перезапусти!");
        } catch (IOException io) {
            ColorPrint.cpRed.println("Ошибки ввода вывода");
        } finally {
            ColorPrint.cpYellow.println("Файл " + fileNameWriteExcel + " заполнен данными");
        }
    }


    /** write in *.txt file - текстоый вариант QR кода */
    public void txtFileWrite () {
        try (FileWriter fw = new FileWriter(fileTxt)) {
            for (Person person : persons) {
                fw.write("# " + counterRedaction + " ----------------------------\n" + person.getResult()  + "\n");
                counterRedaction++;
            }
            fw.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }
        ColorPrint.cpYellow.println("Текстовый вариант QR кода сохранен в файл c именем " + fileTxt.getName());
    }
}