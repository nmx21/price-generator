package com.price.price.generator.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXFileGenerator {
    public static Workbook generateXlsxFile() {

        String[][] tableData = PriceTableGenerator.getTable();
        Workbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("Купи слона!");


        int rowIndex = 0;
        for (Object[] rowData : tableData) {
            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (Object cellData : rowData) {
                Cell cell = row.createCell(cellIndex++);
                if (cellData instanceof String) {
                    cell.setCellValue((String) cellData);
                } else if (cellData instanceof Integer) {
                    cell.setCellValue((Integer) cellData);
                }
            }
        }
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        Row firstRow = sheet.getRow(0);
        int columnCount = firstRow.getLastCellNum();
        if (columnCount>=3){
            for (int i = 2; i <columnCount ; i++) {
                sheet.setColumnWidth(i, 10 * 256);
            }
        }

        return wb;
    }

}
