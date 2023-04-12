package ru.example.dto;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class EmployeeDto {

    private Integer id;
    private String name;
    private String occupation;
    private Integer salary;
    private String companyName;
  //  private List<ProjectDto> projects;
}
