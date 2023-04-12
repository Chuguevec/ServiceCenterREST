package ru.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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
