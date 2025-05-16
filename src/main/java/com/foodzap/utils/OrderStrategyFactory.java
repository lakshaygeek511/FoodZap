package com.foodzap.utils;

import org.springframework.stereotype.Component;

/**
 * Factory for creating order assignment strategies
 */
@Component
public class OrderStrategyFactory {
    
    private final LowestCostStrategy lowestCostStrategy;
    private final HighestRatingStrategy highestRatingStrategy;
    
    public OrderStrategyFactory(LowestCostStrategy lowestCostStrategy, HighestRatingStrategy highestRatingStrategy) {
        this.lowestCostStrategy = lowestCostStrategy;
        this.highestRatingStrategy = highestRatingStrategy;
    }
    
    /**
     * Get a strategy based on the selection criteria
     * 
     * @param selectionCriteria The criteria for selecting a restaurant
     * @return The appropriate strategy
     */
    public OrderAssignmentStrategy getStrategy(String selectionCriteria) {
        if (selectionCriteria == null) {
            // Default strategy
            return lowestCostStrategy;
        }
        
        switch (selectionCriteria.toUpperCase()) {
            case "HIGHEST_RATING":
                return highestRatingStrategy;
            case "LOWEST_COST":
            default:
                return lowestCostStrategy;
        }
    }
} 