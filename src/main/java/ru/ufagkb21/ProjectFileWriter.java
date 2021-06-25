package ru.ufagkb21;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectFileWriter {

    private static int counterRedaction = 1;
    private List<Person> persons;
    private File fileExel;
    private File fileTxt;
    private String hospital = "Ufa City Clinical Hospital No. 21 \nDepartment: Bacteriological laboratory;";
    private ColoredPrinter cpRed = new ColoredPrinter.Builder(1, false).foreground(FColor.RED).build();
    private ColoredPrinter cpYellow = new ColoredPrinter.Builder(1, false).foreground(FColor.YELLOW).build();
    private ColoredPrinter cpCyan = new ColoredPrinter.Builder(1, false).foreground(FColor.CYAN).build();



    public ProjectFileWriter (List<Person> persons, File file) {
        this.persons = persons;
        this.fileExel = file;
    }

    public ProjectFileWriter (List<Person> persons, String fileName, String fileNameTxt) {
        this.persons = persons;
        this.fileExel = new File(fileName);
        this.fileTxt = new File(fileNameTxt);
    }


    /** Запись в excel форму данные - передаем file */
    public void excelFileWrite () {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileExel);

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheetOriginal = workbook.getSheetAt(0);

            List<Row> rowList = new ArrayList<>();
            for (Row row : sheetOriginal) {
                rowList.add(row);
            }

            cpCyan.println("Количество людей в списке на исследование - " + persons.size());

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
                    cpRed.println("Какие то проблемы при обработке списка");
            }


            fileInputStream.close();

            FileOutputStream fos = new FileOutputStream(fileExel);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException fne) {
            cpRed.println("файл не найден. Возможно открыт, проверь и перезапусти!");
        } catch (IOException io) {
            cpRed.println("Ошибки ввода вывода");
        }
        cpYellow.println("Файл " + fileExel.getName() + " заполнен данными");
    }




    /** write in *.txt file - нужно для QR кода */
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

        cpYellow.println("Для создания QR кода файл c именем " + fileTxt.getName() + " сформирован");
    }
}