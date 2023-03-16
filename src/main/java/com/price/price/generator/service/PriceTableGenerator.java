package com.price.price.generator.service;

import com.price.price.generator.model.Goods;
import com.price.price.generator.model.Price;

import java.util.List;

import static com.price.price.generator.controller.Controller.goodsList;

public class PriceTableGenerator {
    public static String[][] getTable() {
        int shiftRow = 1;
        int shiftCeil = 2;
        int countRow = goodsList.size() + shiftRow;
        int countCeil = 2;
        List<Price> prices = goodsList.get(0).getPrice();
        if (prices != null) {
            countCeil = prices.size() + shiftCeil;
        }

        String[][] table = new String[countRow][countCeil];
        table[0][0] = "Category";
        table[0][1] = "Price position";

        int itemPrice = shiftCeil;
        if (prices != null) {
            for (Price price : prices) {
                table[0][itemPrice++] = price.getPriceProvider();
            }
        }

        int row = 0;
        for (Goods goods : goodsList) {
            int ceil = shiftCeil;
            table[row + 1][0] = goods.getCategory();
            table[row + 1][1] = goods.getDevice().getDescription();
            if (goodsList.get(row).getPrice() != null) {
                for (Price price : goodsList.get(row).getPrice()) {
                    if (price.getPriceValue() == null) {
                        table[row + 1][ceil++] = "";
                    } else {
                        if (price.getPriceValue().equals("0")) {
                            table[row + 1][ceil++] = "";
                        } else {
                            table[row + 1][ceil++] = price.getPriceValue();
                        }
                    }
                }
            }
            row++;
        }
        return table;
    }
}
