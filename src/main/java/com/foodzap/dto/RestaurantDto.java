package com.foodzap.dto;

import java.util.List;

public class RestaurantDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String openingHours;
    private Float rating;
    private String imageUrl;
    private Integer maxOrders = 5;
    private Integer currentOrders = 0;
    private List<MenuItemDto> menuItems;
    
    // Default constructor
    public RestaurantDto() {
    }
    
    // Constructor with essential fields
    public RestaurantDto(Long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    
    // Constructor with all fields
    public RestaurantDto(Long id, String name, String description, String address, String phone, 
                        String email, String openingHours, Float rating, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.openingHours = openingHours;
        this.rating = rating;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getOpeningHours() {
        return openingHours;
    }
    
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
    
    public Float getRating() {
        return rating;
    }
    
    public void setRating(Float rating) {
        this.rating = rating;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Integer getMaxOrders() {
        return maxOrders;
    }
    
    public void setMaxOrders(Integer maxOrders) {
        this.maxOrders = maxOrders;
    }
    
    public Integer getCurrentOrders() {
        return currentOrders;
    }
    
    public void setCurrentOrders(Integer currentOrders) {
        this.currentOrders = currentOrders;
    }
    
    public List<MenuItemDto> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItemDto> menuItems) {
        this.menuItems = menuItems;
    }
} 