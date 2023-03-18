package com.price.price.generator.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XLSXFileGenerator {
    public static Workbook generateXlsxFile() {
        return getWorkbook(PriceTableGenerator.getTable());
    }

    public static Workbook generateXlsxFileByCategories() {
        return getWorkbook(PriceTableGenerator.getTableWithCategories());
    }

    private static Workbook getWorkbook(String[][] tableData) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Купи слона!");

        int rowIndex = 0;
        for (Object[] rowData : tableData) {
            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (Object cellData : rowData) {
                Cell cell = row.createCell(cellIndex++);
                if (cellData instanceof String cSData) {
                    cell.setCellValue(cSData);
                } else if (cellData instanceof Integer cIData) {
                    cell.setCellValue(cIData);
                }
            }
        }

        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 50 * 256);

        CellStyle columnStyle = getCellStyle(wb);

        Row firstRow = sheet.getRow(0);
        int columnCount = firstRow.getLastCellNum();
        if (columnCount >= 3) {
            for (int i = 4; i < columnCount; i++) {
                sheet.setColumnWidth(i, 10 * 256);
                for (int j = 0; j < sheet.getLastRowNum(); j++) {
                    Cell cell = sheet.getRow(j).getCell(i);
                    cell.setCellStyle(columnStyle);
                }
            }
        }
        return wb;
    }

    private static CellStyle getCellStyle(Workbook wb) {
        CellStyle columnStyle = wb.createCellStyle();
        columnStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        columnStyle.setFillPattern(FillPatternType.SPARSE_DOTS);
        columnStyle.setBorderBottom(BorderStyle.THIN);
        columnStyle.setBorderTop(BorderStyle.THIN);
        columnStyle.setBorderRight(BorderStyle.THIN);
        columnStyle.setBorderLeft(BorderStyle.THIN);
        columnStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        columnStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        columnStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        columnStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        return columnStyle;
    }
}
