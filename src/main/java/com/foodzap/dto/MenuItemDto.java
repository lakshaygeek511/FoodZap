package com.foodzap.dto;

import java.math.BigDecimal;

public class MenuItemDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long restaurantId;
    private String category;
    private boolean isVegetarian;
    private boolean isAvailable;
    private String imageUrl;
    
    // Default constructor
    public MenuItemDto() {
    }
    
    // Constructor with essential fields
    public MenuItemDto(Long id, String name, BigDecimal price, Long restaurantId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }
    
    // Constructor with all fields
    public MenuItemDto(Long id, String name, String description, BigDecimal price, 
                      Long restaurantId, String category, boolean isVegetarian, 
                      boolean isAvailable, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
        this.category = category;
        this.isVegetarian = isVegetarian;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Long getRestaurantId() {
        return restaurantId;
    }
    
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public boolean isVegetarian() {
        return isVegetarian;
    }
    
    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
} 