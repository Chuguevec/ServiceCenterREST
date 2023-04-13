package ru.example.service;

import ru.example.dto.ProjectDto;
import ru.example.entity.Customer;
import ru.example.entity.Project;

import java.util.List;

public interface ProjectService {
    Project findOne(int id);

    List<ProjectDto> findAll();

    Integer save(ProjectDto projectDto);

    void update(ProjectDto projectDto);

    void delete(int id);

    List<Project> findAllByCustomer(Customer customer);
}
