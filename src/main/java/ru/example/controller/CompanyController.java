package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.dto.CompanyDto;
import ru.example.dto.RestResponse;
import ru.example.service.CompanyService;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public String getAll(){

    List<CompanyDto> companies = companyService.findAll();
        RestResponse response = new RestResponse(companies.toString());
        return response.toString();
}
}
