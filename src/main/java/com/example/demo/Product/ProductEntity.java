package com.example.demo.Product;

import com.example.demo.Department.DepartmentEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    Double weight;
    String color;
    Double price;
    Long pieces;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="department_id")
    private DepartmentEntity department;



}
