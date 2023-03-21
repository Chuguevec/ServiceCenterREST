package ru.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
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
    List<Project> projects;

    public Customer() {
    }

    public Customer(String name, Integer age, Company company) {
        this.name = name;
        this.age = age;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
