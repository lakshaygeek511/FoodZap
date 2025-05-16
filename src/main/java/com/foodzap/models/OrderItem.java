package com.foodzap.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String specialInstructions;

    // Default constructor
    public OrderItem() {
        this.quantity = 1;
    }

    // Constructor with essential fields
    public OrderItem(Order order, MenuItem menuItem, Integer quantity) {
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = menuItem.getPrice();
    }

    // Constructor with all fields
    public OrderItem(Order order, MenuItem menuItem, Integer quantity, String specialInstructions) {
        this(order, menuItem, quantity);
        this.specialInstructions = specialInstructions;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        // Update price from menu item
        if (menuItem != null) {
            this.price = menuItem.getPrice();
        }
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

    // Helper method to calculate subtotal
    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
} 