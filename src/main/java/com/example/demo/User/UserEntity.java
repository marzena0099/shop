package com.example.demo.User;

import com.example.demo.TrolleyCart.TrolleyCartEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "userEntity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TrolleyCartEntity trolleyCart;


}
