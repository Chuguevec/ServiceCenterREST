package ru.example.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.example.entity.Customer;
import ru.example.entity.Project;

import java.util.List;

@Repository
public class ProjectDAO extends AbstractHibernateDao<Project> {
    @Autowired
    public ProjectDAO(SessionFactory sessionFactory) {
        super(Project.class, sessionFactory);
    }

    public List<Project> findAllByCustomer(Customer customer) {
        return getCurrentSession().createQuery("SELECT p FROM Project p where p.customer =:customer", Project.class)
                .setParameter("customer", customer).getResultList();
    }
}
