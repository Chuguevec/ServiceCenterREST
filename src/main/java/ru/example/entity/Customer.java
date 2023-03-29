package ru.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(schema = "public", name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    @ManyToOne
    @JoinColumn (name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(mappedBy = "customer")
    private List<Project> projects;

}
