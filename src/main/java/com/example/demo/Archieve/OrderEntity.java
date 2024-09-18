package com.example.demo.Archieve;

import com.example.demo.Address.AddressEntity;
import com.example.demo.ENUM.OrderStatus;
import com.example.demo.User.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "OrderTable")
@Entity
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private AddressEntity shippingAddressEntity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id")
    private AddressEntity billingAddressEntity;
}
