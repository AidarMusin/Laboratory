package ru.ufagkb21;

import net.glxn.qrgen.QRCode;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class QRcode {
    private Person person;
    private String fileName;

    public QRcode (Person person) {
        this.person = person;
    }


}
