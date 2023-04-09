package ru.example.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class ProjectDto {
    private String name;
    private BigDecimal price;
    private String customerName;
    private List<EmployeeDto> employees;
}
