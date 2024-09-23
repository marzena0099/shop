package com.example.demo.TrolleyCart;

import com.example.demo.Address.AddressEntity;
import com.example.demo.Address.AddressRepository;
import com.example.demo.Archieve.OrderEntity;
import com.example.demo.Archieve.OrderItemEntity;
import com.example.demo.Archieve.OrderItemRepository;
import com.example.demo.Archieve.OrderRepository;
import com.example.demo.CartItem.CartItemEntity;
import com.example.demo.CartItem.CartItemRepository;
import com.example.demo.DTO.AddressNotFoundException;
import com.example.demo.DTO.InsufficientProductQuantityException;
import com.example.demo.DTO.ProductNotFoundException;
import com.example.demo.DTO.TrolleyCartNotFoundException;
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
    public TrolleyCartEntity buy(Long userId, Long billing, long shipping) {
        if(!trolleyCartRepository.existsByUserEntity_Id(userId)){
            throw new TrolleyCartNotFoundException("not found trolleyCart");
        }
        TrolleyCartEntity trolleyCart = trolleyCartRepository
                .getTrolleyCartEntitiesByUserEntity_Id(userId);
        List<CartItemEntity> cartItems = trolleyCart.getCartItems();
        OrderEntity orderEntity = createNewOrder(trolleyCart, billing, shipping, cartItems);
        updateQuantityProduct(cartItems);
        createNewOrderItem(orderEntity, cartItems);
        trolleyCart.getCartItems().clear();
       return trolleyCartRepository.save(trolleyCart);
    }

    @Transactional
    public void deleteCartItems(List<CartItemEntity> cartItems) {
        cartItemRepository.flush();
        cartItemRepository.deleteAll(cartItems);
        cartItemRepository.flush();
    }

    public void updateQuantityProduct(List<CartItemEntity> cartItems) {
        List<ProductEntity> products = cartItems
                .stream()
                .map(ci -> productRepository
                        .findById(ci.getProduct().getId())
                        .map(p -> getProduct(ci, p))
                        .orElseThrow(() -> new ProductNotFoundException("Product not found for ID: " + ci.getProduct().getId()))
                )
                .toList();
        productRepository.saveAll(products);
    }

    private static ProductEntity getProduct(CartItemEntity cartItem, ProductEntity product) {
        Long orderedQuantity = cartItem.getQuantity();
        Long availableQuantity = product.getPieces();
        long newQuantity = availableQuantity - orderedQuantity;
        if (newQuantity < 0) {
            throw new InsufficientProductQuantityException("too much quantity, you can only " + product.getPieces() + "procuct name: " + product.getName());
        }
        product.setPieces(newQuantity);
        return product;
    }

    public void createNewOrderItem(OrderEntity orderEntity, List<CartItemEntity> cartItems) {
        cartItems.forEach(e -> {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrderEntity(orderEntity);
            orderItemEntity.setProduct(e.getProduct());
            orderItemEntity.setQuantity(e.getQuantity());
            orderItemEntity.setPriceAtPurchase(e.getProduct().getPrice());
            orderItemRepository.save(orderItemEntity);
        });
    }

    public OrderEntity createNewOrder(TrolleyCartEntity trolleycart, Long billing, Long
            shipping, List<CartItemEntity> cartItems) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(trolleycart.getUserEntity());
        orderEntity.setOrderDate(LocalDateTime.now());
            if (billing != null) {
                AddressEntity billingAddress = addressRepository.findById(billing)
                        .orElseThrow(() -> new AddressNotFoundException("Billing address not found"));
                orderEntity.setBillingAddressEntity(billingAddress);
            }
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
            return createNewProductInTrolley(product, quantity, trolleyCart);

        }

    }

    public CartItemEntity createNewProductInTrolley(ProductEntity product, Long quantity, TrolleyCartEntity
            trolleyCart) {
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

