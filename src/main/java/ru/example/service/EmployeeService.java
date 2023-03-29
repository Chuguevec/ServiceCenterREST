package ru.example.service;

import ru.example.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto findOne(int id);

    List<EmployeeDto> findAll();

    Integer save(EmployeeDto employeeDto);

    void update(EmployeeDto employeeDto);

    void delete(int id);
}
