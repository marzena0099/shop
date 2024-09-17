package com.example.demo.TrolleyCart;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.demo.CartItem.CartItemEntity;
import com.example.demo.CartItem.CartItemRepository;
import com.example.demo.DTO.InsufficientProductQuantityException;
import com.example.demo.DTO.ProductNotFoundException;
import com.example.demo.Product.ProductEntity;
import com.example.demo.Product.ProductRepository;
import com.example.demo.User.UserEntity;
import com.example.demo.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrolleyCartService {
    private final TrolleyCartRepository trolleyCartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

//    @Transactional
//    public Optional<List<ProductEntity>> buy(List<TrolleyCartEntity> listP) {
//        List<ProductEntity> foundPRodu = productRepository
//                .findAllById(listP.stream()
//                        .map(e->e.getId());
//        if (foundPRodu.isEmpty()) {
//            return Optional.empty();
//        }
//        foundPRodu.forEach(product -> product.setPieces(product.getPieces() - trolleyCartRepository
//                .findByProductId(product.getId()).getQuantity()));
//        productRepository.saveAll(foundPRodu);
//        return Optional.of(foundPRodu);
//    }

    @Transactional
    public Optional<CartItemEntity> add(Long productId, int quantity, Long idUser) {
        Optional<UserEntity> user = userRepository.findById(idUser);
        // Sprawdzamy, czy ilość produktu jest dostępna
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (product.getPieces() < quantity) {
            throw new InsufficientProductQuantityException("Not enough pieces of product");
        }

        // Sprawdzamy, czy użytkownik ma już koszyk
        TrolleyCartEntity trolleyCart = trolleyCartRepository.findByUserEntity_Id(idUser)
                .orElseGet(() -> {
                    // Tworzymy nowy koszyk, jeśli go nie ma
                    TrolleyCartEntity newCart = new TrolleyCartEntity();
                    newCart.setUserEntity(user.get());
                    newCart.setCartItems(new ArrayList<>()); // Pusta lista produktów na początek
                    trolleyCartRepository.save(newCart);
                    return newCart;
                });
        // Sprawdzamy, czy ten produkt już istnieje w koszyku
        Optional<CartItemEntity> existingCartItem = cartItemRepository.findByProductIdAndTrolleyCartId(productId, trolleyCart.getId()); //czy nie trzeba id

        if (existingCartItem.isPresent()) {
            // Jeśli produkt jest już w koszyku, zwiększamy ilość
            CartItemEntity cartItem = existingCartItem.get();
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
            return Optional.of(cartItem);
        } else {
            // Jeśli produkt nie istnieje, tworzymy nowy element koszyka
            CartItemEntity newCartItem = new CartItemEntity();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setTrolleyCart(trolleyCart);
            cartItemRepository.save(newCartItem);

            // Dodajemy nowy element do listy przedmiotów w koszyku
            trolleyCart.getCartItems().add(newCartItem);
            trolleyCartRepository.save(trolleyCart);

            return Optional.of(newCartItem);
        }
    }


}

