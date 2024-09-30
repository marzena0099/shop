package com.example.demo.Employee;

import com.example.demo.Address.AddressEntity;
import com.example.demo.Department.DepartmentEntity;
import com.example.demo.ENUM.Degree;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "employee")
@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String pesel;
    private String position;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;


    @Enumerated(EnumType.STRING)
    private Degree degree;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

}
