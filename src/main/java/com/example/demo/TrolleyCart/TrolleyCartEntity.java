package com.example.demo.TrolleyCart;

import com.example.demo.CartItem.CartItemEntity;

import com.example.demo.User.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "TrolleyCart")

public class TrolleyCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonIgnore
    private UserEntity userEntity;

    @OneToMany(mappedBy = "trolleyCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<CartItemEntity> cartItems;







}
