package com.price.price.generator.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Device {
    private String stringDescription;
    private List<String> code;
    private List<String> customCode;
}
