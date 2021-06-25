package ru.ufagkb21;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveCode {

    /* Резульатат исследования */

//                if (getCellText(sheet.getRow(i).getCell(8)).toUpperCase().equals("DETECTED")) {
//        resultTest = true;
//    } else if (getCellText(sheet.getRow(i).getCell(8)).toLowerCase().equals("обнаружено"))  {
//        resultTest = true;
//    } else if (getCellText(sheet.getRow(i).getCell(8)).toLowerCase().equals("обнаружино"))  {
//        resultTest = true;
//    } else  {
//        resultTest = false;
//    }
//                try {
//        if (resultTest) {
//            throw  new IOException();
//        }
//
//    } catch (IOException ioException) {
//        cpRed.println("У пациента " + lastName +  " положительный тест");
//    }
}
    /* reading data from the *.txt format  (with boolean resultTest) */
//    public static List<Person> txtFileRead (String txtNameFileRead, String dateResult, boolean resultTest) {
//        List<Person> persons = new ArrayList<Person>();
//        int number;
//        String lastName;
//        String firstName;
//        String dateOfBirth;
//        String passportNumber;
//        String hospital = "Ufa City Clinical Hospital No. 21 \nDepartment: Bacteriological laboratory;";
//        int numberProd = 0, numberproduction = 0;
//
//        try (FileReader fileReader = new FileReader(txtNameFileRead);
//             BufferedReader readerReadFile = new BufferedReader(fileReader)) {
//            String line;
//            while ((line = readerReadFile.readLine()) != null) {
//                String[] arrayLine = line.split("[\\s]+");
//                number = Integer.parseInt(arrayLine[0]);
//                lastName = arrayLine[1];
//                firstName = arrayLine[2];
//                dateOfBirth = arrayLine[3];
//                passportNumber = arrayLine[4];
//                persons.add(new Person(number, lastName, firstName, dateOfBirth, passportNumber, resultTest, dateResult, numberProd,numberproduction, hospital));
//            }
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
//        System.out.println("Данные с файла " + txtNameFileRead + " считаны и список сформирован");
//        return persons;
//    }
//
    /* reading data from the *.txt format */
//    public static List<Person> txtFileRead (String txtNameFileRead, String dateResult) {
//        List<Person> persons = new ArrayList<Person>();
//        int number;
//        String lastName;
//        String firstName;
//        String dateOfBirth;
//        String passportNumber;
//        String hospital = "Ufa City Clinical Hospital No. 21 \nDepartment: Bacteriological laboratory;";
//        int numberProd = 0, numberproduction = 0;
//
//        try (FileReader fileReader = new FileReader(txtNameFileRead);
//             BufferedReader readerReadFile = new BufferedReader(fileReader)) {
//            String line;
//            while ((line = readerReadFile.readLine()) != null) {
//                String[] arrayLine = line.split("[\\s]+");
//                number = Integer.parseInt(arrayLine[0]);
//                lastName = arrayLine[1];
//                firstName = arrayLine[2];
//                dateOfBirth = arrayLine[3];
//                passportNumber = arrayLine[4];
//                persons.add(new Person(number, lastName, firstName, dateOfBirth, passportNumber, dateResult, numberProd, numberproduction, hospital));
//            }
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
//        System.out.println("Данные с файла " + txtNameFileRead + " считаны и список сформирован");
//        return persons;
//    }

    /* Подключение картинки */
//    public static void insertImagesExcel (String nameImage, String fileName) {
//        try (InputStream is = new FileInputStream(nameImage);
//             FileOutputStream fos = new FileOutputStream(fileName)) {
//
//            Workbook workbook = new XSSFWorkbook();
//            workbook.createSheet();
//            byte[] bytes = IOUtils.toByteArray(is);
//            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
//            workbook.write(fos);
//        } catch (IOException ioException) {
//            System.out.println("При загрузке изображения произошла ошибка");
//            ioException.printStackTrace();
//        }
//    }


/*    public String[] getDataForWriteExcel () {
        String[] result;
        int i;
        if (passportNumber != null) {
            i = 8;
            result = new String[i];
        } else {
            i = 7;
            result = new String[i];
        }
        result[0] = "" + String.valueOf(number);
        result[1] = "Report  No." + study.getNumberReport() + " of  the molecular biological study by polymerase chain reaction";
        result[2] = "Patient: " + lastName + " " + firstName;
        if (passportNumber != null)
            result[i-5] = "Passport: " + passportNumber;
        result[i-4] = "Date of Birth: " + dateOfBirth;
        result[i-3] = "SARS-CoV2 RNA – " + study.getResult();
        result[i-2] = "No." + study.getNumberReport() + " production No." + study.getNumberProduction();
        result[i-1] = "Result ready date: " + study.getDateTimeResultString();
        return result;
    }

 */