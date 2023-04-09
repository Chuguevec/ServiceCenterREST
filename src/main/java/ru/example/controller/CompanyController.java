package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.CompanyDto;
import ru.example.dto.CompanyWithIdDto;
import ru.example.service.CompanyService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyWithIdDto> getAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public CompanyWithIdDto show(@PathVariable("id") int id) {
        return companyService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto companyDto) {
        companyService.save(companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") int id,
                                                   @RequestBody CompanyDto companyDto){

        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        CompanyWithIdDto company = companyService.update(companyDto, id);
        if (isNull(company)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        CompanyWithIdDto company = companyService.delete(id);
        if (isNull(company)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }


}
