package com.foodzap.repositories;

import com.foodzap.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Find restaurant by name
    Optional<Restaurant> findByName(String name);
    
    // Find all restaurants with a rating greater than or equal to the given value
    List<Restaurant> findByRatingGreaterThanEqual(Float rating);
    
    // Find restaurants by address containing the search term
    List<Restaurant> findByAddressContaining(String location);
    
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menuItems")
    List<Restaurant> findAllWithMenuItems();
    
    // Find restaurant by ID and eagerly fetch its menu items
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menuItems WHERE r.id = :id")
    Optional<Restaurant> findByIdWithMenuItems(@Param("id") Long id);
} 