package com.example.demo.Archieve;


import com.example.demo.Address.AddressEntity;
import com.example.demo.Address.AddressRepository;
import com.example.demo.DTO.AddressNotFoundException;
import com.example.demo.DTO.OrderNotFoundException;
import com.example.demo.DTO.UserNotFoundException;
import com.example.demo.ENUM.OrderStatus;
import com.example.demo.Employee.EmployeeEntity;
import com.example.demo.Employee.EmployeeRepository;
import com.example.demo.User.UserEntity;
import com.example.demo.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    private AddressRepository addressRepository;
private EmployeeRepository employeeRepository;
    private UserRepository userRepository;

    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for ID: " + orderId));
        orderEntity.setStatus(newStatus);
        orderRepository.save(orderEntity);
    }


    public OrderEntity createOrder(Long userId, Long shippingAddressId, Long billingAddressId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userId));

        AddressEntity shippingAddressEntity = addressRepository.findById(shippingAddressId)
                .orElseThrow(() -> new AddressNotFoundException("Shipping address not found for ID: " + shippingAddressId));

        AddressEntity billingAddressEntity = addressRepository.findById(billingAddressId)
                .orElseThrow(() -> new AddressNotFoundException("Billing address not found for ID: " + billingAddressId));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(user);
        orderEntity.setShippingAddressEntity(shippingAddressEntity);
        orderEntity.setBillingAddressEntity(billingAddressEntity);
        orderEntity.setStatus(OrderStatus.PENDING);

        return orderRepository.save(orderEntity);
    }

    public OrderStatus getOrderStatus(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for ID: " + orderId));
        return orderEntity.getStatus();
    }


    public void addWorkerToOrder(Long orderId, Long employeeId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for ID: " + orderId));
        if (orderEntity.getStatus() == OrderStatus.PENDING) {
            orderEntity.setStatus(OrderStatus.PROCESSING);
            EmployeeEntity employee = employeeRepository.findById(employeeId)
                            .orElseThrow(()-> new RuntimeException("not found employee"));
            orderEntity.setEmployee(employee);
            orderRepository.save(orderEntity);
        } else {
            throw new IllegalStateException("Order must be in PENDING status to be marked as PROCESSING");
        }
    }
}

