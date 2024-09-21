package com.example.demo.Employee;

import com.example.demo.Address.AddressEntity;
import com.example.demo.Department.DepartmentEntity;
import com.example.demo.ENUM.Degree;
import com.example.demo.Position.PositionEntity;
import com.example.demo.Unit.UnitEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@Data
@Table(name = "employee")
@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    String PESEL;
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private DepartmentEntity department;



    @Enumerated(EnumType.STRING)
    private Degree degree;  // Stopień doświadczenia związany z pracownikiem (JUNIOR, MID, SENIOR)

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "position_id")
    private PositionEntity position;  // Powiązanie z tabelą stanowisk (PositionEntity)


@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "address_id", referencedColumnName = "id")
private AddressEntity address;

}
