package com.acunsoz.ecommerce_backend.controller;

import com.acunsoz.ecommerce_backend.model.dto.OrderDto;
import com.acunsoz.ecommerce_backend.model.entity.User;
import com.acunsoz.ecommerce_backend.model.enums.OrderStatus;
import com.acunsoz.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> placeOrder(
            @AuthenticationPrincipal User user
    )
    {
        OrderDto order = orderService.placeOrder(user);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrders(
            @AuthenticationPrincipal User user
    )
    {
        List<OrderDto> orders = orderService.getUserOrders(user);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ){
        OrderDto updatedOrder = orderService.updateOrderStatus(orderId,status);
        return ResponseEntity.ok(updatedOrder);
    }

}
