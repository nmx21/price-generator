package com.price.price.generator.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods {
    private String category;
    private String subCategory;
    private Device device;
    private List<Price> price;

}
