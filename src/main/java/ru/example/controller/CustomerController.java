package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.CustomerDto;
import ru.example.entity.Customer;
import ru.example.service.CustomerService;
import ru.example.utils.DtoUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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


    @PostMapping
    public ResponseEntity<String> create(@RequestBody CustomerDto customerDto) {
        Customer customer = DtoUtil.customerDtoToCustomer(customerDto);
        customerService.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}