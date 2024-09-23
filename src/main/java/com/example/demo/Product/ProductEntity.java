package com.example.demo.Product;

import com.example.demo.Department.DepartmentEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@Table(name = "product_entity")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double weight;
    private String color;
    private Double price;
    private Long pieces;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="department_id", referencedColumnName = "id")
    private DepartmentEntity department;



}
