package com.example.demo.Address;

import com.example.demo.ENUM.AddressType;
import com.example.demo.Employee.EmployeeEntity;
import com.example.demo.User.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "addresses")
@Data
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String postalCode;
    private String country;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;


    @OneToOne(mappedBy = "address")
    private EmployeeEntity employee;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}