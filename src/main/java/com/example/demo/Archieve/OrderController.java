package com.example.demo.Archieve;

import com.example.demo.ENUM.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RequestMapping("/api/order")
@RestController
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{orderId}/status")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long orderId) {
        OrderStatus status = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(status);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus newStatus) {
        orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderEntity> createOrder(Long userId, Long shippingAddressId, Long billingAddressId) {
       OrderEntity orderEntity= orderService.createOrder(userId, shippingAddressId, billingAddressId);
    return ResponseEntity.ok(orderEntity);
    }

    @PostMapping("/addWorkerToOrder")
    public ResponseEntity<Void> addWorker(@RequestParam Long orderId, @RequestParam Long workerId) {
        orderService.addWorkerToOrder(orderId, workerId);
        return ResponseEntity.ok().build();
    }
}
