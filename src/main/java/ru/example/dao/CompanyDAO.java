package ru.example.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.entity.Company;

@Component
public class CompanyDAO extends AbstractHibernateDao<Company>{

    @Autowired
    public CompanyDAO(SessionFactory sessionFactory) {
        super(Company.class, sessionFactory);
    }

    public Company findByName(String name){
        return getCurrentSession().createQuery("SELECT c FROM Company c where c.name = :name", Company.class)
                .setParameter("name", name).getSingleResult();
    }
}
