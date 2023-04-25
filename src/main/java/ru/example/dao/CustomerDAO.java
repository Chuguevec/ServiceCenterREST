package ru.example.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.example.entity.Company;
import ru.example.entity.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDAO extends AbstractHibernateDao<Customer> {
    @Autowired
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    @Override
    public List<Customer> findAll() {
        return getCurrentSession().createQuery("SELECT DISTINCT c from Customer c LEFT JOIN FETCH c.company  order by c.id", Customer.class)
                .getResultList();
    }

    public Optional<Customer> findByName(String customerName) {
        return Optional.ofNullable(getCurrentSession().createQuery("SELECT c FROM Customer c where c.name = :name", Customer.class)
                .setParameter("name", customerName).getSingleResult());
    }

    public List<Customer> findAllByPaging(int page, int size) {
        return getCurrentSession().createQuery("SELECT DISTINCT c from Customer c LEFT JOIN FETCH c.company order by c.id", Customer.class)
                .setFirstResult((page * size) - size)
                .setMaxResults(size).getResultList();
    }

    public List<Customer> findAllByCompany(Company company) {
        Query<Customer> query = getCurrentSession()
                .createQuery("SELECT c from Customer c where c.company =: company", Customer.class)
                .setParameter("company", company);
        return query.getResultList();
    }
}
