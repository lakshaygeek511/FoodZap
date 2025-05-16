package com.foodzap.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String phone;

    @Column
    private String address;
    
    @Column
    private BigDecimal walletBalance = new BigDecimal("100.00");

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    // Default constructor
    public User() {
        this.walletBalance = new BigDecimal("100.00");
    }

    // Constructor with essential fields
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.walletBalance = new BigDecimal("100.00");
    }

    // Constructor with all fields
    public User(String name, String email, String password, String phone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.walletBalance = new BigDecimal("100.00");
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public BigDecimal getWalletBalance() {
        return walletBalance;
    }
    
    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }
    
    public boolean addToWallet(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            this.walletBalance = this.walletBalance.add(amount);
            return true;
        }
        return false;
    }
    
    public boolean deductFromWallet(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 && this.walletBalance.compareTo(amount) >= 0) {
            this.walletBalance = this.walletBalance.subtract(amount);
            return true;
        }
        return false;
    }
    
    public boolean hasEnoughBalance(BigDecimal amount) {
        return this.walletBalance.compareTo(amount) >= 0;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
} 