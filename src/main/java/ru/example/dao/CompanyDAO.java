package ru.example.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.example.entity.Company;

import java.util.Optional;

@Repository
public class CompanyDAO extends AbstractHibernateDao<Company> {

    @Autowired
    public CompanyDAO(SessionFactory sessionFactory) {
        super(Company.class, sessionFactory);
    }

    public Optional<Company> findByName(String name) {
        String SQL = "SELECT c FROM Company c where c.name =: name";
        Query<Company> query = getCurrentSession().createQuery(SQL, Company.class);
        query.setParameter("name", name);
        Company company = query.uniqueResult();
        return Optional.ofNullable(company);
    }
}
