package com.foodzap.constants;

/**
 * Enum representing the possible states of an order
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PREPARING,
    READY_FOR_PICKUP,
    OUT_FOR_DELIVERY,
    DELIVERED,
    COMPLETED,
    CANCELLED
} 