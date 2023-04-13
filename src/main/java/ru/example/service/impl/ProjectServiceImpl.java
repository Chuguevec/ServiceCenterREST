package ru.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.dao.CustomerDAO;
import ru.example.dao.ProjectDAO;
import ru.example.dto.ProjectDto;
import ru.example.entity.Customer;
import ru.example.entity.Project;
import ru.example.service.ProjectService;
import ru.example.utils.exception.ProjectNotFoundException;

import java.util.List;
import java.util.Optional;
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
    public Project findOne(int id) {
        Optional<Project> project = projectDAO.findOne(id);
        return project.orElseThrow(ProjectNotFoundException::new);
    }

    @Override
    public List<ProjectDto> findAll() {
        List<Project> projects = projectDAO.findAll();
        return projects.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    @Override
    public List<Project> findAllByCustomer(Customer customer){
        return projectDAO.findAllByCustomer(customer);
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
