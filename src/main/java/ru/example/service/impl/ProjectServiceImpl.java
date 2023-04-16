package ru.example.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.CustomerDAO;
import ru.example.dao.ProjectDAO;
import ru.example.entity.Customer;
import ru.example.entity.Project;
import ru.example.service.ProjectService;
import ru.example.utils.exception.ProjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDAO projectDAO;
    private final CustomerDAO customerDAO;

    @Autowired
    public ProjectServiceImpl(ProjectDAO projectDAO, CustomerDAO customerDAO) {
        this.projectDAO = projectDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public Project findOne(int id) {
        Optional<Project> project = projectDAO.findOne(id);
        return project.orElseThrow(ProjectNotFoundException::new);
    }

    @Override
    public List<Project> findAll() {
        return projectDAO.findAll();
    }
    @Override
    public List<Project> findAllByCustomer(Customer customer){
        List<Project> projects = projectDAO.findAllByCustomer(customer);
        projects.forEach(project -> Hibernate.initialize(project.getEmployees()));
        return projects;
    }

    @Override
    @Transactional
    public Integer save(Project project) {
        return projectDAO.create(project).getId();
    }

    @Override
    @Transactional
    public void update(Project project) {
        projectDAO.update(project);
    }

    @Override
    @Transactional
    public void delete(int id) {

    }
}
