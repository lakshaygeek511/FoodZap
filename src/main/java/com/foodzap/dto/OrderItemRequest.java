package com.foodzap.dto;

/**
 * DTO for requesting a specific item in an order
 */
public class OrderItemRequest {
    private String itemName;
    private int quantity;
    private String specialInstructions;
    
    // Default constructor
    public OrderItemRequest() {
    }
    
    // Constructor with essential fields
    public OrderItemRequest(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
    
    // Constructor with all fields
    public OrderItemRequest(String itemName, int quantity, String specialInstructions) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.specialInstructions = specialInstructions;
    }
    
    // Getters and Setters
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
} 