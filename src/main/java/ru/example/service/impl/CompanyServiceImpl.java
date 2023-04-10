package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.CompanyDAO;
import ru.example.entity.Company;
import ru.example.service.CompanyService;
import ru.example.utils.DtoUtil;

import java.util.List;
import java.util.stream.Collectors;

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
        return companyDAO.findOne(id);
    }
    @Override
    public List<Company> findAll(){
        return companyDAO.findAll();
    }
    @Transactional
    @Override
    public Company save (Company company){
        return companyDAO.create(company);
    }
    @Transactional
    @Override
    public Company update (Company updatedCompany){
        return companyDAO.update(updatedCompany);
    }
    @Transactional
    @Override
    public void delete (int id){
        companyDAO.deleteById(id);
    }



}
