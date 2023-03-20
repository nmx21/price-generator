package com.price.price.generator.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Device {
    private String description;
    private List<String> code;
}
