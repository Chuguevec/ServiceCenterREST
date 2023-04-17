package ru.example.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.example.entity.Company;
import ru.example.entity.Employee;

import java.util.List;

@Repository
public class EmployeeDAO extends AbstractHibernateDao<Employee> {
    @Autowired
    public EmployeeDAO(SessionFactory sessionFactory) {
        super(Employee.class, sessionFactory);
    }

    public List<Employee> findAllByCompany(Company company) {
        return getCurrentSession()
                .createQuery("select e from Employee e where e.company =: company", Employee.class)
                .setParameter("company", company)
                .getResultList();
    }
}
