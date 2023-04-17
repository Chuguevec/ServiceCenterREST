package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.ProjectShowDto;
import ru.example.dto.ProjectToSaveDto;
import ru.example.entity.Project;
import ru.example.service.ProjectService;
import ru.example.utils.DtoUtil;
import ru.example.utils.ErrorResponse;
import ru.example.utils.exception.EmployeeNotFoundException;
import ru.example.utils.exception.ProjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectShowDto> getAll() {
        return projectService.findAll().stream().map(DtoUtil::projectToProjectShowDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectShowDto getOne(@PathVariable("id") int id) {
        return DtoUtil.projectToProjectShowDto(projectService.findOne(id));
    }


    @PostMapping
    public ProjectShowDto create(@RequestBody ProjectToSaveDto projectToSaveDto) {
        Integer savedProjectId = projectService.save(DtoUtil.projectToSaveDtoToProject(projectToSaveDto));
        Project project = projectService.findOne(savedProjectId);
        return DtoUtil.projectToProjectShowDto(project);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody ProjectToSaveDto projectToSaveDto) {
        Project updatedProject = DtoUtil.projectToSaveDtoToProject(projectToSaveDto);
        updatedProject.setId(id);
        projectService.update(updatedProject);
        return ResponseEntity.ok("Success update Project");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        projectService.delete(id);
        return ResponseEntity.ok("Success delete project");
    }

    @PutMapping("/{id}/employees")
    public ResponseEntity<String> putEmployee(@PathVariable("id") int projectId, @RequestParam("employee_id") Integer employeeId) {
        projectService.addProjectToEmployee(projectId, employeeId);
        return ResponseEntity.ok("Success add project");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelException(ProjectNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Project with this id not found", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelException(EmployeeNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Employee with this id not found", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
