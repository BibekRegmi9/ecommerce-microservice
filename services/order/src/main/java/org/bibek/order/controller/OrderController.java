package org.bibek.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bibek.order.OrderRequest;
import org.bibek.order.OrderResponse;
import org.bibek.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequest request
    ) {
        return ResponseEntity.ok(this.service.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(this.service.findAllOrders());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById(
            @PathVariable("order-id") Long orderId
    ) {
        return ResponseEntity.ok(this.service.findById(orderId));
    }
}
