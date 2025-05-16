package com.foodzap.dto;

import java.math.BigDecimal;

public class OrderItemDto {
    private Long id;
    private Long orderId;
    private Long menuItemId;
    private String menuItemName;
    private Integer quantity;
    private BigDecimal price;
    private String specialInstructions;
    
    // Default constructor
    public OrderItemDto() {
    }
    
    // Constructor with essential fields
    public OrderItemDto(Long id, Long orderId, Long menuItemId, Integer quantity, BigDecimal price) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Constructor with all fields
    public OrderItemDto(Long id, Long orderId, Long menuItemId, String menuItemName, 
                       Integer quantity, BigDecimal price, String specialInstructions) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
        this.price = price;
        this.specialInstructions = specialInstructions;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public Long getMenuItemId() {
        return menuItemId;
    }
    
    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }
    
    public String getMenuItemName() {
        return menuItemName;
    }
    
    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    // Calculate subtotal
    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
} 