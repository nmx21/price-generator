package com.price.price.generator.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price implements Serializable {
    private String priceProvider;
    private String priceValue;

}
