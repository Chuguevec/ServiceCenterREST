package ru.example.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dao.CustomerDAO;
import ru.example.dao.EmployeeDAO;
import ru.example.dao.ProjectDAO;
import ru.example.entity.Customer;
import ru.example.entity.Employee;
import ru.example.entity.Project;
import ru.example.service.ProjectService;
import ru.example.utils.exception.EmployeeNotFoundException;
import ru.example.utils.exception.ProjectNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDAO projectDAO;
    private final EmployeeDAO employeeDAO;
    private final CustomerDAO customerDAO;

    @Autowired
    public ProjectServiceImpl(ProjectDAO projectDAO, EmployeeDAO employeeDAO, CustomerDAO customerDAO) {
        this.projectDAO = projectDAO;
        this.employeeDAO = employeeDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public Project findOne(int id) {
        Project project = projectDAO.findOne(id).orElseThrow(ProjectNotFoundException::new);
        Hibernate.initialize(project.getEmployees());
        return project;
    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = projectDAO.findAll();
        projects.forEach(project -> Hibernate.initialize(project.getEmployees()));
        return projects;
    }

    @Override
    public List<Project> findAllByCustomer(Customer customer) {
        List<Project> projects = projectDAO.findAllByCustomer(customer);
        projects.forEach(project -> Hibernate.initialize(project.getEmployees()));
        return projects;
    }

    @Transactional
    @Override
    public void addProjectToEmployee(int projectId, Integer employeeId) {
        Project project = projectDAO.findOne(projectId).orElseThrow(ProjectNotFoundException::new);
        Employee employee = employeeDAO.findOne(employeeId).orElseThrow(EmployeeNotFoundException::new);
        project.getEmployees().add(employee);
        employee.getProjects().add(project);
    }

    @Override
    @Transactional
    public Integer save(Project project) {
        Customer customer = customerDAO.findByName(project.getCustomer().getName());
        project.setCustomer(customer);
        return projectDAO.create(project).getId();
    }

    @Override
    @Transactional
    public void update(Project updatedProject) {
        //Check id
        projectDAO.findOne(updatedProject.getId()).orElseThrow(ProjectNotFoundException::new);
        //Initialize Customer
        Customer customer = customerDAO.findByName(updatedProject.getCustomer().getName());
        //сетаем зависимости с обоих сторон
        customer.getProjects().add(updatedProject);
        updatedProject.setCustomer(customer);
        //обновляем project
        projectDAO.update(updatedProject);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Project project = projectDAO.findOne(id).orElseThrow(ProjectNotFoundException::new);
        List<Employee> employees = project.getEmployees();
        if (!employees.isEmpty()) {
            employees.forEach(employee -> employee.getProjects().remove(project));
        }
        projectDAO.deleteById(id);
    }
}
