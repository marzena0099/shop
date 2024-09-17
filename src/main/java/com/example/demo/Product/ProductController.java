package com.example.demo.Product;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/product")
public class ProductController {
    public final ProductService productService;

    @GetMapping()
    public List<ProductEntity> getAllProduct() {
        return productService.getAllProducts();
    }

    @PostMapping()
    public ProductEntity addProduct(@RequestBody ProductEntity p) {
        return productService.addProduct(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return productService.remove(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<ProductEntity> editProduct(@PathVariable Long id, @RequestBody ProductEntity product) {
        return productService.edit(id, product).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}


