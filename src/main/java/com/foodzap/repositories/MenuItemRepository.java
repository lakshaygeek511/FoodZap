package com.foodzap.repositories;

import com.foodzap.models.MenuItem;
import com.foodzap.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    // Find all menu items for a specific restaurant
    List<MenuItem> findByRestaurant(Restaurant restaurant);
    
    // Find menu items by name containing the search term
    List<MenuItem> findByNameContaining(String name);
    
    // Find menu items by category
    List<MenuItem> findByCategory(String category);
    
    // Find menu items with a price lower than the given value
    List<MenuItem> findByPriceLessThan(BigDecimal price);
    
    // Find menu items by restaurant and name containing the search term
    List<MenuItem> findByRestaurantAndNameContaining(Restaurant restaurant, String name);
    
    // Find menu items by restaurant and category
    List<MenuItem> findByRestaurantAndCategory(Restaurant restaurant, String category);
    
    // Find menu items within a price range
    List<MenuItem> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Find vegetarian menu items
    List<MenuItem> findByIsVegetarian(boolean isVegetarian);
    
    // Find available menu items
    List<MenuItem> findByIsAvailable(boolean isAvailable);
} 