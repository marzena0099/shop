package com.example.demo.CartItem;

import com.example.demo.Product.ProductEntity;
import com.example.demo.TrolleyCart.TrolleyCartEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_item")
@Setter
@Getter
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    private Long quantity;

    // Relacja wiele do jednego z koszykiem
    @ManyToOne
    @JoinColumn(name = "trolley_cart_id")
    @JsonBackReference
    @ToString.Exclude
    private TrolleyCartEntity trolleyCart;

    // Relacja wiele do jednego z produktem
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;


}

