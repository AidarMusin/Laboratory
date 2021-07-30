package ru.ufagkb21;

import java.io.*;

public class Code {
    private int numberN;
    private File fileQrCode;
    private String fileImgName;
    private int [] coordinates;

    public Code (int numberN, String fileImgName, File fileQrCode, int [] coordinates) {
        this.numberN = numberN;
        this.fileImgName = fileImgName;
        this.fileQrCode = fileQrCode;
        this.coordinates = coordinates;
    }

    public int getCoordinates (int numberIndex) {
        return coordinates[numberIndex];
    }

    public File getFileQrCode() {
        return fileQrCode;
    }
}
