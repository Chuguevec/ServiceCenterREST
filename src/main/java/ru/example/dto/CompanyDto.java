package ru.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class CompanyDto {

    private String name;
    private List<CustomerDto> customers;
    private List<EmployeeDto> employees;
}
