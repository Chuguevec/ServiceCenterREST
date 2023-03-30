package ru.example.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class CustomerDto {
    private String name;
    private Integer age;
    private String companyName;
    private List<ProjectDto> projects;
}
