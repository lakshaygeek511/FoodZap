package com.foodzap.repositories;

import com.foodzap.models.MenuItem;
import com.foodzap.models.Order;
import com.foodzap.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Find all order items for a specific order
    List<OrderItem> findByOrder(Order order);
    
    // Find order items by menu item
    List<OrderItem> findByMenuItem(MenuItem menuItem);
    
    // Count how many times a menu item has been ordered
    long countByMenuItem(MenuItem menuItem);
    
    // Get the top ordered menu items
    @Query("SELECT oi.menuItem, COUNT(oi) as count FROM OrderItem oi GROUP BY oi.menuItem ORDER BY count DESC")
    List<Object[]> findTopOrderedMenuItems();
} 