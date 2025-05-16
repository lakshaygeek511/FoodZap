package com.foodzap.utils;

import com.foodzap.models.Restaurant;
import com.foodzap.models.MenuItem;
import com.foodzap.dto.OrderItemRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Strategy that selects the restaurant with the lowest cost for the requested menu item
 */
@Component
public class LowestCostStrategy implements OrderAssignmentStrategy {
    
    @Override
    public Restaurant findBestRestaurant(List<Restaurant> eligibleRestaurants, List<OrderItemRequest> orderItems) {
        if (eligibleRestaurants == null || eligibleRestaurants.isEmpty() || orderItems == null || orderItems.isEmpty()) {
            return null;
        }
        
        // We just need to find the restaurant with the lowest price for the requested menu item
        String menuItemName = orderItems.get(0).getItemName();
        
        Restaurant lowestPriceRestaurant = null;
        BigDecimal lowestPrice = null;
        
        // Find restaurant with lowest price for the menu item
        for (Restaurant restaurant : eligibleRestaurants) {
            Optional<MenuItem> menuItemOpt = restaurant.getMenuItems().stream()
                    .filter(item -> item.getName().equalsIgnoreCase(menuItemName) && item.isAvailable())
                    .findFirst();
            
            if (menuItemOpt.isPresent()) {
                MenuItem menuItem = menuItemOpt.get();
                BigDecimal price = menuItem.getPrice();
                
                if (lowestPrice == null || price.compareTo(lowestPrice) < 0) {
                    lowestPrice = price;
                    lowestPriceRestaurant = restaurant;
                }
            }
        }
        
        // Debug output - can be removed after confirming it works
        System.out.println("Lowest Cost Strategy: Selected restaurant " + 
                (lowestPriceRestaurant != null ? lowestPriceRestaurant.getName() : "None") + 
                " with price " + lowestPrice + " for " + menuItemName);
        
        return lowestPriceRestaurant;
    }
} 
