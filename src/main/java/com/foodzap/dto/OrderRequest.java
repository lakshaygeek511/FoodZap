package com.foodzap.dto;

import java.util.List;

/**
 * DTO for creating a new order
 */
public class OrderRequest {
    private String username;
    private List<OrderItemRequest> items;
    private String selectionStrategy; // LOWEST_COST or HIGHEST_RATING
    private String deliveryAddress;
    private String notes;
    
    // Default constructor
    public OrderRequest() {
    }
    
    // Constructor with essential fields
    public OrderRequest(String username, List<OrderItemRequest> items) {
        this.username = username;
        this.items = items;
        this.selectionStrategy = "LOWEST_COST"; // Default
    }
    
    // Constructor with all fields
    public OrderRequest(String username, List<OrderItemRequest> items, String selectionStrategy, String deliveryAddress, String notes) {
        this.username = username;
        this.items = items;
        this.selectionStrategy = selectionStrategy;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public List<OrderItemRequest> getItems() {
        return items;
    }
    
    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
    
    public String getSelectionStrategy() {
        return selectionStrategy;
    }
    
    public void setSelectionStrategy(String selectionStrategy) {
        this.selectionStrategy = selectionStrategy;
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
} 