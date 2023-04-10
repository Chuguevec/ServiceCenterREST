package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.CompanyDto;
import ru.example.dto.CompanyWithIdDto;
import ru.example.dto.CustomerDto;
import ru.example.entity.Company;
import ru.example.service.CompanyService;
import ru.example.service.CustomerService;
import ru.example.service.EmployeeService;
import ru.example.utils.DtoUtil;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    @Autowired
    public CompanyController(CompanyService companyService, CustomerService customerService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.customerService = customerService;
        this.employeeService = employeeService;
    }


    @GetMapping
    public List<CompanyWithIdDto> getAll() {
        return companyService.findAll().stream().map(DtoUtil::companyToCompanyWithIdDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyWithIdDto show(@PathVariable("id") Integer id) {
        return DtoUtil.companyToCompanyWithIdDto(companyService.findOne(id));
    }

    @GetMapping("/{id}/customers")
    public List<CustomerDto> getCustomersByCompany(@PathVariable("id") Integer id) {
        Company company = companyService.findOne(id);
        return customerService.findAllByCompany(company).stream().map(DtoUtil::customerToCustomerDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CompanyWithIdDto> create(@RequestBody CompanyDto companyDto) {
        Company newCompany = DtoUtil.companyDtoToCompany(companyDto);
        CompanyWithIdDto companyWithIdDto = DtoUtil.companyToCompanyWithIdDto(companyService.save(newCompany));
        return ResponseEntity.ok(companyWithIdDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") int id,
                                         @RequestBody CompanyDto companyDto) {

        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Company updatedCompany = DtoUtil.companyDtoToCompany(companyDto);
        updatedCompany.setId(id);
        Company savedCompany = companyService.update(updatedCompany);
        if (isNull(savedCompany)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        companyService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }


}
