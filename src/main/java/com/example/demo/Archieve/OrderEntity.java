package com.example.demo.Archieve;

import com.example.demo.Address.AddressEntity;
import com.example.demo.ENUM.OrderStatus;
import com.example.demo.Employee.EmployeeEntity;
import com.example.demo.User.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "order_table")
@Entity
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Powiązanie z użytkownikiem
    private UserEntity user;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "employee_id", nullable = true )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private EmployeeEntity employee;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private AddressEntity shippingAddressEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id")
    private AddressEntity billingAddressEntity;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();


}
