package com.price.price.generator.service;

import com.price.price.generator.model.Device;
import com.price.price.generator.model.Goods;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

import static com.price.price.generator.controller.Controller.goodsList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XLSXFileParcer {

    public static void parceXLSXFile(XSSFWorkbook workbook){
        XSSFSheet sheet = workbook.getSheetAt(0); // Получаем первый лист
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                int lastCellNum = row.getLastCellNum();
                if (lastCellNum > 0) {
                    String categoryProduct = row.getCell(0).toString();
                    String nameProduct = row.getCell(1).toString();
                    String code1Product = row.getCell(2).toString();
                    String code2Product = row.getCell(3).toString();

                    List<String> codeList = new ArrayList<>();
                    if (!code1Product.isBlank()) {
                        codeList.add(code1Product);
                    }
                    if (!code2Product.isBlank()) {
                        codeList.add(code2Product);
                    }
                    if (codeList.isEmpty()) {
                        codeList.add("na");
                    }

                    Device device = new Device();
                    if (!nameProduct.isBlank()) {
                        device.setDescription(nameProduct);
                        device.setCode(codeList);
                    }

                    Goods goods = new Goods();
                    if (!categoryProduct.isBlank()) {
                        goods.setCategory(categoryProduct);
                        goods.setDevice(device);
                        goodsList.add(goods);
                    }
                }
            }
        }
    }
}
