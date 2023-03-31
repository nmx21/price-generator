package com.price.price.generator.service;

import com.price.price.generator.model.Device;
import com.price.price.generator.model.Goods;
import com.price.price.generator.model.PlainText;
import com.price.price.generator.model.Price;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.price.price.generator.controller.Controller.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceGeneratorService {
    public static List<Goods> prepareBasePrice(PlainText plainText) {
        List<Goods> goodsList = new ArrayList<>();
        String[] lines = plainText.getText().split(System.getProperty("line.separator"));
        int start = 1;

        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {
                String[] partitions = line.split("\t");
                List<String> codeList = new ArrayList<>();

                switch (partitions.length) {
                    case 2:
                        codeList.add("na" + start);
                        break;
                    case 3:
                        codeList.add(partitions[2]);
                        break;
                    case 4:
                        codeList.add(partitions[2]);
                        codeList.add(partitions[3]);
                        break;
                }

                Goods goods = new Goods(partitions[0], new Device(partitions[1], codeList), null);
                goodsList.add(goods);
            }

        }
        return goodsList;
    }

    public static List<Goods> addNewPrice(PlainText plainText) {
        if (Objects.equals(goodsList, null)) {
            throw new IllegalArgumentException("Спочатку створи основний прайс !!!");
        }

        for (Goods goodsitem : goodsList) {
            List<Price> priceList = goodsitem.getPrice();
            if (priceList == null) {
                priceList = new ArrayList<>();
            }
            Price newPrice = new Price(plainText.getName(), "0");
            priceList.add(newPrice);
            goodsitem.setPrice(priceList);
        }

        List<String> linesFromPlainText = Arrays.stream(plainText.getText().split(System.getProperty("line.separator")))
                .collect(Collectors.toList());
        for (String line : linesFromPlainText) {
            line = line.trim();
            if (isHaveText(line)) {
                String priceValue = breakLineToParts(line, plainText.getDelimiter());

                if (priceValue == null) {
                    withOutPriceList.add(line);
                } else {
                    isPositionPresentInMainPrice(line, new Price(plainText.getName(), priceValue));
                }
            }
        }
        return goodsList;
    }

    private static void isPositionPresentInMainPrice(String partition, Price price) {
        boolean isPositionPresent = false;
        for (Goods goodsItem : goodsList) {
            List<String> codeList = goodsItem.getDevice().getCode();
            for (String codeItem : codeList) {
                if (isContains(partition, codeItem)) {
                    List<Price> currentPriceList = goodsItem.getPrice();
                    currentPriceList.set(currentPriceList.size() - 1, price);
                    isPositionPresent = true;
                }
            }
        }
        if (!isPositionPresent) {
            badList.add(partition + " " + price.getPriceValue());
        }
    }


    private static boolean isHaveText(String partitionOfText) {
        partitionOfText = partitionOfText.trim();
        return !partitionOfText.trim().isEmpty() && partitionOfText.length() > 3;
    }

    private static String breakLineToParts(String textLine, String separator) {
        Pattern pattern = Pattern.compile("\\s\\d+\\s*$");
        if (separator != null && !separator.trim().isEmpty()) {
            pattern = Pattern.compile(separator);
        }
        Matcher matcher = pattern.matcher(textLine);

        if (matcher.find()) {
            String number = matcher.group();
            return number.trim();
        }
        return null;
    }

    private static boolean isContains(String text, String textBlock) {
        return text.toLowerCase().contains(textBlock.toLowerCase());
    }

}
