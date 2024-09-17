package com.example.demo.Unit;

import com.example.demo.Department.DepartmentEntity;
import com.example.demo.Employee.EmployeeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@Data
@Entity
public class UnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String city;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "deparment_id")
    private DepartmentEntity department;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="employee_id")
    private EmployeeEntity employee;
}
