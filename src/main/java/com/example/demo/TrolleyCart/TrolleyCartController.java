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
    public ResponseEntity<TrolleyCartEntity> buy(@RequestParam Long userId, @RequestParam(required=false) Long BillingId , @RequestParam Long shippingId) {
        TrolleyCartEntity trolley = trolleyCartService.buy(userId, BillingId, shippingId);
        return new ResponseEntity<>(trolley, HttpStatus.OK);


    }

}