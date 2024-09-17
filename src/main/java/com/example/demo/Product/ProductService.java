package com.example.demo.Product;

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

    public ProductEntity addProduct(ProductEntity p) {
        productRepository.save(p);
        return p;
    }

    public Optional<Object> remove(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return product;
                });
    }
@Transactional
    public Optional<ProductEntity> edit(Long id, ProductEntity p) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(!product.isPresent()){
            return Optional.empty();
        }
        else {
            ProductEntity oldProduct =product.get();
            oldProduct.setName(p.getName());
//            oldProduct.setDepartment(p.getDepartment());
            oldProduct.setColor(p.getColor());
            oldProduct.setPieces(p.getPieces());
            oldProduct.setPrice(p.getPrice());
            oldProduct.setWeight(p.getWeight());
            productRepository.save(oldProduct);
            return Optional.of(oldProduct);
        }
    }
}
