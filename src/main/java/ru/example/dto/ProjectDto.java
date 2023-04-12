package ru.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProjectDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String customerName;
    private List<EmployeeDto> employees;
}
