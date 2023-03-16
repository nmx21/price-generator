package com.price.price.generator.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Device implements Serializable {
    private String description;
    private List<String> code;
}
