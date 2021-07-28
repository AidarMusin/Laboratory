package ru.ufagkb21;

import java.io.*;
import java.util.*;

public class Redactor {
    private static int counterRedaction = 1;

    public static void main(String[] args) throws IOException {

//        String nameFileRead = "CoV.xlsx";
//        String nameFileExcelWrite = "formQR.xlsx";
//        String readyFileName = "QR";

        ColorPrint.cpMag.println("-----------------------------------------------------");
        // читаем Excel файл CoV.xlsx

        List<Person> persons  = new ProjectFileReader("CoV.xlsx").excelFileRead();

        // Создаем динамический массив из считанных данных
        for (Person o : persons) {
            ColorPrint.cpGreen.println(o.toString());
        }
        // Создаем объект FileWriter имеющего методы для записи в форму formQR.xlsx и сохранения под именем  "QR + сегодняшняя дата"
        ProjectFileWriter projectFileWriter = new ProjectFileWriter(persons, "formQR.xlsx", "QR");
        //projectFileWriter.txtFileWrite(); // - создание текстового варианты QR кода

//        // На основе данных массива создаем QR коды
//        for (Person person : persons) {
//            CreateQrCode createQrCode = new CreateQrCode(person);
//            //createQrCode.createQr(); // - запись QR  кода на диск
//
//            // передаем каждый QR код в соответствующую ячейку
//            projectFileWriter.exelImagesInsert(createQrCode.createQrFile());
//        }
//        // Вызываем метод заполнения в форму и пишем в файл
//        projectFileWriter.excelFileWrite();
//
//        System.out.println(persons.size() + " кол-во столбцов");

        ExcelForm excelForm = new ExcelForm(persons.size());
        excelForm.createExcelForm();

        ColorPrint.cpMag.println("-----------------------------------------------------");


    }
}