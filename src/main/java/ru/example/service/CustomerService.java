package ru.example.service;

import ru.example.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer findOne(int id);

    List<Customer> findAll();
    List<Customer> findAll(int page, int perPage);

    Customer save(Customer customer);

    void update(Customer customer);

    void delete(int id);
}
