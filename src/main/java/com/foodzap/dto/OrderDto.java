package com.foodzap.dto;

import com.foodzap.constants.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String userName;
    private String restaurantName;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String deliveryAddress;
    private String notes;
    private List<OrderItemDto> orderItems;
    
    // Default constructor
    public OrderDto() {
    }
    
    // Constructor with essential fields
    public OrderDto(Long id, Long userId, Long restaurantId, OrderStatus status, BigDecimal totalAmount) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.totalAmount = totalAmount;
    }
    
    // Constructor with all fields
    public OrderDto(Long id, Long userId, Long restaurantId, LocalDateTime orderDate, 
                   LocalDateTime deliveryDate, OrderStatus status, BigDecimal totalAmount, 
                   String deliveryAddress, String notes) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Long getRestaurantId() {
        return restaurantId;
    }
    
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
    
    public String getRestaurantName() {
        return restaurantName;
    }
    
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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
    
    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
} 