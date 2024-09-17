package com.example.demo.Department;

import com.example.demo.Employee.EmployeeEntity;
import com.example.demo.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@Data
@Table(name = "department")
@Entity

public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String city;
    String postalCode;


//    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
//    private Set<ProductEntity> products;
//
//    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
//    private Set<EmployeeEntity> employees;

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "department_id")
//    private EmployeeEntity employee;
}
