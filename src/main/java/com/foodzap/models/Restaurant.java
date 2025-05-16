package com.foodzap.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String openingHours;

    @Column
    private Float rating;

    @Column
    private String imageUrl;
    
    @Column
    private Integer maxOrders = 5; // Default max orders is 5
    
    @Column
    private Integer currentOrders = 0; // Default current orders is 0

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

    // Default constructor
    public Restaurant() {
    }

    // Constructor with essential fields
    public Restaurant(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Constructor with more fields
    public Restaurant(String name, String description, String address, String phone, String email) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.email = email;
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
    
    public boolean hasCapacity() {
        return currentOrders < maxOrders;
    }
    
    public void incrementCurrentOrders() {
        this.currentOrders++;
    }
    
    public void decrementCurrentOrders() {
        if (this.currentOrders > 0) {
            this.currentOrders--;
        }
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    // Helper method to add a menu item
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        menuItem.setRestaurant(this);
    }

    // Helper method to remove a menu item
    public void removeMenuItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
        menuItem.setRestaurant(null);
    }
} 