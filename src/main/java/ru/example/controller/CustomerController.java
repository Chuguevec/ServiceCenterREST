package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.CustomerDto;
import ru.example.dto.ProjectShowDto;
import ru.example.dto.ProjectToSaveDto;
import ru.example.entity.Customer;
import ru.example.entity.Project;
import ru.example.service.CustomerService;
import ru.example.service.ProjectService;
import ru.example.utils.DtoUtil;
import ru.example.utils.ErrorResponse;
import ru.example.utils.exception.CompanyNotFoundException;
import ru.example.utils.exception.CustomerNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final ProjectService projectService;

    @Autowired
    public CustomerController(CustomerService customerService, ProjectService projectService) {
        this.customerService = customerService;
        this.projectService = projectService;
    }

    @GetMapping
    public List<CustomerDto> getAll(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "size", required = false) Integer size,
                                    @RequestParam(value = "name", required = false) String name) {
        List<Customer> customers;

        if (name != null) {
            customers = Collections.singletonList(customerService.findByName(name));
        } else if (page != null && size != null) {
            customers = customerService.findAll(page, size);
        } else {
            customers = customerService.findAll();
        }
        return customers.stream().map(DtoUtil::customerToCustomerDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDto getOne(@PathVariable("id") int id) {
        return DtoUtil.customerToCustomerDto(customerService.findOne(id));
    }

    @GetMapping("/{id}/projects")
    public List<ProjectShowDto> getProjects(@PathVariable("id") int id) {
        Customer customer = customerService.findOne(id);
        List<Project> projects = projectService.findAllByCustomer(customer);
        return projects.stream().map(DtoUtil::projectToProjectShowDto).collect(Collectors.toList());
    }


    @PostMapping
    public ResponseEntity<String> create(@RequestBody CustomerDto customerDto) {
        Customer customer = DtoUtil.customerDtoToCustomer(customerDto);
        customerService.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/projects")
    public ResponseEntity<String> putProject(@PathVariable("id") int id, @RequestBody ProjectToSaveDto projectToSaveDto) {
        Project project = DtoUtil.projectToSaveDtoToProject(projectToSaveDto);
        customerService.addProject(id, project);
        return ResponseEntity.ok("Add project Success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.findOne(id);
        DtoUtil.customerUpdateValueFromDto(customer, customerDto);
        customerService.update(customer);
        return ResponseEntity.ok("Success update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        customerService.delete(id);
        return ResponseEntity.ok("Success delete");
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handelException(CustomerNotFoundException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Customer with this id not found", System.currentTimeMillis()));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handelException(CompanyNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Company with this name not found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}