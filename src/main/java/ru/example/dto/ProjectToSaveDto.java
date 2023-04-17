package ru.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProjectToSaveDto {

    private String name;
    private BigDecimal price;
    private String customerName;
}
