package ru.example.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "company")
    private List<Customer> customers;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

}
