package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.EmployeeDAO;
import ru.example.entity.Company;
import ru.example.entity.Employee;
import ru.example.service.EmployeeService;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public Employee findOne(int id) {
        return employeeDAO.findOne(id);
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
        return employeeDAO.create(employee);
    }

    @Transactional
    @Override
    public Employee update(Employee employee) {
       return employeeDAO.update(employee);

    }

    @Transactional
    @Override
    public void delete(int id) {
        employeeDAO.deleteById(id);
    }
}
