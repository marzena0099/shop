package com.example.demo.TrolleyCart;

import com.example.demo.Archieve.OrderEntity;
import com.example.demo.Archieve.OrderItemEntity;
import com.example.demo.Archieve.OrderItemRepository;
import com.example.demo.Archieve.OrderRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void buy(Long userId) {

        Optional<TrolleyCartEntity> trolleycart = trolleyCartRepository.findByUserEntity_Id(userId);
        List<CartItemEntity> cartItems = trolleycart.get().getCartItems();
        // Tworzenie nowego zamówienia
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(trolleycart.get().getUserEntity()); // Przypisanie użytkownika
        orderEntity.setOrderDate(LocalDateTime.now()); // Ustawienie daty zamówienia


        cartItems.forEach(e -> {
            Optional<ProductEntity> productOpt = productRepository.findById(e.getProduct().getId());
            if (productOpt.isPresent()) {


                ProductEntity product = productOpt.get();

                int orderedQuantity = e.getQuantity();
                Long availableQuantity = product.getPieces();
                Long newQuantity = availableQuantity - orderedQuantity;

                if (newQuantity < 0) {
                    throw new InsufficientProductQuantityException("too much quantity, you can only " + product.getPieces());
                }
                product.setPieces(newQuantity);
                productRepository.save(product);
//                // Tworzenie pozycji zamówienia
                OrderItemEntity orderItemEntity = new OrderItemEntity();
                orderItemEntity.setOrderEntity(orderEntity);
                orderItemEntity.setProduct(product);
                orderItemEntity.setQuantity(orderedQuantity);
                orderItemEntity.setPriceAtPurchase(product.getPrice());
                orderItemRepository.save(orderItemEntity);

            } else {
                throw new ProductNotFoundException("Product not found for ID: " + e.getProduct().getId());
            }
            BigDecimal totalAmount = calculateTotalAmount(cartItems);
            orderEntity.setTotalAmount(totalAmount);
            orderRepository.save(orderEntity);
            cartItemRepository.deleteAll(cartItems);
            trolleyCartRepository.delete(trolleycart.get());
//            //
//            cartItemRepository.deleteAll(cartItems);
//            trolleycart.get().setCartItems(new ArrayList<>());
//            trolleyCartRepository.save(trolleycart.get());
        });


    }
    public BigDecimal calculateTotalAmount(List<CartItemEntity> cartItems) {
        BigDecimal totalAmount;

        totalAmount = cartItems.stream()
                .map(item -> {
                    BigDecimal price = BigDecimal.valueOf(item.getProduct().getPrice());

                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());

                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalAmount;
    }

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

