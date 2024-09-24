package com.example.demo.TrolleyCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrolleyCartRepository extends JpaRepository<TrolleyCartEntity, Long> {

    boolean existsByUserEntity_Id(Long userId);

    Optional<TrolleyCartEntity> findByUserEntity_Id(Long userId);

    TrolleyCartEntity getTrolleyCartEntitiesByUserEntity_Id(Long userId);


}
