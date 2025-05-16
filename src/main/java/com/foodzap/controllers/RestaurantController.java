package com.foodzap.controllers;

import com.foodzap.dto.ErrorResponse;
import com.foodzap.dto.MenuItemDto;
import com.foodzap.dto.RestaurantDto;
import com.foodzap.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Long id) {
        RestaurantDto restaurant = restaurantService.getRestaurantById(id);
        if (restaurant != null) {
            return ResponseEntity.ok(restaurant);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestaurantDto> getRestaurantByName(@PathVariable String name) {
        RestaurantDto restaurant = restaurantService.getRestaurantByName(name);
        if (restaurant != null) {
            return ResponseEntity.ok(restaurant);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<RestaurantDto>> getRestaurantsByRating(@PathVariable Float rating) {
        List<RestaurantDto> restaurants = restaurantService.getRestaurantsByRating(rating);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<RestaurantDto>> getRestaurantsByLocation(@PathVariable String location) {
        List<RestaurantDto> restaurants = restaurantService.getRestaurantsByLocation(location);
        return ResponseEntity.ok(restaurants);
    }

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        try {
            RestaurantDto createdRestaurant = restaurantService.createRestaurant(restaurantDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDto restaurantDto) {
        try {
            RestaurantDto updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDto);
            if (updatedRestaurant != null) {
                return ResponseEntity.ok(updatedRestaurant);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Restaurant not found with id: " + id, HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/{id}/capacity")
    public ResponseEntity<?> updateCapacity(@PathVariable Long id, @RequestBody Map<String, Integer> capacityData) {
        try {
            if (!capacityData.containsKey("maxOrders")) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("maxOrders field is required", HttpStatus.BAD_REQUEST.value()));
            }
            
            Integer maxOrders = capacityData.get("maxOrders");
            if (maxOrders < 0) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("maxOrders cannot be negative", HttpStatus.BAD_REQUEST.value()));
            }
            
            RestaurantDto updatedRestaurant = restaurantService.updateRestaurantCapacity(id, maxOrders);
            if (updatedRestaurant != null) {
                return ResponseEntity.ok(updatedRestaurant);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Restaurant not found with id: " + id, HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        try {
            boolean deleted = restaurantService.deleteRestaurant(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Restaurant not found with id: " + id, HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // Menu Item Endpoints
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItemDto>> getMenuItems(@PathVariable Long restaurantId) {
        List<MenuItemDto> menuItems = restaurantService.getMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<?> addMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItemDto menuItemDto) {
        try {
            MenuItemDto createdMenuItem = restaurantService.addMenuItem(restaurantId, menuItemDto);
            if (createdMenuItem != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdMenuItem);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Restaurant not found with id: " + restaurantId, HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/name/{restaurantName}/menu")
    public ResponseEntity<?> addMenuItemByName(
            @PathVariable String restaurantName,
            @RequestBody MenuItemDto menuItemDto) {
        try {
            MenuItemDto createdMenuItem = restaurantService.addMenuItemByRestaurantName(restaurantName, menuItemDto);
            if (createdMenuItem != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdMenuItem);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Restaurant not found: " + restaurantName, HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/name/{restaurantName}/menu/{itemName}")
    public ResponseEntity<?> updateMenuItemByName(
            @PathVariable String restaurantName,
            @PathVariable String itemName,
            @RequestBody MenuItemDto menuItemDto) {
        try {
            MenuItemDto updatedMenuItem = restaurantService.updateMenuItemByName(restaurantName, itemName, menuItemDto);
            if (updatedMenuItem != null) {
                return ResponseEntity.ok(updatedMenuItem);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Restaurant or menu item not found", HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/{restaurantId}/menu/{menuItemId}")
    public ResponseEntity<?> updateMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId,
            @RequestBody MenuItemDto menuItemDto) {
        try {
            MenuItemDto updatedMenuItem = restaurantService.updateMenuItem(restaurantId, menuItemId, menuItemDto);
            if (updatedMenuItem != null) {
                return ResponseEntity.ok(updatedMenuItem);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Restaurant or menu item not found", HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{restaurantId}/menu/{menuItemId}")
    public ResponseEntity<?> deleteMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId) {
        try {
            boolean deleted = restaurantService.deleteMenuItem(restaurantId, menuItemId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Restaurant or menu item not found", HttpStatus.NOT_FOUND.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // Search Endpoints
    @GetMapping("/menu/search")
    public ResponseEntity<List<MenuItemDto>> searchMenuItems(@RequestParam String name) {
        List<MenuItemDto> menuItems = restaurantService.searchMenuItemsByName(name);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/menu/price/{maxPrice}")
    public ResponseEntity<List<MenuItemDto>> searchMenuItemsByPrice(@PathVariable BigDecimal maxPrice) {
        List<MenuItemDto> menuItems = restaurantService.searchMenuItemsByMaxPrice(maxPrice);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/menu/vegetarian")
    public ResponseEntity<List<MenuItemDto>> getVegetarianItems() {
        List<MenuItemDto> menuItems = restaurantService.searchVegetarianItems();
        return ResponseEntity.ok(menuItems);
    }
} 