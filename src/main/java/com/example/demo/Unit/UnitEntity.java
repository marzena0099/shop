package com.example.demo.Unit;

import com.example.demo.Department.DepartmentEntity;
import com.example.demo.Employee.EmployeeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.bind.annotation.RestController;

@Data
@Entity
@Table(name = "unit_entity")
public class UnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String postalCode;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private EmployeeEntity employee;
}
