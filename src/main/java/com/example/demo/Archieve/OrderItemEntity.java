package com.example.demo.Archieve;

import com.example.demo.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="orderItem")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OrderEntity orderEntity; // Powiązanie z zamówieniem

    @ManyToOne
    private ProductEntity product; // Produkt, który został zamówiony

    private int quantity; // Ilość zamówionych sztuk produktu

    private  Double priceAtPurchase; // Cena produktu w momencie zakupu

}

