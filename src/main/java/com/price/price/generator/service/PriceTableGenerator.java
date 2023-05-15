package com.price.price.generator.service;

import com.price.price.generator.model.Goods;
import com.price.price.generator.model.Price;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

import static com.price.price.generator.controller.Controller.goodsList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceTableGenerator {

    public static final int SHIFT_CEIL = 5;

    public static String[][] getTable() {
        if (goodsList.isEmpty()) {
            return new String[0][0];
        }
        List<Price> prices = goodsList.get(0).getPrice();
        int countRow = goodsList.size() + 1;
        int countCeil = (prices != null) ? (prices.size() + SHIFT_CEIL) : 3;

        String[][] table = new String[countRow][countCeil];
        table[0][0] = "Код";
        table[0][1] = "Категорія";
        table[0][2] = "Товарна позиція";
        if (prices != null && !prices.isEmpty()) {
            table[0][3] = "Ціна($)";
            table[0][4] = "";
        }


        int itemPrice = SHIFT_CEIL;
        if (prices != null && !prices.isEmpty()) {
            for (Price price : prices) {
                table[0][itemPrice++] = price.getPriceProvider();
            }
        }

        int row = 0;
        for (Goods goods : goodsList) {
            int ceil = SHIFT_CEIL;
            table[row + 1][0] = goods.getCategory();
            table[row + 1][1] = goods.getSubCategory();
            table[row + 1][2] = goods.getDevice().getStringDescription();
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

    public static String[][] getTableWithCategories() {
        int shiftRow = (getCountCategories() * 2) + 1;
        int countRow = goodsList.size() + shiftRow;
        List<Price> prices = goodsList.get(0).getPrice();
        int countCeil = (prices != null) ? (prices.size() + SHIFT_CEIL) : 2;

        String[][] table = new String[countRow][countCeil];
        table[0][0] = "Код";
        table[0][1] = "Категорія";
        table[0][2] = "Товарна позиція";
        if (prices != null && !prices.isEmpty()) {
            table[0][3] = "Ціна($)";
            table[0][4] = "";
        }

        int itemPrice = SHIFT_CEIL;
        if (prices != null) {
            for (Price price : prices) {
                table[0][itemPrice++] = price.getPriceProvider();
            }
        }

        String currentCategory = "";
        int row = 0;
        int rowInList = 0;
        for (Goods goods : goodsList) {
            if (!currentCategory.equals(goods.getSubCategory())) {
                table[++row + 1][2] = goods.getSubCategory();
                currentCategory = goods.getSubCategory();
                row++;
            }

            int ceil = SHIFT_CEIL;
            String code = "(" + String.join("/", goods.getDevice().getCode()) + ")";
            table[row + 1][0] = code;
            table[row + 1][1] = goods.getCategory();
            table[row + 1][2] = goods.getDevice().getStringDescription();
            if (goodsList.get(rowInList).getPrice() != null) {
                for (Price price : goodsList.get(rowInList).getPrice()) {
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
            rowInList++;
        }
        return table;
    }

    private static HashSet<String> getCategories() {
        HashSet<String> categories = new HashSet<>();
        for (Goods goods : goodsList) {
            categories.add(goods.getSubCategory());
        }
        return categories;
    }

    private static int getCountCategories() {
        return getCategories().size();
    }
}
