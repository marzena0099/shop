package com.example.demo.Employee;

import com.example.demo.Department.DepartmentEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;
@Data
@Table(name = "employee")
@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    String city;
    String PESEL;
    String position;
    String degree;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;


}
