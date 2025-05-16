package com.foodzap.services;

import com.foodzap.dto.MenuItemDto;
import com.foodzap.dto.RestaurantDto;
import com.foodzap.models.MenuItem;
import com.foodzap.models.Restaurant;
import com.foodzap.repositories.MenuItemRepository;
import com.foodzap.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<RestaurantDto> getAllRestaurants() {
        // Fetch restaurants with JOIN FETCH to eagerly load menu items
        List<Restaurant> restaurants = restaurantRepository.findAllWithMenuItems();
        return restaurants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public RestaurantDto getRestaurantById(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findByIdWithMenuItems(id);
        return restaurant.map(this::convertToDto).orElse(null);
    }

    public RestaurantDto getRestaurantByName(String name) {
        Optional<Restaurant> restaurant = restaurantRepository.findByName(name);
        return restaurant.map(this::convertToDto).orElse(null);
    }

    public List<RestaurantDto> getRestaurantsByRating(Float rating) {
        return restaurantRepository.findByRatingGreaterThanEqual(rating).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RestaurantDto> getRestaurantsByLocation(String location) {
        return restaurantRepository.findByAddressContaining(location).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        // Check if restaurant with the same name already exists
        if (restaurantRepository.findByName(restaurantDto.getName()).isPresent()) {
            throw new RuntimeException("Restaurant with this name already exists");
        }
        
        Restaurant restaurant = convertToEntity(restaurantDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        
        // Save menu items if provided
        if (restaurantDto.getMenuItems() != null && !restaurantDto.getMenuItems().isEmpty()) {
            for (MenuItemDto menuItemDto : restaurantDto.getMenuItems()) {
                MenuItem menuItem = convertMenuItemDtoToEntity(menuItemDto);
                menuItem.setRestaurant(savedRestaurant);
                menuItemRepository.save(menuItem);
            }
        }
        
        return convertToDto(restaurantRepository.findById(savedRestaurant.getId()).get());
    }

    @Transactional
    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        Optional<Restaurant> existingRestaurantOpt = restaurantRepository.findById(id);
        
        if (existingRestaurantOpt.isPresent()) {
            Restaurant existingRestaurant = existingRestaurantOpt.get();
            
            // Check if name is being changed and if new name already exists
            if (!existingRestaurant.getName().equals(restaurantDto.getName()) && 
                restaurantRepository.findByName(restaurantDto.getName()).isPresent()) {
                throw new RuntimeException("Restaurant with this name already exists");
            }
            
            // Update basic restaurant details
            existingRestaurant.setName(restaurantDto.getName());
            existingRestaurant.setDescription(restaurantDto.getDescription());
            existingRestaurant.setAddress(restaurantDto.getAddress());
            existingRestaurant.setPhone(restaurantDto.getPhone());
            existingRestaurant.setEmail(restaurantDto.getEmail());
            existingRestaurant.setOpeningHours(restaurantDto.getOpeningHours());
            existingRestaurant.setRating(restaurantDto.getRating());
            existingRestaurant.setImageUrl(restaurantDto.getImageUrl());
            
            Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
            return convertToDto(updatedRestaurant);
        }
        
        return null;
    }

    @Transactional
    public boolean deleteRestaurant(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public MenuItemDto addMenuItem(Long restaurantId, MenuItemDto menuItemDto) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        
        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();
            
            MenuItem menuItem = new MenuItem();
            menuItem.setName(menuItemDto.getName());
            menuItem.setDescription(menuItemDto.getDescription());
            menuItem.setPrice(menuItemDto.getPrice());
            menuItem.setCategory(menuItemDto.getCategory());
            menuItem.setVegetarian(menuItemDto.isVegetarian());
            menuItem.setAvailable(menuItemDto.isAvailable());
            menuItem.setImageUrl(menuItemDto.getImageUrl());
            menuItem.setRestaurant(restaurant);
            
            MenuItem savedMenuItem = menuItemRepository.save(menuItem);
            
            return convertMenuItemToDto(savedMenuItem);
        }
        
        return null;
    }

    @Transactional
    public MenuItemDto updateMenuItem(Long restaurantId, Long menuItemId, MenuItemDto menuItemDto) {
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(menuItemId);
        
        if (menuItemOpt.isPresent()) {
            MenuItem menuItem = menuItemOpt.get();
            
            // Verify that the menu item belongs to the correct restaurant
            if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
                throw new RuntimeException("Menu item does not belong to the specified restaurant");
            }
            
            // Update menu item details
            menuItem.setName(menuItemDto.getName());
            menuItem.setDescription(menuItemDto.getDescription());
            menuItem.setPrice(menuItemDto.getPrice());
            menuItem.setCategory(menuItemDto.getCategory());
            menuItem.setVegetarian(menuItemDto.isVegetarian());
            menuItem.setAvailable(menuItemDto.isAvailable());
            menuItem.setImageUrl(menuItemDto.getImageUrl());
            
            MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
            return convertMenuItemToDto(updatedMenuItem);
        }
        
        return null;
    }

    @Transactional
    public boolean deleteMenuItem(Long restaurantId, Long menuItemId) {
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(menuItemId);
        
        if (menuItemOpt.isPresent()) {
            MenuItem menuItem = menuItemOpt.get();
            
            // Verify that the menu item belongs to the correct restaurant
            if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
                throw new RuntimeException("Menu item does not belong to the specified restaurant");
            }
            
            menuItemRepository.delete(menuItem);
            return true;
        }
        
        return false;
    }

    public List<MenuItemDto> getMenuItemsByRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        
        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();
            return menuItemRepository.findByRestaurant(restaurant).stream()
                    .map(this::convertMenuItemToDto)
                    .collect(Collectors.toList());
        }
        
        return List.of();
    }

    // Search menu items by name across all restaurants
    public List<MenuItemDto> searchMenuItemsByName(String name) {
        return menuItemRepository.findByNameContaining(name).stream()
                .map(this::convertMenuItemToDto)
                .collect(Collectors.toList());
    }

    // Search menu items by price less than a value
    public List<MenuItemDto> searchMenuItemsByMaxPrice(BigDecimal maxPrice) {
        return menuItemRepository.findByPriceLessThan(maxPrice).stream()
                .map(this::convertMenuItemToDto)
                .collect(Collectors.toList());
    }

    // Search vegetarian menu items
    public List<MenuItemDto> searchVegetarianItems() {
        return menuItemRepository.findByIsVegetarian(true).stream()
                .map(this::convertMenuItemToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RestaurantDto updateRestaurantCapacity(Long id, Integer maxOrders) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
        
        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();
            restaurant.setMaxOrders(maxOrders);
            Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
            return convertToDto(updatedRestaurant);
        }
        
        return null;
    }
    
    @Transactional
    public MenuItemDto addMenuItemByRestaurantName(String restaurantName, MenuItemDto menuItemDto) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findByName(restaurantName);
        
        if (restaurantOpt.isEmpty()) {
            throw new RuntimeException("Restaurant not found: " + restaurantName);
        }
        
        Restaurant restaurant = restaurantOpt.get();
        
        // Check if menu item with the same name already exists
        boolean itemExists = restaurant.getMenuItems().stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(menuItemDto.getName()));
        
        if (itemExists) {
            throw new RuntimeException("Menu item with this name already exists: " + menuItemDto.getName());
        }
        
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setCategory(menuItemDto.getCategory());
        menuItem.setVegetarian(menuItemDto.isVegetarian());
        menuItem.setAvailable(menuItemDto.isAvailable());
        menuItem.setImageUrl(menuItemDto.getImageUrl());
        menuItem.setRestaurant(restaurant);
        
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        
        return convertMenuItemToDto(savedMenuItem);
    }
    
    @Transactional
    public MenuItemDto updateMenuItemByName(String restaurantName, String itemName, MenuItemDto menuItemDto) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findByName(restaurantName);
        
        if (restaurantOpt.isEmpty()) {
            throw new RuntimeException("Restaurant not found: " + restaurantName);
        }
        
        Restaurant restaurant = restaurantOpt.get();
        
        // Find the menu item by name
        Optional<MenuItem> menuItemOpt = restaurant.getMenuItems().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst();
        
        if (menuItemOpt.isEmpty()) {
            throw new RuntimeException("Menu item not found: " + itemName);
        }
        
        MenuItem menuItem = menuItemOpt.get();
        
        // Update menu item details
        if (menuItemDto.getName() != null) {
            menuItem.setName(menuItemDto.getName());
        }
        if (menuItemDto.getDescription() != null) {
            menuItem.setDescription(menuItemDto.getDescription());
        }
        if (menuItemDto.getPrice() != null) {
            menuItem.setPrice(menuItemDto.getPrice());
        }
        if (menuItemDto.getCategory() != null) {
            menuItem.setCategory(menuItemDto.getCategory());
        }
        menuItem.setVegetarian(menuItemDto.isVegetarian());
        menuItem.setAvailable(menuItemDto.isAvailable());
        if (menuItemDto.getImageUrl() != null) {
            menuItem.setImageUrl(menuItemDto.getImageUrl());
        }
        
        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
        return convertMenuItemToDto(updatedMenuItem);
    }

    // Conversion methods
    private RestaurantDto convertToDto(Restaurant restaurant) {
        RestaurantDto dto = new RestaurantDto();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setAddress(restaurant.getAddress());
        dto.setPhone(restaurant.getPhone());
        dto.setEmail(restaurant.getEmail());
        dto.setOpeningHours(restaurant.getOpeningHours());
        dto.setRating(restaurant.getRating());
        dto.setImageUrl(restaurant.getImageUrl());
        dto.setMaxOrders(restaurant.getMaxOrders());
        dto.setCurrentOrders(restaurant.getCurrentOrders());
        
        // Only convert menu items if they're available (not null and already initialized)
        if (restaurant.getMenuItems() != null && Hibernate.isInitialized(restaurant.getMenuItems()) && !restaurant.getMenuItems().isEmpty()) {
            dto.setMenuItems(restaurant.getMenuItems().stream()
                    .map(this::convertMenuItemToDto)
                    .collect(Collectors.toList()));
        } else {
            // Initialize with empty list to avoid null pointer
            dto.setMenuItems(new ArrayList<>());
        }
        
        return dto;
    }

    private Restaurant convertToEntity(RestaurantDto dto) {
        Restaurant restaurant = new Restaurant();
        if (dto.getId() != null) {
            restaurant.setId(dto.getId());
        }
        restaurant.setName(dto.getName());
        restaurant.setDescription(dto.getDescription());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhone(dto.getPhone());
        restaurant.setEmail(dto.getEmail());
        restaurant.setOpeningHours(dto.getOpeningHours());
        restaurant.setRating(dto.getRating());
        restaurant.setImageUrl(dto.getImageUrl());
        restaurant.setMaxOrders(dto.getMaxOrders());
        restaurant.setCurrentOrders(dto.getCurrentOrders());
        return restaurant;
    }

    private MenuItemDto convertMenuItemToDto(MenuItem menuItem) {
        MenuItemDto dto = new MenuItemDto();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setRestaurantId(menuItem.getRestaurant().getId());
        dto.setCategory(menuItem.getCategory());
        dto.setVegetarian(menuItem.isVegetarian());
        dto.setAvailable(menuItem.isAvailable());
        dto.setImageUrl(menuItem.getImageUrl());
        return dto;
    }

    private MenuItem convertMenuItemDtoToEntity(MenuItemDto dto) {
        MenuItem menuItem = new MenuItem();
        if (dto.getId() != null) {
            menuItem.setId(dto.getId());
        }
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        menuItem.setVegetarian(dto.isVegetarian());
        menuItem.setAvailable(dto.isAvailable());
        menuItem.setImageUrl(dto.getImageUrl());
        return menuItem;
    }
} 