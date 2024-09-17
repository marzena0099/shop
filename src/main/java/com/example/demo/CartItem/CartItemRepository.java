package com.example.demo.CartItem;

import com.example.demo.Product.ProductEntity;
import com.example.demo.TrolleyCart.TrolleyCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    boolean existsByProduct_Id(Long productId);

    Optional<CartItemEntity> findByProductId(Long id);

    Optional<CartItemEntity> findByProductIdAndTrolleyCartId(Long productId, Long trolleyCartId);
}
