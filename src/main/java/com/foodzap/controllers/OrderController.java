package com.foodzap.controllers;

import com.foodzap.constants.OrderStatus;
import com.foodzap.dto.ErrorResponse;
import com.foodzap.dto.OrderDto;
import com.foodzap.dto.OrderRequest;
import com.foodzap.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Order not found with id: " + id, HttpStatus.NOT_FOUND.value()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderDto>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        List<OrderDto> orders = orderService.getOrdersByRestaurant(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<OrderDto> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            OrderDto placedOrder = orderService.placeOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(placedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        try {
            OrderDto createdOrder = orderService.createOrder(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        try {
            OrderDto updatedOrder = orderService.updateOrderStatus(id, status);
            if (updatedOrder != null) {
                return ResponseEntity.ok(updatedOrder);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Order not found with id: " + id, HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            boolean deleted = orderService.deleteOrder(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Order not found with id: " + id, HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
} 