package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.CompanyDAO;
import ru.example.dto.CompanyDto;
import ru.example.entity.Company;
import ru.example.service.CompanyService;

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

    public CompanyDto findOne(int id){
        return entityToDto(companyDAO.findOne(id));
    }

    public List<CompanyDto> findAll(){
        return companyDAO.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }
    @Transactional
    public Integer save (CompanyDto companyDto){
        return companyDAO.create(dtoToEntity(companyDto)).getId();
    }
    @Transactional
    public void update (CompanyDto companyDto){
       companyDAO.update(dtoToEntity(companyDto));
    }
    @Transactional
    public void delete (int id){
        companyDAO.deleteById(id);
    }

    private Company dtoToEntity(CompanyDto companyDto){
        Company company = new Company();
        company.setName(companyDto.getName());
        return company;
    }

    private CompanyDto entityToDto(Company company){
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCustomers(company.getCustomers());
        companyDto.setName(company.getName());
        companyDto.setEmployees(company.getEmployees());
        return companyDto;
    }

}
