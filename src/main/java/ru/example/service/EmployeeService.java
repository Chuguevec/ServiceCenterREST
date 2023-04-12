package ru.example.service;

import ru.example.entity.Company;
import ru.example.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee findOne(int id);

    List<Employee> findAll();

    List<Employee> findAllByCompany(Company company);

    Employee save(Employee employee);

    Employee update(Employee employee);

    void delete(int id);
}
