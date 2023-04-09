package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.dao.CustomerDAO;
import ru.example.dao.ProjectDAO;
import ru.example.dto.ProjectDto;
import ru.example.entity.Customer;
import ru.example.entity.Project;
import ru.example.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDAO projectDAO;
    private final CustomerDAO customerDAO;

    @Autowired
    public ProjectServiceImpl(ProjectDAO projectDAO, CustomerDAO customerDAO) {
        this.projectDAO = projectDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public ProjectDto findOne(int id) {
        return entityToDto(projectDAO.findOne(id));
    }

    @Override
    public List<ProjectDto> findAll() {
        List<Project> projects = projectDAO.findAll();
        return projects.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<ProjectDto> findAllByCustomer(Customer customer){
        List<Project> projects = projectDAO.findAllByCustomer(customer);
        return projects.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public Integer save(ProjectDto projectDto) {
        return projectDAO.create(dtoToEntity(projectDto)).getId();
    }

    @Override
    public void update(ProjectDto projectDto) {

    }

    @Override
    public void delete(int id) {

    }

    private ProjectDto entityToDto(Project project){
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(project.getName());
        projectDto.setPrice(project.getPrice());
        projectDto.setCustomerName(project.getCustomer().getName());
        return projectDto;
    }

    private Project dtoToEntity(ProjectDto projectDto){
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setPrice(projectDto.getPrice());
        Customer customer = customerDAO.findByName(projectDto.getCustomerName());
        project.setCustomer(customer);
        return project;
    }
}
