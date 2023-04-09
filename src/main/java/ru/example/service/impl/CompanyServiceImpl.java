package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.CompanyDAO;
import ru.example.dto.CompanyDto;
import ru.example.dto.CompanyWithIdDto;
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
    public CompanyWithIdDto findOne(int id) {
        Company company = companyDAO.findOne(id);
        return DtoUtil.companyToCompanyWithIdDto(company);
    }
    @Override
    public List<CompanyWithIdDto> findAll(){
        return companyDAO.findAll().stream().map(DtoUtil::companyToCompanyWithIdDto).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public Integer save (CompanyDto companyDto){
        return companyDAO.create(DtoUtil.companyDtoToCompany(companyDto)).getId();
    }
    @Transactional
    @Override
    public CompanyWithIdDto update (CompanyDto companyDto, int id){
        Company company = companyDAO.findOne(id);
        company.setName(companyDto.getName());
        return DtoUtil.companyToCompanyWithIdDto(company);
    }
    @Transactional
    @Override
    public CompanyWithIdDto delete (int id){
        Company company = companyDAO.findOne(id);
        if(company == null){
            return null;
        }
        companyDAO.deleteById(id);
        return DtoUtil.companyToCompanyWithIdDto(company);
    }



}
