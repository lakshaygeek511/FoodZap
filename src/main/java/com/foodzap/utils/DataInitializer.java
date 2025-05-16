package com.foodzap.utils;

import com.foodzap.models.MenuItem;
import com.foodzap.models.Restaurant;
import com.foodzap.models.User;
import com.foodzap.repositories.MenuItemRepository;
import com.foodzap.repositories.RestaurantRepository;
import com.foodzap.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                // Create sample users
                User user1 = new User("John Doe", "john@example.com", "password", "555-123-4567", "123 Main St");
                User user2 = new User("Jane Smith", "jane@example.com", "password", "555-987-6543", "456 Oak Ave");
                User user3 = new User("Bob Johnson", "bob@example.com", "password", "555-111-2222", "789 Pine Rd");
                
                List<User> users = Arrays.asList(user1, user2, user3);
                userRepository.saveAll(users);
                
                System.out.println("Sample users created");
            }
            
            if (restaurantRepository.count() == 0) {
                // Create sample restaurants
                Restaurant restaurant1 = new Restaurant(
                        "Pizza Palace",
                        "Best pizza in town",
                        "456 Main St",
                        "555-111-2233",
                        "pizza@example.com"
                );
                restaurant1.setOpeningHours("Mon-Sun: 11:00 AM - 11:00 PM");
                restaurant1.setRating(4.5f);
                restaurant1.setImageUrl("https://example.com/pizza_palace.jpg");
                
                Restaurant restaurant2 = new Restaurant(
                        "Burger Joint",
                        "Juicy burgers and crispy fries",
                        "789 Oak Ave",
                        "555-444-5555",
                        "burger@example.com"
                );
                restaurant2.setOpeningHours("Mon-Sun: 10:00 AM - 10:00 PM");
                restaurant2.setRating(4.2f);
                restaurant2.setImageUrl("https://example.com/burger_joint.jpg");
                
                Restaurant restaurant3 = new Restaurant(
                        "Sushi Express",
                        "Fresh sushi delivered fast",
                        "123 Maple St",
                        "555-777-8888",
                        "sushi@example.com"
                );
                restaurant3.setOpeningHours("Mon-Sun: 11:30 AM - 9:30 PM");
                restaurant3.setRating(4.7f);
                restaurant3.setImageUrl("https://example.com/sushi_express.jpg");
                
                Restaurant restaurant4 = new Restaurant(
                        "Taco Time",
                        "Authentic Mexican cuisine",
                        "321 Pine Rd",
                        "555-999-0000",
                        "taco@example.com"
                );
                restaurant4.setOpeningHours("Mon-Sun: 10:30 AM - 9:00 PM");
                restaurant4.setRating(4.0f);
                restaurant4.setImageUrl("https://example.com/taco_time.jpg");
                
                Restaurant restaurant5 = new Restaurant(
                        "Pasta Paradise",
                        "Italian comfort food",
                        "654 Elm St",
                        "555-222-3333",
                        "pasta@example.com"
                );
                restaurant5.setOpeningHours("Tue-Sun: 5:00 PM - 10:00 PM");
                restaurant5.setRating(4.8f);
                restaurant5.setImageUrl("https://example.com/pasta_paradise.jpg");
                
                List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5);
                restaurantRepository.saveAll(restaurants);
                
                System.out.println("Sample restaurants created");
                
                // Create menu items for Pizza Palace
                MenuItem pizza1 = new MenuItem("Margherita Pizza", "Classic tomato and mozzarella", new BigDecimal("12.99"), restaurant1, "Pizza");
                pizza1.setVegetarian(true);
                pizza1.setAvailable(true);
                pizza1.setImageUrl("https://example.com/margherita.jpg");
                
                MenuItem pizza2 = new MenuItem("Pepperoni Pizza", "Loaded with pepperoni", new BigDecimal("14.99"), restaurant1, "Pizza");
                pizza2.setVegetarian(false);
                pizza2.setAvailable(true);
                pizza2.setImageUrl("https://example.com/pepperoni.jpg");
                
                MenuItem pizza3 = new MenuItem("Hawaiian Pizza", "Ham and pineapple", new BigDecimal("15.99"), restaurant1, "Pizza");
                pizza3.setVegetarian(false);
                pizza3.setAvailable(true);
                pizza3.setImageUrl("https://example.com/hawaiian.jpg");
                
                MenuItem pizzaSide1 = new MenuItem("Garlic Bread", "Crispy garlic bread", new BigDecimal("4.99"), restaurant1, "Sides");
                pizzaSide1.setVegetarian(true);
                pizzaSide1.setAvailable(true);
                
                // Create menu items for Burger Joint
                MenuItem burger1 = new MenuItem("Classic Burger", "Beef patty with lettuce, tomato, and cheese", new BigDecimal("9.99"), restaurant2, "Burgers");
                burger1.setVegetarian(false);
                burger1.setAvailable(true);
                burger1.setImageUrl("https://example.com/classic_burger.jpg");
                
                MenuItem burger2 = new MenuItem("Veggie Burger", "Plant-based patty with all the fixings", new BigDecimal("10.99"), restaurant2, "Burgers");
                burger2.setVegetarian(true);
                burger2.setAvailable(true);
                burger2.setImageUrl("https://example.com/veggie_burger.jpg");
                
                MenuItem burger3 = new MenuItem("Bacon Cheeseburger", "Classic burger with bacon", new BigDecimal("12.99"), restaurant2, "Burgers");
                burger3.setVegetarian(false);
                burger3.setAvailable(true);
                burger3.setImageUrl("https://example.com/bacon_burger.jpg");
                
                MenuItem burgerSide1 = new MenuItem("French Fries", "Crispy golden fries", new BigDecimal("3.99"), restaurant2, "Sides");
                burgerSide1.setVegetarian(true);
                burgerSide1.setAvailable(true);
                
                // Create menu items for Sushi Express
                MenuItem sushi1 = new MenuItem("California Roll", "Crab, avocado, and cucumber", new BigDecimal("8.99"), restaurant3, "Sushi Rolls");
                sushi1.setVegetarian(false);
                sushi1.setAvailable(true);
                sushi1.setImageUrl("https://example.com/california_roll.jpg");
                
                MenuItem sushi2 = new MenuItem("Spicy Tuna Roll", "Fresh tuna with spicy sauce", new BigDecimal("10.99"), restaurant3, "Sushi Rolls");
                sushi2.setVegetarian(false);
                sushi2.setAvailable(true);
                sushi2.setImageUrl("https://example.com/spicy_tuna.jpg");
                
                MenuItem sushi3 = new MenuItem("Veggie Roll", "Avocado, cucumber, and carrot", new BigDecimal("7.99"), restaurant3, "Sushi Rolls");
                sushi3.setVegetarian(true);
                sushi3.setAvailable(true);
                sushi3.setImageUrl("https://example.com/veggie_roll.jpg");
                
                // Save all menu items
                List<MenuItem> menuItems = Arrays.asList(
                        pizza1, pizza2, pizza3, pizzaSide1,
                        burger1, burger2, burger3, burgerSide1,
                        sushi1, sushi2, sushi3
                );
                menuItemRepository.saveAll(menuItems);
                
                System.out.println("Sample menu items created");
            }
        };
    }
} 