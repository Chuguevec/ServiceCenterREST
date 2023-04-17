package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.CompanyDto;
import ru.example.dto.CompanyWithIdDto;
import ru.example.dto.CustomerDto;
import ru.example.dto.EmployeeDto;
import ru.example.entity.Company;
import ru.example.entity.Customer;
import ru.example.entity.Employee;
import ru.example.service.CompanyService;
import ru.example.service.CustomerService;
import ru.example.service.EmployeeService;
import ru.example.utils.DtoUtil;
import ru.example.utils.ErrorResponse;
import ru.example.utils.exception.CompanyNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
    public List<CompanyWithIdDto> getAll(@RequestParam(value = "name", required = false) String name) {
        List<Company> companies;
        if(name != null){
            companies = Collections.singletonList(companyService.findByName(name));
        } else {
            companies = companyService.findAll();
        }
        return companies.stream().map(DtoUtil::companyToCompanyWithIdDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyWithIdDto getOne(@PathVariable("id") Integer id) {
        Company company = companyService.findOne(id);
        return DtoUtil.companyToCompanyWithIdDto(company);
    }


    @GetMapping("/{id}/customers")
    public List<CustomerDto> getCustomersByCompany(@PathVariable("id") Integer id) {
        Company company = companyService.findOne(id);
        List<Customer> companyCustomers = customerService.findAllByCompany(company);
        return companyCustomers.stream().map(DtoUtil::customerToCustomerDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeDto> getEmployeesCompany(@PathVariable("id") Integer id) {
        Company company = companyService.findOne(id);
        List<Employee> employees = employeeService.findAllByCompany(company);
        return employees.stream().map(DtoUtil::employeeToEmployeeDto).collect(Collectors.toList());
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

        companyService.findOne(id); //Проверяем наличие
        Company updatedCompany = DtoUtil.companyDtoToCompany(companyDto);
        updatedCompany.setId(id);
        companyService.update(updatedCompany);
        return ResponseEntity.status(HttpStatus.OK).body("Success update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        companyService.delete(companyService.findOne(id));
        return ResponseEntity.status(HttpStatus.OK).body("Success delete");

    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handelException(CompanyNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Company with this id not found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
