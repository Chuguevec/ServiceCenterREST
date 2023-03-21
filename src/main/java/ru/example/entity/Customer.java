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

    private String email;

    private String phone;

    private List<Record> records;


}
