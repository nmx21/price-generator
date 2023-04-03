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
        goodsList = new ArrayList<>();
        String[] lines = plainText.getText().split(System.getProperty("line.separator"));
        int start = 1;

        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {
                String[] partitions = line.split("\t");
                List<String> codeList = new ArrayList<>();
                List<String> customCodeList = new ArrayList<>();
                int countPartOfLine = partitions.length;

                if (countPartOfLine == 2) {
                    codeList.add("na" + start);
                }

                if (countPartOfLine >= 3) {
                    if (!partitions[2].trim().isEmpty()) {
                        codeList.add(partitions[2]);
                    }
                }

                if (countPartOfLine >= 4) {
                    if (!partitions[3].trim().isEmpty()) {
                        codeList.add(partitions[3]);
                    }
                }

                if (countPartOfLine > 4) {
                    customCodeList = getCustomCode(partitions);
                }

                Goods goods = new Goods(partitions[0], new Device(partitions[1], codeList, customCodeList), null);
                goodsList.add(goods);
            }
        }
        return goodsList;
    }

    private static List<String> getCustomCode(String[] partitions) {
        List<String> customCode = new ArrayList<>();
        for (int i = 4; i < partitions.length; i++) {
            String tempValuePartition = partitions[i].trim();
            if (!tempValuePartition.isEmpty()) {
                customCode.add(tempValuePartition);
            }
        }
        return customCode.size() > 0 ? customCode : null;
    }

    public static void addNewPrice(PlainText plainText) {
        if (Objects.equals(goodsList, null)) {
            throw new IllegalArgumentException("Спочатку створи основний прайс !!!");
        }

        badList.clear();
        withOutPriceList.clear();
        generateEmptyPrices(plainText);

        List<String> linesFromPlainText = Arrays.stream(plainText.getText().split(System.getProperty("line.separator")))
                .collect(Collectors.toList());
        for (String line : linesFromPlainText) {
            line = line.trim();
            if (isHaveText(line)) {
                String priceValue = breakLineToParts(line, plainText.getDelimiter());

                if (priceValue == null) {
                    withOutPriceList.add(line);
                } else {
                    findPositionInMainPrice(line, new Price(plainText.getName(), priceValue));
                }
            }
        }
    }

    private static void generateEmptyPrices(PlainText plainText) {
        for (Goods goodsitem : goodsList) {
            List<Price> priceList = goodsitem.getPrice();
            if (priceList == null) {
                priceList = new ArrayList<>();
            }
            Price newPrice = new Price(plainText.getName(), "0");
            priceList.add(newPrice);
            goodsitem.setPrice(priceList);
        }
    }

    private static void findPositionInMainPrice(String partition, Price price) {
        boolean isPositionPresent = false;
        for (Goods goodsItem : goodsList) {
            List<String> codeList = goodsItem.getDevice().getCode();
            List<String> customCodeList = goodsItem.getDevice().getCustomCode();
            if (codeList != null) {
                isPositionPresent = isPositionPresentByCode(partition, price, goodsItem, codeList);
            }
            if (isPositionPresent) {
                break;
            }
            if (customCodeList != null) {
                isPositionPresent = isPositionPresentByCode(partition, price, goodsItem, customCodeList);
            }
            if (isPositionPresent) {
                break;
            }
        }
        if (!isPositionPresent) {
            badList.add(partition + " " + price.getPriceValue());
        }
    }

    private static boolean isPositionPresentByCode(String partition, Price price, Goods goodsItem, List<String> codeList) {
        for (String codeItem : codeList) {
            if (isContains(partition, codeItem)) {
                List<Price> currentPriceList = goodsItem.getPrice();
                currentPriceList.set(currentPriceList.size() - 1, price);
                return true;
            }
        }
        return false;
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
