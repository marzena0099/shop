package com.example.demo.Archieve;

import com.example.demo.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long quantity;

    private Double priceAtPurchase;


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)  // Powiązanie z zamówieniem, gdzie "order_id" to klucz obcy
    private OrderEntity orderEntity; // Powiązanie z zamówieniem

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)  // Powiązanie z produktem, gdzie "product_id" to klucz obcy
    private ProductEntity product; // Produkt, który został zamówiony

}

