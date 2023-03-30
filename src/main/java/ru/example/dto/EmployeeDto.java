package ru.example.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class EmployeeDto {

    private String name;
    private String occupation;
    private Integer salary;
    private LocalDate joinDate;
    private String companyName;
    private List<ProjectDto> projects;
}
