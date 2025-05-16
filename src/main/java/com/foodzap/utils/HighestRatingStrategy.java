package com.foodzap.utils;

import com.foodzap.models.Restaurant;
import com.foodzap.dto.OrderItemRequest;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Strategy that selects the highest rated restaurant that offers the requested menu item
 */
@Component
public class HighestRatingStrategy implements OrderAssignmentStrategy {
    
    @Override
    public Restaurant findBestRestaurant(List<Restaurant> eligibleRestaurants, List<OrderItemRequest> orderItems) {
        if (eligibleRestaurants == null || eligibleRestaurants.isEmpty() || orderItems == null || orderItems.isEmpty()) {
            return null;
        }
        
        // We just need to find the highest rated restaurant that offers the requested menu item
        String menuItemName = orderItems.get(0).getItemName();
        
        Restaurant highestRatedRestaurant = null;
        Float highestRating = null;
        
        // Find highest rated restaurant that offers the menu item
        for (Restaurant restaurant : eligibleRestaurants) {
            // Check if restaurant has the menu item and has a valid rating
            boolean hasItem = restaurant.getMenuItems().stream()
                    .anyMatch(item -> item.getName().equalsIgnoreCase(menuItemName) && item.isAvailable());
            
            if (hasItem && restaurant.getRating() != null) {
                Float rating = restaurant.getRating();
                
                if (highestRating == null || rating > highestRating) {
                    highestRating = rating;
                    highestRatedRestaurant = restaurant;
                }
            }
        }
        
        // Debug output - can be removed after confirming it works
        System.out.println("Highest Rating Strategy: Selected restaurant " + 
                (highestRatedRestaurant != null ? highestRatedRestaurant.getName() : "None") + 
                " with rating " + highestRating + " for " + menuItemName);
        
        return highestRatedRestaurant;
    }
} 