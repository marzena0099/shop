package com.example.demo.TrolleyCart;

import com.example.demo.CartItem.CartItemEntity;
import com.example.demo.Product.ProductEntity;
import com.example.demo.User.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "TrolleyCart")

public class TrolleyCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
//   Long quantity;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "trolleyCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItemEntity> cartItems;



//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "TrolleyCart_Product",
//            joinColumns = @JoinColumn(name = "trolley_cart_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    private List<ProductEntity> products = new ArrayList<>();
//    @OneToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "user_id")
//    UserEntity userEntity;
}
