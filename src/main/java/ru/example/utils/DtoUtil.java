package ru.example.utils;

import ru.example.dto.*;
import ru.example.entity.Company;
import ru.example.entity.Customer;
import ru.example.entity.Employee;
import ru.example.entity.Project;

import java.util.stream.Collectors;

public class DtoUtil {

    public static Company companyDtoToCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setName(companyDto.getName());
        return company;
    }

    public static CompanyWithIdDto companyToCompanyWithIdDto(Company company) {
        CompanyWithIdDto companyDto = new CompanyWithIdDto();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        return companyDto;
    }

    public static CustomerDto customerToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setAge(customer.getAge());
        customerDto.setCompanyName(customer.getCompany().getName());
        return customerDto;
    }

    public static Customer customerDtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setAge(customerDto.getAge());
        Company company = new Company(); // need persist in service
        company.setName(customerDto.getName());
        customer.setCompany(company);
        return customer;
    }

    public static Employee employeeDtoToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());
        employee.setOccupation(employeeDto.getOccupation());
        Company company = new Company(); // need persist in service
        company.setName(employeeDto.getCompanyName());
        employee.setCompany(company);
        return employee;
    }

    public static EmployeeDto employeeToEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setOccupation(employee.getOccupation());
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setCompanyName(employee.getCompany().getName());
        return employeeDto;
    }

    public static ProjectShowDto projectToProjectShowDto(Project project) {
        ProjectShowDto projectDto = new ProjectShowDto();
        projectDto.setId(project.getId());
        projectDto.setPrice(project.getPrice());
        projectDto.setName(project.getName());
        projectDto.setCustomerName(project.getCustomer().getName());
        projectDto.setEmployees(project.getEmployees().stream()
                .map(DtoUtil::employeeToEmployeeDto).collect(Collectors.toList()));
        return projectDto;
    }

    public static Project projectToSaveDtoToProject(ProjectToSaveDto projectToSaveDto) {
        Project project = new Project();
        project.setPrice(projectToSaveDto.getPrice());
        project.setName(projectToSaveDto.getName());
        //создаем Заказчика с именем для добавления в проект. В сервисе выполним поиск по имени и добавим реальный объект в проект.
        Customer customer = new Customer();
        customer.setName(projectToSaveDto.getCustomerName());
        project.setCustomer(customer);
        return project;
    }

    public static void customerUpdateValueFromDto(Customer customer, CustomerDto customerDto) {
        customer.setName(customerDto.getName());
        customer.setAge(customerDto.getAge());
        if (!customer.getCompany().getName().equals(customerDto.getCompanyName())) {
            Company company = new Company();
            company.setName(customerDto.getCompanyName());
            customer.setCompany(company);
        }
    }

}
