package com.example.demo.Product;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/product")
public class ProductController {
    public final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductEntity>> getAllProduct() {
        List<ProductEntity> listProducts = productService.getAllProducts();
        return new ResponseEntity<>(listProducts,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductEntity p) {
        ProductEntity added= productService.addProduct(p);
        return new ResponseEntity<>(added,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/edit")
    public ResponseEntity<ProductEntity> editProduct(@RequestBody ProductEntity product) {
        ProductEntity productEdited = productService.edit(product);
        return new ResponseEntity<>(productEdited, HttpStatus.OK);
    }
}


