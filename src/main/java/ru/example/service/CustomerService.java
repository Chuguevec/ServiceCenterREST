package ru.example.service;

import ru.example.entity.Company;
import ru.example.entity.Customer;
import ru.example.entity.Project;

import java.util.List;

public interface CustomerService {
    Customer findOne(int id);

    List<Customer> findAll();
    List<Customer> findAll(int page, int perPage);

    List<Customer> findAllByCompany(Company company);

    Customer save(Customer customer);

    Customer update(Customer customer);

    void addProject (int customerId, Project project);

    void delete(int id);
}
