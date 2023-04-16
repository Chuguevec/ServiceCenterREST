package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.EmployeeDto;
import ru.example.dto.ProjectToSaveDto;
import ru.example.entity.Employee;
import ru.example.entity.Project;
import ru.example.service.EmployeeService;
import ru.example.utils.DtoUtil;
import ru.example.utils.ErrorResponse;
import ru.example.utils.exception.EmployeeNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController( EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDto> getAll() {
        List<Employee> employeeList = employeeService.findAll();
        return employeeList.stream().map(DtoUtil::employeeToEmployeeDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto getOne(@PathVariable("id") int id){
        return DtoUtil.employeeToEmployeeDto(employeeService.findOne(id));
    }

    @GetMapping("/{id}/projects")
    public List<Project> getProjects (@PathVariable("id") int id){
        return null; //TODO
    }

    @PutMapping("/{id}/projects")
    public ResponseEntity<String> putProject (@PathVariable("id") int id, @RequestBody ProjectToSaveDto projectToSaveDto){
        return null; //TODO
    }

    @PostMapping
    public ResponseEntity<String> create (@RequestBody EmployeeDto employeeDto){
        Employee employee = DtoUtil.employeeDtoToEmployee(employeeDto);
        Employee savedEmployee = employeeService.save(employee);
        if(savedEmployee == null) {
            return new ResponseEntity<>("Incorrect values", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success create employee", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update (@PathVariable("id") int id, @RequestBody EmployeeDto employeeDto){
        employeeDto.setId(id);
        Employee updatedEmployee = employeeService.update(DtoUtil.employeeDtoToEmployee(employeeDto));
        if(updatedEmployee == null) {
            return new ResponseEntity<>("Incorrect values", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Success update employee");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String > delete(@PathVariable("id") int id){
        employeeService.delete(id);
        return ResponseEntity.ok("Success delete employee");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EmployeeNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse("Employee with this id not found", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
