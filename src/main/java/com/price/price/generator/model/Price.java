package com.price.price.generator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price  implements Serializable {
    private String priceProvider;
    private String price;

    @Override
    public String toString() {
        return
                "[ priceProvider='" + priceProvider + '\'' +
                ", price=" + price + "]";
    }
}
