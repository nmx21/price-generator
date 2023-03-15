package com.price.price.generator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    private String category;
    private Device device;
    private List<Price> price;

    @Override
    public String toString() {
        return "\n" +
                "category='" + category + '\'' +
                ", device=" + device +
                ", price=" + price +"\n";
    }
}
