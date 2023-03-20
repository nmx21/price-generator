package com.price.price.generator.model;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlainText {
    private String name;
    private String text;
    private String delimiter;
}
