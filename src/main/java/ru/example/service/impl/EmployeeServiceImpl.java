package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.CompanyDAO;
import ru.example.dao.EmployeeDAO;
import ru.example.entity.Company;
import ru.example.entity.Employee;
import ru.example.service.EmployeeService;
import ru.example.utils.exception.CompanyNotFoundException;
import ru.example.utils.exception.EmployeeNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final CompanyDAO companyDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO, CompanyDAO companyDAO) {
        this.employeeDAO = employeeDAO;
        this.companyDAO = companyDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public Employee findOne(int id) {
        Optional<Employee> optEmployee = employeeDAO.findOne(id);
        return optEmployee.orElseThrow(EmployeeNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findAllByCompany(Company company) {
        return employeeDAO.findAllByCompany(company);
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        Company company = companyDAO.findByName(employee.getCompany().getName()).orElseThrow(CompanyNotFoundException::new);
        employee.setCompany(company);
        Employee savedEmployee = employeeDAO.create(employee);
        company.getEmployees().add(savedEmployee);
        return savedEmployee;
    }

    @Transactional
    @Override
    public Employee update(Employee employee) {
        employeeDAO.findOne(employee.getId()).orElseThrow(EmployeeNotFoundException::new);
        Company company = companyDAO.findByName(employee.getCompany().getName()).orElseThrow(CompanyNotFoundException::new);
        employee.setCompany(company);
        return employeeDAO.update(employee).orElseThrow(EmployeeNotFoundException::new);

    }

    @Transactional
    @Override
    public void delete(int id) {
        employeeDAO.findOne(id).orElseThrow(EmployeeNotFoundException::new);
        employeeDAO.deleteById(id);
    }
}
