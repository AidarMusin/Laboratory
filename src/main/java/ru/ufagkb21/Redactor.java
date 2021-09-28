package ru.ufagkb21;

import jdbc.connect.JDBCconnetion;

import java.io.*;
import java.util.*;

public class Redactor {
    private static int counterRedaction = 1;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String tableName = "savepeople";

        ColorPrint.cpMag.println("-----------------------------------------------------");

        // читаем Excel файл CoV.xlsx
        List<Person> persons  = new ProjectFileReader("CoV.xlsx").excelFileRead();
        JDBCconnetion jdbCconnetion = new JDBCconnetion();


        // Создаем динамический массив из считанных данных
        for (Person person : persons) {
            ColorPrint.cpGreen.println(person.toString());
            jdbCconnetion.appendPeople(person, tableName);
        }



        //jdbCconnetion.createTable("test");

        System.out.println(persons.size() + " кол-во персон на исследование");
        // формируем форму
        ExcelForm excelForm = new ExcelForm(persons.size());
        excelForm.createExcelForm();

        // Создаем объект FileWriter имеющего методы для записи в форму *.xlsx и сохранения под именем  "QR + сегодняшняя дата"
        ProjectFileWriter projectFileWriter = new ProjectFileWriter(persons, "file.xlsx", "QR");

        //projectFileWriter.txtFileWrite(); // - создание текстового варианты QR кода

        // На основе данных массива создаем QR коды
        for (Person person : persons) {
            CreateQrCode createQrCode = new CreateQrCode(person);
            //createQrCode.createQr(); // - запись QR  кода на диск

            // передаем каждый QR код в соответствующую ячейку
            projectFileWriter.exelImagesInsert(createQrCode.createQrFile());
        }

        // Вызываем метод заполнения в форму и пишем в файл
        projectFileWriter.excelFileWrite();

        ColorPrint.cpMag.println("-----------------------------------------------------");
    }
}