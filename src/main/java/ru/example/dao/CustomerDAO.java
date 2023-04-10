package ru.example.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.entity.Company;
import ru.example.entity.Customer;

import java.util.List;

@Component
public class CustomerDAO extends AbstractHibernateDao<Customer> {
    @Autowired
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    public Customer findByName(String customerName) {
        return getCurrentSession().createQuery("SELECT c FROM Customer c where c.name = :name", Customer.class)
                .setParameter("name", customerName).getSingleResult();
    }

    public List<Customer> findAllByPaging(int page, int perPage) {
        return getCurrentSession().createQuery("SELECT c from Customer c", Customer.class)
                .setFirstResult((page * perPage) - perPage)
                .setMaxResults(perPage).getResultList();
    }

    public List<Customer> findAllByCompany(Company company) {
        Query<Customer> query = getCurrentSession()
                .createQuery("SELECT c from Customer c where c.company =: company", Customer.class)
                .setParameter("company", company);
        return query.getResultList();
    }
}
