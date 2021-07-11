package ru.ufagkb21;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.*;

public class CreateQrCode {
    private int numberN;
    private Person person;
    private String fileImgName;
    private File fileQrCode;
    private int[] coordinates = new int[4];
    private static int counter = 0;

    /** Конструктор генератора QR кода */
    public CreateQrCode(Person person) {
        this.numberN = person.getNumber();
        this.person = person;
        this.fileImgName = person.getNameFileQrCode();
        int x;  // 1 - 6  - 11 - 16 - 21 - 26
        int y;  // 2 - 22 - 2  - 22 - 2  - 22
        if ((numberN % 2) != 0 ) {
            y = 2;
            x =  counter + 1;
        } else {
            y = 22;
            x = counter + 1;
            counter = counter + 5;
        }
        this.coordinates[0] = x;       //Column B
        this.coordinates[1] = y;       //Row 3
        this.coordinates[2] = x + 1;   //Column C
        this.coordinates[3] = y + 4;   //Row 6
    }

    /** создаем QR коды в png формате на диске */
    public void createQr () {
        ByteArrayOutputStream bos = QRCode.from(person.getResult()).withSize(1000, 1000).to(ImageType.PNG).stream();
        try (OutputStream outputStream = new FileOutputStream(fileImgName)) {
            bos.writeTo(outputStream);
            outputStream.flush();
            ColorPrint.cpBlue.println("QR код - " + fileImgName + " сохранен на диске");
        } catch (FileNotFoundException e) {
            ColorPrint.cpRed.println("скорее всего, не найдено имя файла");
        } catch (IOException e) {
            ColorPrint.cpRed.println("во время записи QR кода, что то пошло не так");
        }
    }


    /** создаем QR код для дальнейшей передачи в форму excel */
    public Code createQrFile () {
        this.fileQrCode = QRCode.from(person.getResult()).withSize(1000, 1000).to(ImageType.PNG).file();
        return new Code(numberN, fileImgName, fileQrCode, coordinates);
    }


}
