package ru.ufagkb21;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelForm {
    String fileName;
    int columnCount;
    int rowCount = 22;


    public ExcelForm (String fileName, int columnCount, int rowCount) {
        this.fileName = fileName;
        this.columnCount = columnCount;
        this.rowCount = rowCount;

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

        //зададим ширину столбцов
        int columnFirsWidth = 1725;
        int columnSecondWidth = 9670;
        for (int i = 0; i < columnCount; i = i + 2) {
            sheet.setColumnWidth(i, columnFirsWidth);
            sheet.setColumnWidth(i + 1, columnSecondWidth);
        }

        //установим высоту строк
        for (Row row : sheet) {
            row.setHeight((short) 300);
        }
        rowList.get(0).setHeight((short) 1800);
        rowList.get(1).setHeight((short) 600);
        rowList.get(2).setHeight((short) 800);
        rowList.get(9).setHeight((short) 500);

        //создаем ячейки  с параметрами по умолчанию
        for (int j = 0; j < rowList.size(); j ++) {
            for (int i = 1; i < columnCount; i = i + 2) {
                createCellMyWb (bookNew, rowList.get(j), i);
            }
        }

        // создаем форму
        for (int i = 1; i < columnCount; i = i + 2) {
            createCellMyWb (bookNew, rowList.get(0),i,true, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);

            CellStyle cellOnlyStyle = bookNew.createCellStyle();
            cellOnlyStyle.setAlignment(HorizontalAlignment.CENTER);
            cellOnlyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellOnlyStyle.setWrapText(true);
            rowList.get(0).createCell(i-1).setCellStyle(cellOnlyStyle);

            createCellMyWb (bookNew, rowList.get(1),i, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 11,true).setCellValue("Ufa City Clinical Hospital No.21 Clinical  laboratory");
            createCellMyWb (bookNew, rowList.get(8),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("Department: Bacteriological laboratory");
            createCellMyWb (bookNew, rowList.get(9),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("Clinical diagnosis: examination Studies of a smear from the pharynx, nose");
            createCellMyWb (bookNew, rowList.get(11),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER,10,true).setCellValue("The result of the study: ");
            createCellMyWb (bookNew, rowList.get(12),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("COVID-19 PCR testing results");
            createCellMyWb (bookNew, rowList.get(13),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("SARS-CoV2 RNA – NOT DETECTED");
            createCellMyWb (bookNew, rowList.get(19),i, HorizontalAlignment.LEFT, VerticalAlignment.CENTER).setCellValue("Signature: ");
            createCellMyWb (bookNew, rowList.get(21),i,false, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        }

        File file = new File (fileName);
        try (OutputStream fos = new FileOutputStream(file)) {
            CellReference cellRef = new CellReference(rowCount, columnCount);
            String letterF = cellRef.formatAsString();
            System.out.println(letterF + " - формат");
            bookNew.setPrintArea(0,String.format("$A$1:$%s%s", letterF.substring(0,1),letterF.substring(1))); //область печати
            sheet.getPrintSetup().setLandscape(true);
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
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setTopBorderColor(IndexedColors.AQUA.getIndex());
        } else {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBottomBorderColor(IndexedColors.AQUA.getIndex());
        }
        cellStyle.setBorderLeft(BorderStyle.THIN);
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
