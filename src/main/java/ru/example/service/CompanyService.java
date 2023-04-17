package ru.example.service;

import ru.example.entity.Company;

import java.util.List;

public interface CompanyService {
    Company findOne(int id);

    List<Company> findAll();

    Company save(Company company);

    Company update(Company company);

    void delete(Company company);

    Company findByName(String name);
}
