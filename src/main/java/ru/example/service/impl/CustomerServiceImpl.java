package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.CompanyDAO;
import ru.example.dao.CustomerDAO;
import ru.example.dao.ProjectDAO;
import ru.example.entity.Company;
import ru.example.entity.Customer;
import ru.example.service.CustomerService;
import ru.example.utils.exception.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private final CompanyDAO companyDAO;
    private final ProjectDAO projectDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, CompanyDAO companyDAO, ProjectDAO projectDAO) {
        this.customerDAO = customerDAO;
        this.companyDAO = companyDAO;
        this.projectDAO = projectDAO;
    }

    @Override
    public Customer findOne(int id) {
        Optional<Customer> optCustomer = customerDAO.findOne(id);
        return optCustomer.orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public List<Customer> findAll(int page, int perPage) {
        return customerDAO.findAllByPaging(page, perPage);
    }

    @Override
    public List<Customer> findAllByCompany(Company company) {
        return customerDAO.findAllByCompany(company);
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        //Иницилизируем Company
        Company company = companyDAO.findByName(customer.getCompany().getName());
        customer.setCompany(company);
        company.getCustomers().add(customer);
        return customerDAO.create(customer);
    }

    @Override
    @Transactional
    public void update(Customer customer) {
        customerDAO.update(customer);
    }

    @Override
    @Transactional
    public void delete(int id) {
        customerDAO.deleteById(id);
    }


}
