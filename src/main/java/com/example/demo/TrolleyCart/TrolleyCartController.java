package com.example.demo.TrolleyCart;

import com.example.demo.Address.AddressEntity;
import com.example.demo.CartItem.CartItemEntity;
import com.example.demo.DTO.AddProductRequest;
import com.example.demo.DTO.InsufficientProductQuantityException;
import com.example.demo.DTO.ProductNotFoundException;
import com.example.demo.DTO.TrolleyCartNotFoundException;
import com.example.demo.Product.ProductEntity;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trolleyCart")
public class TrolleyCartController {
    private final TrolleyCartService trolleyCartService;
@PostMapping("/nc/{id}")
public void nc(@PathVariable Long id){
    trolleyCartService.b2(id);
}
    @PostMapping("/addProduct")
    public ResponseEntity<Object> add(@RequestBody AddProductRequest addProductRequest) {
        Long productId = addProductRequest.getProductId();
        Long quantity = addProductRequest.getIlosc();
        Long userId = addProductRequest.getUzytkownikId();

        try {
            // Dodaj przedmiot do koszyka
            CartItemEntity cartItem = trolleyCartService.add(productId, quantity, userId);
//                    .orElseThrow(() -> new ProductNotFoundException("Product not found in cart"));

            // Zwróć odpowiedź z dodanym przedmiotem
            return ResponseEntity.ok(cartItem);
        } catch (ProductNotFoundException e) {
            // Obsługuje przypadek, gdy produkt nie został znaleziony
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InsufficientProductQuantityException e) {
            // Obsługuje przypadek, gdy ilość produktu jest niewystarczająca
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Obsługuje inne ogólne wyjątki
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestParam Long userId, @RequestParam Long BillingId, @RequestParam Long shippingId) {
        try {
            trolleyCartService.buy(userId, BillingId, shippingId);
            return ResponseEntity.ok("Purchase successful");
        } catch (InsufficientProductQuantityException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (TrolleyCartNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}

