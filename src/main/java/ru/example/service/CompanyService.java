package ru.example.service;

import ru.example.dto.CompanyDto;
import ru.example.dto.CompanyWithIdDto;

import java.util.List;

public interface CompanyService {
    CompanyWithIdDto findOne(int id);

    List<CompanyWithIdDto> findAll();

    Integer save(CompanyDto companyDto);

    CompanyWithIdDto update(CompanyDto companyDto, int id);

    CompanyWithIdDto delete(int id);
}
