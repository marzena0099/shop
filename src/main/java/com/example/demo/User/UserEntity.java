package com.example.demo.User;

import com.example.demo.Address.AddressEntity;
import com.example.demo.TrolleyCart.TrolleyCartEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String telephone;
    @OneToOne(mappedBy = "userEntity")
    @JsonBackReference
    private TrolleyCartEntity trolleyCart;

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<AddressEntity> addresses;


}
