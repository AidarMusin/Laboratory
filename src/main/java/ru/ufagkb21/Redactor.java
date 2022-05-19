package ru.ufagkb21;

import jdbc.connect.JDBConnection;

import java.io.*;
import java.util.*;

public class Redactor {
    private static int counterRedaction = 1;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String tableName = "savepeople";
        JDBConnection jdbConnection = new JDBConnection();
        ColorPrint.cpMag.println("--------------------------BEGIN---------------------------");

        // читаем Excel файл CoV.xlsx
        // Создаем динамический массив из считанных данных
        List<Person> persons  = new ProjectFileReader("CoV.xlsx").excelFileRead();

//        ColorPrint.cpYellow.println("-------------------КОРРЕКТИРОВКА-------------------");
//        // корректировка даты результата
//        List<String> findPerson = new ArrayList<>();
//        findPerson.add("KONKOVA GUZEL");
//        String dateNew = "04.10.2021";
//        List<Person> persons = jdbCconnetion.findPeople(findPerson, dateNew);
//        for (Person per : persons) {
//            System.out.println(per.getNumber() + " # " + per.getPatient() + " @ " + per.getDateOfBirth() + " @ " + per.getOnlyPasNumber() + " @ " + per.getOnlyDateResult());
//        }
//        ColorPrint.cpYellow.println("-------------------------КОРРЕКТИРОВКА---------------");

        for (Person person : persons) {
            ColorPrint.cpGreen.println(person.toString());
            jdbConnection.appendPeople(person, tableName);
        }
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
        ColorPrint.cpMag.println("------------------------END-----------------------------");


//        ColorPrint.cpRed.println("----------------DELETE---------------");
//        ArrayList<Integer> arrayNumberRow = new ArrayList<>();
//        arrayNumberRow.add(21);
//        arrayNumberRow.add(22);
//        jdbCconnetion.deleteRow(arrayNumberRow);
//        ColorPrint.cpRed.println("----------------DELETE---------------");

    }
}