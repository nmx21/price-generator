package com.price.price.generator.service;

import com.price.price.generator.model.Goods;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import static com.price.price.generator.controller.Controller.goodsList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XLSXFileGenerator {
    public static Workbook generateXlsxFile() {
        return getWorkbook(PriceTableGenerator.getTable());
    }

    public static Workbook generateXlsxFileByCategories() {
        return getWorkbook(PriceTableGenerator.getTableWithCategories());
    }

    public static Workbook generateXlsxFileByCategoriesInSeparateSheets() {
        String[][] tableContent = PriceTableGenerator.getTableWithCategories();
        Workbook wb = getWorkbook(tableContent);

        HashSet<String> categories = getCategories();
        if (!categories.isEmpty()) {
            for (String category : categories) {
                String[][] newTable = filterDataByCategory(PriceTableGenerator.getTable(), category);
                createNewSheet(newTable, wb, category);
            }
        }
        return wb;
    }

    private static String[][] filterDataByCategory(String[][] tableContent, String category) {
        List<String[]> filteredRows = new ArrayList<>();
        filteredRows.add(tableContent[0]);
        for (String[] row : tableContent) {
            if (row.length > 0 && category.equals(row[0])) {
                filteredRows.add(row);
            }
        }
        return filteredRows.toArray(new String[0][0]);
    }


    private static Workbook getWorkbook(String[][] tableData) {
        Workbook wb = new XSSFWorkbook();
        String sheetName = "index";
        createNewSheet(tableData, wb, sheetName);
        return wb;
    }

    private static Workbook createNewSheet(String[][] tableData, Workbook wb, String nameNewSheet) {
        Sheet sheet = wb.createSheet(nameNewSheet);


        int rowIndex = 0;
        for (Object[] rowData : tableData) {
            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (Object cellData : rowData) {
                Cell cell = row.createCell(cellIndex++);
                cell.setCellValue((String) cellData);

            }
        }

        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 50 * 256);

        CellStyle columnStyle = getCellStyle(wb);

        Row firstRow = sheet.getRow(0);
        int columnCount = firstRow.getLastCellNum();
        if (columnCount >= 4) {
            for (int i = 5; i < columnCount; i++) {
                sheet.setColumnWidth(i, 10 * 256);
                for (int j = 0; j <= sheet.getLastRowNum(); j++) {
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

    private static HashSet<String> getCategories() {
        HashSet<String> categories = new LinkedHashSet<>();
        for (Goods goods : goodsList) {
            categories.add(goods.getCategory());
        }
        return categories;
    }
}
