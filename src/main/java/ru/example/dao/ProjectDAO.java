package ru.example.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.entity.Project;

@Component
public class ProjectDAO extends AbstractHibernateDao<Project>{
    @Autowired
    public ProjectDAO(SessionFactory sessionFactory) {
        super(Project.class, sessionFactory);
    }
}
