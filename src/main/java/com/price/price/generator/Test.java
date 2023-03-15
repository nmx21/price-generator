package com.price.price.generator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String textLine = "01633  iPhone 13 Pro Max 128GB Graphite (MLL63)      1270\n";
        String patternStr = "(\\s+)\\d";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(textLine);
        int max = 0;
        while(matcher.find()) {
            //Prints the offset after the last character matched.
            System.out.println("First Capturing Group, (a*b) Match String end(): "+matcher.end());
            max = matcher.end()-1;
        }
        System.out.println(textLine.substring(0,max));
        System.out.println(textLine.substring(max));



    }
}
