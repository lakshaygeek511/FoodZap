package com.foodzap.utils;

import com.foodzap.models.Restaurant;
import com.foodzap.dto.OrderItemRequest;

import java.util.List;

/**
 * Interface for different strategies to assign restaurants to orders
 */
public interface OrderAssignmentStrategy {
    /**
     * Find the best restaurant to fulfill an order based on a specific strategy
     * 
     * @param eligibleRestaurants List of restaurants that can fulfill all items in the order
     * @param orderItems List of items in the order
     * @return The best restaurant according to the strategy, or null if no restaurant can fulfill the order
     */
    Restaurant findBestRestaurant(List<Restaurant> eligibleRestaurants, List<OrderItemRequest> orderItems);
} 