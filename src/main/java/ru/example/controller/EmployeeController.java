package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.EmployeeDto;
import ru.example.dto.ProjectShowDto;
import ru.example.entity.Company;
import ru.example.entity.Employee;
import ru.example.entity.Project;
import ru.example.service.CompanyService;
import ru.example.service.EmployeeService;
import ru.example.utils.DtoUtil;
import ru.example.utils.ErrorResponse;
import ru.example.utils.exception.CompanyNotFoundException;
import ru.example.utils.exception.EmployeeNotFoundException;
import ru.example.utils.exception.ProjectNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @GetMapping
    public List<EmployeeDto> getAll(@RequestParam(value = "company_name", required = false) String companyName) {
        List<Employee> employeeList;
        if (companyName != null) {
            Company company = companyService.findByName(companyName);
            employeeList = employeeService.findAllByCompany(company);
            Map<String, String > map = new HashMap<>();
        } else {
            employeeList = employeeService.findAll();
        }
        return employeeList.stream().map(DtoUtil::employeeToEmployeeDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto getOne(@PathVariable("id") int id) {
        return DtoUtil.employeeToEmployeeDto(employeeService.findOne(id));
    }

    @GetMapping("/{id}/projects")
    public List<ProjectShowDto> getProjects(@PathVariable("id") int id) {
        List<Project> projects = employeeService.getEmployeeProjects(id);
        return projects.stream().map(DtoUtil::projectToProjectShowDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}/projects")
    public ResponseEntity<String> putProject(@PathVariable("id") int employeeId, @RequestParam("project_id") Integer projectId) {
        employeeService.addProjectToEmployee(employeeId, projectId);
        return ResponseEntity.ok("Success add Project");
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeDto employeeDto) {
        Employee employee = DtoUtil.employeeDtoToEmployee(employeeDto);
        Employee savedEmployee = employeeService.save(employee);
        if (savedEmployee == null) {
            return new ResponseEntity<>("Incorrect values", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success create employee", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody EmployeeDto employeeDto) {
        employeeDto.setId(id);
        Employee updatedEmployee = employeeService.update(DtoUtil.employeeDtoToEmployee(employeeDto));
        if (updatedEmployee == null) {
            return new ResponseEntity<>("Incorrect values", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Success update employee");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        employeeService.delete(id);
        return ResponseEntity.ok("Success delete employee");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EmployeeNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Employee with this id not found", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelException(ProjectNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Project with this id not found", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelException(CompanyNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Company with this name not found", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
