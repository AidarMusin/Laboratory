package ru.ufagkb21;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelForm {
    private String fileName = "file.xlsx";
    private int columnCount;
    private int rowCount;
    private int personsSize;


    public ExcelForm (int personsSize) {

        if ((personsSize % 2) == 0) {
            this.columnCount = (personsSize / 2) * 5;
        } else {
            this.columnCount = (((personsSize) / 2) + 1) * 5;
        }

        this.personsSize = personsSize;

        if (personsSize > 1) {
            rowCount = 40;
        } else
            rowCount = 20;
        ColorPrint.cpBlue.println("Количество стобцов = " + columnCount);
    }

    /** Создаем форму EXCEL с параметрами - Входщие данные: имя файла и кол-во колонок*/
    public File createExcelForm () {

        Workbook bookNew = new XSSFWorkbook();
        Sheet sheet = bookNew.createSheet("patients");

        //создаем строки и добавляем в динамическикй массив
        List<Row> rowList = new ArrayList<Row>();
        for (int i = 0; i < rowCount; i++) {
            rowList.add(sheet.createRow(i));
        }

        //установим высоту строк
        for (Row row : sheet) {
            row.setHeight((short) 300);
        }
        rowList.get(0).setHeight((short) 885);
        rowList.get(1).setHeight((short) 885);
        rowList.get(2).setHeight((short) 400);
        rowList.get(3).setHeight((short) 560);
        rowList.get(4).setHeight((short) 400);
        rowList.get(5).setHeight((short) 400);
        rowList.get(7).setHeight((short) 560);
        rowList.get(18).setHeight((short) 675);
        if (personsSize > 1) {
            rowList.get(20).setHeight((short) 885);
            rowList.get(21).setHeight((short) 885);
            rowList.get(22).setHeight((short) 400);
            rowList.get(23).setHeight((short) 560);
            rowList.get(24).setHeight((short) 400);
            rowList.get(25).setHeight((short) 400);
            rowList.get(27).setHeight((short) 560);
        }

        //зададим ширину столбцов
        int columnArrWid [] = new int[10];
        columnArrWid[0] = 550;  // A
        columnArrWid[1] = 3913; // B
        columnArrWid[2] = 4820; // C
        columnArrWid[3] = 2303; // D
        columnArrWid[4] = 1339; // E
        columnArrWid[5] = 1339; // F
        columnArrWid[6] = 3913; // G
        columnArrWid[7] = 4820; // H
        columnArrWid[8] = 2303; // I
        columnArrWid[9] = 550;  // J

        for (int k = 0; k < columnCount; k ++) {
            for (int i = 0; i < columnArrWid.length; i++) {
                sheet.setColumnWidth(k, columnArrWid[i]);
                k++;
                if (k == columnCount) {
                    break;
                }
            }
            k--;
        }

        // объеденяем ячейки
        int rowX = 0;
        int columnY = 0;
        for (int numberPerson = 0; numberPerson < personsSize; numberPerson ++) {

            sheet.addMergedRegion(new CellRangeAddress(0 + rowX, 0 + rowX, 1 + columnY, 3 + columnY));
            sheet.addMergedRegion(new CellRangeAddress(1 + rowX, 1 + rowX, 1 + columnY, 2 + columnY));
            sheet.addMergedRegion(new CellRangeAddress(2 + rowX, 5 + rowX, 1 + columnY, 1 + columnY));
            sheet.addMergedRegion(new CellRangeAddress(2 + rowX, 2 + rowX, 2 + columnY, 3 + columnY));
            sheet.addMergedRegion(new CellRangeAddress(3 + rowX, 3 + rowX, 2 + columnY, 3 + columnY));
            sheet.addMergedRegion(new CellRangeAddress(4 + rowX, 4 + rowX, 2 + columnY, 3 + columnY));
            sheet.addMergedRegion(new CellRangeAddress(5 + rowX, 5 + rowX, 2 + columnY, 3 + columnY));
            for (int i = 6; i < 19; i++) {
                sheet.addMergedRegion(new CellRangeAddress(i + rowX, i + rowX, 1 + columnY, 3 + columnY));
            }

            if ((numberPerson % 2) != 0) {
                rowX = 0;
                columnY = columnY + 5;
            }

            if ((numberPerson % 2) == 0) {
                rowX = 20;
            }
        }


        //создаем ячейки  с параметрами по умолчанию
        for (int j = 0; j < rowList.size(); j ++) {
            for (int i = 1; i < columnCount; i = i + 2) {
                createCellMyWb (bookNew, rowList.get(j), i);
            }
        }

        // создаем форму
//        for (int i = 1; i < columnCount; i = i + 2) {
//            createCellMyWb (bookNew, rowList.get(0),i,true, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
//
//            CellStyle cellOnlyStyle = bookNew.createCellStyle();
//            cellOnlyStyle.setAlignment(HorizontalAlignment.CENTER);
//            cellOnlyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//            cellOnlyStyle.setWrapText(true);
//            rowList.get(0).createCell(i-1).setCellStyle(cellOnlyStyle);
//
////            createCellMyWb (bookNew, rowList.get(1),i, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 11,true).setCellValue("Ufa City Clinical Hospital No.21 Clinical  laboratory");
////            createCellMyWb (bookNew, rowList.get(8),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("Department: Bacteriological laboratory");
////            createCellMyWb (bookNew, rowList.get(9),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("Clinical diagnosis: examination Studies of a smear from the pharynx, nose");
////            createCellMyWb (bookNew, rowList.get(11),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER,10,true).setCellValue("The result of the study: ");
////            createCellMyWb (bookNew, rowList.get(12),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("COVID-19 PCR testing results");
////            createCellMyWb (bookNew, rowList.get(13),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("SARS-CoV2 RNA – NOT DETECTED");
////            createCellMyWb (bookNew, rowList.get(19),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("Signature: ");
////            createCellMyWb (bookNew, rowList.get(21),i,false, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
//        }

        File file = new File (fileName);
        try (OutputStream fos = new FileOutputStream(file)) {
            CellReference cellRef = new CellReference(rowCount, columnCount);
            String letterF = cellRef.formatAsString();
            System.out.println(letterF + " - формат");
            bookNew.setPrintArea(0,String.format("$A$1:$%s%s", letterF.substring(0,1),letterF.substring(1))); //область печати
            sheet.getPrintSetup().setLandscape(false); // Оринетация книжная
            bookNew.write(fos);
            System.out.println("Файл с названием " + fileName + " нужными параметрами строк и столбцов создан.");
        } catch (IOException io) {
            System.out.println("При записи файла произошла ошибка.");
        }
        return file;
    }


    /** create a new cell with style  - верхняя и нижняя ячейка */
    private static Cell createCellMyWb(Workbook wb, Row row, int column, boolean topLineBottom, HorizontalAlignment halign, VerticalAlignment valign) {
        Cell cell = row.createCell(column);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        if (topLineBottom) {
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setTopBorderColor(IndexedColors.AQUA.getIndex());
        } else {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBottomBorderColor(IndexedColors.AQUA.getIndex());
        }
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.AQUA.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.AQUA.getIndex());
        cell.setCellStyle(cellStyle);
        return cell;
    }
    /** create a new cell with style  - ячейки со всеми параметрами */
    private static Cell createCellMyWb(Workbook wb, Row row, int column, HorizontalAlignment halign, VerticalAlignment valign, int fontSize, String fontName, boolean boldFontWrap) {
        Cell cell = row.createCell(column);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setFontName(fontName);
        font.setBold(boldFontWrap);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.AQUA.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.AQUA.getIndex());
        cellStyle.setWrapText(boldFontWrap);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        return cell;
    }
    /** create a new cell with style  - ячейки со всеми параметрами, шрифт по умолчанию Verdana */
    private static Cell createCellMyWb(Workbook wb, Row row, int column, HorizontalAlignment halign, VerticalAlignment valign, int fontSize, boolean boldFontWrap) {
        Cell cell = row.createCell(column);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setFontName("Verdana");
        font.setBold(boldFontWrap);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.AQUA.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.AQUA.getIndex());
        cellStyle.setWrapText(boldFontWrap);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        return cell;
    }
    /** create a new cell with style  - шрифт: Verdana, 10, не жирный, в яччейке с переносом  */
    private static Cell createCellMyWb(Workbook wb, Row row, int column, HorizontalAlignment halign, VerticalAlignment valign) {
        Cell cell = row.createCell(column);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Verdana");
        font.setBold(false);
        cellStyle.setWrapText(true);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.AQUA.getIndex()); //AQUA.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.AQUA.getIndex());//.AQUA.getIndex());
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        return cell;
    }
    /** create a new cell with style  - шрифт: Verdana, 10, не жирный, в яччейке с переносом; центрования: слева, горизонт: центр */
    private static Cell createCellMyWb(Workbook wb, Row row, int column) {
        Cell cell = row.createCell(column);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Verdana");
        font.setBold(false);
        cellStyle.setWrapText(true);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.AQUA.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.AQUA.getIndex());
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        return cell;
    }
}
