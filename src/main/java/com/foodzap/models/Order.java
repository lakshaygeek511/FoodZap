package com.foodzap.models;

import com.foodzap.constants.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column
    private LocalDateTime deliveryDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column
    private String deliveryAddress;

    @Column
    private String notes;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // Default constructor
    public Order() {
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.totalAmount = BigDecimal.ZERO;
    }

    // Constructor with essential fields
    public Order(User user, Restaurant restaurant) {
        this();
        this.user = user;
        this.restaurant = restaurant;
    }

    // Constructor with more fields
    public Order(User user, Restaurant restaurant, String deliveryAddress) {
        this(user, restaurant);
        this.deliveryAddress = deliveryAddress;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // Helper method to add an order item
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        // Update total amount
        this.totalAmount = this.totalAmount.add(
            orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
        );
    }

    // Helper method to remove an order item
    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
        // Update total amount
        this.totalAmount = this.totalAmount.subtract(
            orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
        );
    }

    // Helper method to calculate total amount
    public void calculateTotalAmount() {
        this.totalAmount = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            this.totalAmount = this.totalAmount.add(
                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }
    }
} 