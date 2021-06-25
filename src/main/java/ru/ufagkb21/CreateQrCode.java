package ru.ufagkb21;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.*;

public class CreateQrCode {
    private Person person;
    private String fileName;
    ColoredPrinter cpBlue = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.BLUE).build();

    public CreateQrCode(Person person) {
        this.person = person;
        fileName = person.getNameFileQrCode();
    }

    public void createQr () {

        ByteArrayOutputStream bos = QRCode.from(person.getResult()).withSize(1000, 1000).to(ImageType.PNG).stream();

        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            bos.writeTo(outputStream);
            outputStream.flush();
            cpBlue.println("QR код - " + fileName + " сформирован");
        } catch (FileNotFoundException e) {
            System.out.println("скорее всего, не найдено имя файла");
        } catch (IOException e) {
            System.out.println("во время записи QR кода, что то пошло не так");
        }
    }
}
