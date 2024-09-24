package com.example.demo.TrolleyCart;
import com.example.demo.CartItem.CartItemEntity;
import com.example.demo.DTO.AddProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        CartItemEntity cartItem = trolleyCartService.add(productId, quantity, userId);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }


    @PostMapping("/buy")
    public ResponseEntity<TrolleyCartEntity> buy(@RequestParam Long userId, @RequestParam(required=false) Long BillingId , @RequestParam Long shippingId) {
        TrolleyCartEntity trolley = trolleyCartService.buy(userId, BillingId, shippingId);
        return new ResponseEntity<>(trolley, HttpStatus.OK);


    }

}