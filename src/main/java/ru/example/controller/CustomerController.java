package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.CustomerDto;
import ru.example.dto.ProjectDto;
import ru.example.entity.Customer;
import ru.example.entity.Project;
import ru.example.service.CustomerService;
import ru.example.service.ProjectService;
import ru.example.utils.DtoUtil;

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
                                    @RequestParam(value = "per_page", required = false) Integer perPage) {
        if (page != null && perPage != null) {
            return customerService.findAll(page, perPage).stream().map(DtoUtil::customerToCustomerDto).collect(Collectors.toList());
        } else {
            return customerService.findAll().stream().map(DtoUtil::customerToCustomerDto).collect(Collectors.toList());
        }

    }

    @GetMapping("/{id}")
    public CustomerDto getOne(@PathVariable("id") int id) {
        return DtoUtil.customerToCustomerDto(customerService.findOne(id));
    }

    @GetMapping("/{id}/projects")
    public List<ProjectDto> getProjects (@PathVariable("id") int id){
        Customer customer = customerService.findOne(id);
        List<Project> projects = projectService.findAllByCustomer(customer);
        return projects.stream().map(DtoUtil::projectToProjectDto).collect(Collectors.toList());
    }


    @PostMapping
    public ResponseEntity<String> create(@RequestBody CustomerDto customerDto) {
        Customer customer = DtoUtil.customerDtoToCustomer(customerDto);
        customerService.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}