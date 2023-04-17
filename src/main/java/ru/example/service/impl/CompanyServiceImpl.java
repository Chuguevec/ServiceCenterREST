package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.CompanyDAO;
import ru.example.entity.Company;
import ru.example.service.CompanyService;
import ru.example.utils.exception.CompanyNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDAO companyDAO;

    @Autowired
    public CompanyServiceImpl(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    public Company findOne(int id) {
        Optional<Company> company = companyDAO.findOne(id);
        return company.orElse(company.orElseThrow(CompanyNotFoundException::new));
    }

    @Override
    public List<Company> findAll() {
        return companyDAO.findAll();
    }

    @Transactional
    @Override
    public Company save(Company company) {
        return companyDAO.create(company);
    }

    @Transactional
    @Override
    public Company update(Company updatedCompany) {
        return companyDAO.update(updatedCompany).orElseThrow(CompanyNotFoundException::new);
    }

    @Transactional
    @Override
    public void delete(Company company) {
        companyDAO.delete(company);
    }


}
