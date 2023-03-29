package ru.example.service;

import ru.example.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto findOne(int id);

    List<CustomerDto> findAll();

    Integer save(CustomerDto customerDto);

    void update(CustomerDto customerDto);

    void delete(int id);
}
