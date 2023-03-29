package ru.example.service;

import ru.example.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    CompanyDto findOne(int id);

    List<CompanyDto> findAll();

    Integer save(CompanyDto companyDto);

    void update(CompanyDto companyDto);

    void delete(int id);
}
