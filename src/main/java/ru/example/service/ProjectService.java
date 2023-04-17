package ru.example.service;


import ru.example.entity.Customer;
import ru.example.entity.Project;

import java.util.List;

public interface ProjectService {
    Project findOne(int id);

    List<Project> findAll();

    Integer save(Project project);

    void update(Project project);

    void delete(int id);

    List<Project> findAllByCustomer(Customer customer);

    void addProjectToEmployee(int projectId, Integer employeeId);

    List<Project> findAllWithPagination(Integer page, Integer size);
}
