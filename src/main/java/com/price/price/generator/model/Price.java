package com.price.price.generator.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {
    private String priceProvider;
    private String priceValue;

}
