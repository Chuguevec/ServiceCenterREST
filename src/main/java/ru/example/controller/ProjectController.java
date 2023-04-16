package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.EmployeeDto;
import ru.example.dto.ProjectShowDto;
import ru.example.dto.ProjectToSaveDto;
import ru.example.service.ProjectService;
import ru.example.utils.DtoUtil;

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
    public List<ProjectShowDto> getAll(){
        return projectService.findAll().stream().map(DtoUtil::projectToProjectDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectShowDto getOne(@PathVariable("id") int id){
        return null; //TODO
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeDto> getEmployees(@PathVariable("id") int id){
        return null; //TODO
    }

    @PostMapping
    public ResponseEntity<String> create (@RequestBody ProjectToSaveDto projectToSaveDto){
        return null; //TODO
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update (@PathVariable("id") int id, @RequestBody ProjectToSaveDto projectToSaveDto){
        return null; //TODO
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable("id") int id){
        projectService.delete(id);
        return ResponseEntity.ok("Success delete project");
    }

    @PutMapping("/{id}/employees")
    public ResponseEntity<String> putEmployee(@PathVariable("id") int id, @RequestBody EmployeeDto employeeDto){
        return null; //TODO
    }
}
