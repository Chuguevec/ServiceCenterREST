package ru.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.example.entity.Customer;
import ru.example.entity.Employee;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CompanyDto {

    private String name;

    private List<Customer> customers;

    private List<Employee> employees;
}
