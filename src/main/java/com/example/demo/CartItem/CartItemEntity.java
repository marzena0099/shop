package com.example.demo.CartItem;

import com.example.demo.Product.ProductEntity;
import com.example.demo.TrolleyCart.TrolleyCartEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cart_item")
@Data
public class CartItemEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identyfikator elementu koszyka

    private int quantity; // Ilość przedmiotu w koszyku

    // Relacja wiele do jednego z koszykiem
    @ManyToOne
    @JoinColumn(name = "trolley_cart_id")
    private TrolleyCartEntity trolleyCart;

    // Relacja wiele do jednego z produktem
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;


}

