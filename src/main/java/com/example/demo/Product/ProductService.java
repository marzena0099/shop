package com.example.demo.Product;

import com.example.demo.DTO.ProductNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public ProductEntity addProduct(ProductEntity p) {
        productRepository.save(p);
        return p;
    }

    public void remove(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("product not found");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductEntity edit(ProductEntity p) {
        if (!productRepository.existsById(p.getId())) {
            throw new ProductNotFoundException("not found product");
        }
        return productRepository.save(p);
    }
}
