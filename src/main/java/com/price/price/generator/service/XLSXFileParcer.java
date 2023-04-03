package com.price.price.generator.service;

import com.price.price.generator.model.Device;
import com.price.price.generator.model.Goods;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

import static com.price.price.generator.controller.Controller.goodsList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XLSXFileParcer {

    public static void parceXLSXFile(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0); // Получаем первый лист
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                int lastCellNum = row.getLastCellNum();
                if (lastCellNum > 0) {

                    String categoryProduct = getValueCell(row.getCell(0));
                    String nameProduct = getValueCell(row.getCell(1));
                    String code1Product = getValueCell(row.getCell(2));
                    String code2Product = getValueCell(row.getCell(3));

                    List<String> codeList = new ArrayList<>();
                    if (!code1Product.isEmpty()) {
                        codeList.add(code1Product);
                    }
                    if (!code2Product.isEmpty()) {
                        codeList.add(code2Product);
                    }
                    if (codeList.isEmpty()) {
                        codeList.add("na");
                    }

                    List<String> customCode = new ArrayList<>();
                    if (lastCellNum > 3) {
                        for (int j = 4; j < lastCellNum; j++) {
                            String tempCustomCode = getValueCell(row.getCell(j)).trim();
                            if (!tempCustomCode.isEmpty()) {
                                customCode.add(tempCustomCode);
                            }
                        }
                    }

                    Device device = new Device();
                    if (!nameProduct.isEmpty()) {
                        device.setStringDescription(nameProduct);
                        device.setCode(codeList);
                        if (customCode.size() != 0) {
                            device.setCustomCode(customCode);
                        } else {
                            device.setCustomCode(null);
                        }

                    }

                    Goods goods = new Goods();
                    if (!categoryProduct.isEmpty()) {
                        goods.setCategory(categoryProduct);
                        goods.setDevice(device);
                        goodsList.add(goods);
                    }
                }
            }
        }
    }

    private static String getValueCell(XSSFCell cell) {
        if (cell == null) {
            return "";
        } else {
            return cell.toString().trim();
        }
    }

}
