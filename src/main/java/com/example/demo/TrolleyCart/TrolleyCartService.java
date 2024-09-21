package com.example.demo.TrolleyCart;

import com.example.demo.Address.AddressRepository;
import com.example.demo.Archieve.OrderEntity;
import com.example.demo.Archieve.OrderItemEntity;
import com.example.demo.Archieve.OrderItemRepository;
import com.example.demo.Archieve.OrderRepository;
import com.example.demo.CartItem.CartItemEntity;
import com.example.demo.CartItem.CartItemRepository;
import com.example.demo.DTO.InsufficientProductQuantityException;
import com.example.demo.DTO.ProductNotFoundException;
import com.example.demo.ENUM.OrderStatus;
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
    private final AddressRepository addressRepository;


    @Transactional
    public void buy(Long userId, Long billing, long shipping) {
        Optional<TrolleyCartEntity> optionalTrolleyCartEntity = trolleyCartRepository.findByUserEntity_Id(userId);
        if (optionalTrolleyCartEntity.isEmpty()) {
            throw new RuntimeException("not found trolleyCart");
        }
        TrolleyCartEntity trolleycart = optionalTrolleyCartEntity.get();
        List<CartItemEntity> cartItems = trolleycart.getCartItems();
        OrderEntity orderEntity = createNewOrder(trolleycart, billing, shipping, cartItems);
        updateQuantityProduct(cartItems);
        createNewOrderItem(orderEntity,cartItems);

        trolleycart.getCartItems().clear();
        trolleyCartRepository.save(trolleycart);


    }

    @Transactional
    public void deleteCartItems(List<CartItemEntity> cartItems) {
        cartItemRepository.flush();
        cartItemRepository.deleteAll(cartItems);
        cartItemRepository.flush();
    }
    public void updateQuantityProduct(List<CartItemEntity> cartItems) {
        cartItems.forEach(e -> {
            Optional<ProductEntity> productOpt = productRepository.findById(e.getProduct().getId());
            if (productOpt.isPresent()) {
                ProductEntity product = productOpt.get();
                Long orderedQuantity = e.getQuantity();
                Long availableQuantity = product.getPieces();
                Long newQuantity = availableQuantity - orderedQuantity;

                if (newQuantity < 0) {
                    throw new InsufficientProductQuantityException("too much quantity, you can only " + product.getPieces() +"procuct name: " +product.getName() );
                }
                product.setPieces(newQuantity);
                productRepository.save(product);

            } else {
                throw new ProductNotFoundException("Product not found for ID: " + e.getProduct().getId());
            }
        });
    }

    public void createNewOrderItem(OrderEntity orderEntity, List<CartItemEntity> cartItems){
        cartItems.forEach(e-> {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrderEntity(orderEntity);
            orderItemEntity.setProduct(e.getProduct());
            orderItemEntity.setQuantity(e.getQuantity());
            orderItemEntity.setPriceAtPurchase(e.getProduct().getPrice());
            orderItemRepository.save(orderItemEntity);
        });
    }

    public OrderEntity createNewOrder(TrolleyCartEntity trolleycart, Long billing, Long shipping, List<CartItemEntity> cartItems) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(trolleycart.getUserEntity());
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setBillingAddressEntity(addressRepository.getReferenceById(billing));
        orderEntity.setShippingAddressEntity(addressRepository.getReferenceById(shipping));
        orderEntity.setStatus(OrderStatus.PENDING);
        BigDecimal totalAmount = calculateTotalAmount(cartItems);
        orderEntity.setTotalAmount(totalAmount);
        orderRepository.save(orderEntity);
        return orderEntity;
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
    public CartItemEntity add(Long productId, Long quantity, Long idUser) {
        ProductEntity product = checkAvailabilityProduct(productId, quantity);

        TrolleyCartEntity trolleyCart = trolleyCartRepository.findByUserEntity_Id(idUser)
                .orElseGet(() ->
                        createNewTrolley(idUser)
                );
        Optional<CartItemEntity> existingCartItem = cartItemRepository.
                findByProductIdAndTrolleyCartId(productId, trolleyCart.getId());

        if (existingCartItem.isPresent()) {
           return updateQuantityProduct(quantity, existingCartItem.get());

        } else {
          return createNewProductInTrolley(product,quantity,trolleyCart);

        }

    }

public CartItemEntity createNewProductInTrolley(ProductEntity product, Long quantity, TrolleyCartEntity trolleyCart){
    CartItemEntity newCartItem = new CartItemEntity();
    newCartItem.setProduct(product);
    newCartItem.setQuantity(quantity);
    newCartItem.setTrolleyCart(trolleyCart);
    cartItemRepository.save(newCartItem);

    trolleyCart.getCartItems().add(newCartItem);
    trolleyCartRepository.save(trolleyCart);

    return newCartItem;
}

    public CartItemEntity updateQuantityProduct(Long quantity, CartItemEntity existingCartItem) {
        existingCartItem.setQuantity(quantity);
        cartItemRepository.save(existingCartItem);
        return existingCartItem;
    }

    public ProductEntity checkAvailabilityProduct(Long productId, Long quantity) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (product.getPieces() < quantity) {
            throw new InsufficientProductQuantityException("Not enough pieces of product");
        }
        return product;
    }

    public TrolleyCartEntity createNewTrolley(Long idUser) {
        Optional<UserEntity> user = userRepository.findById(idUser);
        TrolleyCartEntity newCart = new TrolleyCartEntity();
        newCart.setUserEntity(user.get());
        newCart.setCartItems(new ArrayList<>());
        trolleyCartRepository.save(newCart);
        return newCart;
    }


}

