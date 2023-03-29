package ru.example.service;

import ru.example.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto findOne(int id);

    List<ProjectDto> findAll();

    Integer save(ProjectDto projectDto);

    void update(ProjectDto projectDto);

    void delete(int id);
}
