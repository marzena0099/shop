package com.example.demo.TrolleyCart;

import com.example.demo.Product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrolleyCartRepository extends JpaRepository<TrolleyCartEntity, Long> {
//    TrolleyCartEntity findByProductId(Long id);
//TrolleyCartEntity findByProductEntity_Id(Long productId);
boolean existsByUserEntity_Id(Long userId);

    Optional<TrolleyCartEntity> findByUserEntity_Id(Long userId);

    TrolleyCartEntity getTrolleyCartEntitiesByUserEntity_Id(Long userId);

//    Optional<TrolleyCartEntity> findByUser_Id(Long userId);

}
