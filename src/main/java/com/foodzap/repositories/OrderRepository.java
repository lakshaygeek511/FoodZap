package com.foodzap.repositories;

import com.foodzap.constants.OrderStatus;
import com.foodzap.models.Order;
import com.foodzap.models.Restaurant;
import com.foodzap.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find all orders for a specific user
    List<Order> findByUser(User user);
    
    // Find all orders for a specific restaurant
    List<Order> findByRestaurant(Restaurant restaurant);
    
    // Find orders by status
    List<Order> findByStatus(OrderStatus status);
    
    // Find orders created within a date range
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find orders by user and status
    List<Order> findByUserAndStatus(User user, OrderStatus status);
    
    // Find orders by restaurant and status
    List<Order> findByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);
    
    // Count orders by restaurant and status
    long countByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);
} 