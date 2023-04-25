package ru.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(mappedBy = "customer")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Project> projects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!id.equals(customer.id)) return false;
        if (!name.equals(customer.name)) return false;
        if (!age.equals(customer.age)) return false;
        if (!company.equals(customer.company)) return false;
        return Objects.equals(projects, customer.projects);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + age.hashCode();
        result = 31 * result + company.hashCode();
        return result;
    }
}
