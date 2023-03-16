package com.price.price.generator.model;


import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Plain implements Serializable {
    private String name;
    private String text;
    private String delimiter;
}
