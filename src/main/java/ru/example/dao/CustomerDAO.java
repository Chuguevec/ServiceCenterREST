package ru.example.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.entity.Customer;

import java.util.List;

@Component
public class CustomerDAO extends AbstractHibernateDao<Customer>{
    @Autowired
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    public Customer findByName(String customerName) {
        return getCurrentSession().createQuery("SELECT c FROM Customer c where c.name = :name", Customer.class)
                .setParameter("name", customerName).getSingleResult();
    }

    public List<Customer> findAllByPaging(int page, int perPage){
        return getCurrentSession().createQuery("SELECT c from Customer c", Customer.class)
                .setFirstResult((page*perPage) - perPage)
                .setMaxResults(perPage).getResultList();
    }
}
