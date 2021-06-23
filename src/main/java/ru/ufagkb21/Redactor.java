package ru.ufagkb21;

import java.io.*;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.apache.commons.compress.utils.IOUtils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static java.lang.String.format;

public class Redactor {
    private static int counterRedaction = 1;

    public static void main(String[] args) {
        ColoredPrinter cpRed = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.RED).build();
        ColoredPrinter cpYellow = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.YELLOW).build();
        ColoredPrinter cpCyan = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.CYAN).build();
        ColoredPrinter cpMag = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.MAGENTA).build();
        ColoredPrinter cpGreen = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.GREEN).build();

        String productionExcelNameFileRead = "CoV.xlsx";
        String productionExcelNameFileWrite = "formQR.xlsx";
        String productionExcelNameFormCreate = "formQR1.xlsx";
        String productionTxtNameFileCreate = "QR.txt";;

        String testExcelNameFileRead = "testCoV.xlsx";
        String testExcelNameFileWrite = "testFormQR.xlsx";
        String testExcelNameFormCreate = "testFormQR1.xlsx";
        String testTxtNameFileCreate = "testQR.txt";

        /* Production version */
        String nameFileRead = productionExcelNameFileRead;
        String nameFileExcelWrite = productionExcelNameFileWrite;
        String nameFileCreate = productionExcelNameFormCreate;
        String nameFileTxtWrite = productionTxtNameFileCreate;

//        /* Testing version */
//        String nameFileRead = testExcelNameFileRead;
//        String nameFileExcelWrite = testExcelNameFileWrite;
//        String nameFileCreate = testExcelNameFormCreate;
//        String nameFileTxtWrite = testTxtNameFileCreate;



        List<Person> persons = new ProjectFileReader(nameFileRead).excelFileRead();


        for (Person o : persons) {
            cpGreen.println(o.toString());
        }
        cpMag.println("-----------------------------------------------------");

        ProjectFileWriter projectFileWriter = new ProjectFileWriter(persons, nameFileExcelWrite, nameFileTxtWrite);

        projectFileWriter.txtFileWrite();
        projectFileWriter.excelFileWrite();
    }
}