package ru.example.utils;

import org.springframework.web.bind.annotation.RequestParam;
import ru.example.dto.CompanyDto;
import ru.example.dto.CompanyWithIdDto;
import ru.example.dto.CustomerDto;
import ru.example.dto.ProjectDto;
import ru.example.entity.Company;
import ru.example.entity.Customer;
import ru.example.entity.Project;

import java.util.ArrayList;
import java.util.List;

public class DtoUtil {

    public static Company companyDtoToCompany(CompanyDto companyDto){
        Company company = new Company();
        company.setName(companyDto.getName());
        return company;
    }

    public static CompanyWithIdDto companyToCompanyWithIdDto(Company company){
        CompanyWithIdDto companyDto = new CompanyWithIdDto();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        return companyDto;
    }

    public static List<CustomerDto> customerListToCustomerDtoList(List<Customer> customers){
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtoList.add(customerToCustomerDto(customer));
        }
        return customerDtoList;
    }

    public static CustomerDto customerToCustomerDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setAge(customer.getAge());
        customerDto.setCompanyName(customer.getCompany().getName());
        return customerDto;
    }
        //Не сетаем Company
    public static Customer customerDtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setAge(customerDto.getAge());
        Company company = new Company();
        company.setName(customerDto.getName());
        customer.setCompany(company);
        return customer;
    }

    private Customer dtoToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setAge(customerDto.getAge());
        return customer;
    }

    private List<ProjectDto> projectListToProjectDtoList (List<Project> projects) {
        List<ProjectDto> projectDtoList = new ArrayList<>();

        for (Project project : projects) {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setName(project.getName());
            projectDto.setPrice(project.getPrice());
        }
        return projectDtoList;
    }

}
