package ru.ufagkb21;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class QRcode {
    private List<Person> persons;
    String fileName;

    public QRcode (List<Person> persons) {
        this.persons = persons;
    }






    public String getFileName (Person person) {
        return person.getPatient();
    }











    /** Подключение картинки */
    public static void insertImagesExcel (String nameImage, String fileName) {
        try (InputStream is = new FileInputStream(nameImage);
             FileOutputStream fos = new FileOutputStream(fileName)) {

            Workbook workbook = new XSSFWorkbook();
            workbook.createSheet();
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            workbook.write(fos);
        } catch (IOException ioException) {
            System.out.println("При загрузке изображения произошла ошибка");
            ioException.printStackTrace();
        }
    }
}
