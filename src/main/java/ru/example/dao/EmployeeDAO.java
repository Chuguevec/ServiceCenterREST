package ru.example.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.entity.Employee;

@Component
public class EmployeeDAO extends AbstractHibernateDao<Employee> {
    @Autowired
    public EmployeeDAO(SessionFactory sessionFactory) {
        super(Employee.class, sessionFactory);
    }
}