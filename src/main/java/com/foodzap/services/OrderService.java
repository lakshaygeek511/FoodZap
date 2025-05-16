package com.foodzap.services;

import com.foodzap.constants.OrderStatus;
import com.foodzap.dto.OrderDto;
import com.foodzap.dto.OrderItemDto;
import com.foodzap.dto.OrderItemRequest;
import com.foodzap.dto.OrderRequest;
import com.foodzap.models.MenuItem;
import com.foodzap.models.Order;
import com.foodzap.models.OrderItem;
import com.foodzap.models.Restaurant;
import com.foodzap.models.User;
import com.foodzap.repositories.MenuItemRepository;
import com.foodzap.repositories.OrderItemRepository;
import com.foodzap.repositories.OrderRepository;
import com.foodzap.repositories.RestaurantRepository;
import com.foodzap.repositories.UserRepository;
import com.foodzap.utils.OrderAssignmentStrategy;
import com.foodzap.utils.OrderStrategyFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderStrategyFactory orderStrategyFactory;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository,
            OrderStrategyFactory orderStrategyFactory) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderStrategyFactory = orderStrategyFactory;
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(this::convertToDto).orElse(null);
    }

    public List<OrderDto> getOrdersByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return orderRepository.findByUser(user).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
        
        return List.of();
    }

    public List<OrderDto> getOrdersByRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        
        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();
            return orderRepository.findByRestaurant(restaurant).stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
        
        return List.of();
    }

    public List<OrderDto> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDto placeOrder(OrderRequest orderRequest) {
        // Validate user exists - try both name and email lookup
        User user = userRepository.findByName(orderRequest.getUsername())
                .orElseGet(() -> userRepository.findByEmail(orderRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + orderRequest.getUsername())));
        
        // Find eligible restaurants that can fulfill the order
        List<Restaurant> eligibleRestaurants = findEligibleRestaurants(orderRequest.getItems());
        
        if (eligibleRestaurants.isEmpty()) {
            throw new RuntimeException("No restaurant can fulfill this order with all requested items");
        }
        
        // Apply the selection strategy to find the best restaurant
        OrderAssignmentStrategy strategy = orderStrategyFactory.getStrategy(orderRequest.getSelectionStrategy());
        Restaurant selectedRestaurant = strategy.findBestRestaurant(eligibleRestaurants, orderRequest.getItems());
        
        if (selectedRestaurant == null) {
            throw new RuntimeException("Could not select a restaurant for this order");
        }
        
        // Check if restaurant has capacity
        if (!selectedRestaurant.hasCapacity()) {
            throw new RuntimeException("Selected restaurant " + selectedRestaurant.getName() + " is at full capacity");
        }
        
        // Calculate total order cost first
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Optional<MenuItem> menuItemOpt = selectedRestaurant.getMenuItems().stream()
                    .filter(item -> item.getName().equalsIgnoreCase(itemRequest.getItemName()))
                    .findFirst();
            
            if (menuItemOpt.isEmpty()) {
                throw new RuntimeException("Menu item not found: " + itemRequest.getItemName());
            }
            
            MenuItem menuItem = menuItemOpt.get();
            totalCost = totalCost.add(menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }
        
        // Check if user has sufficient wallet balance
        if (!user.hasEnoughBalance(totalCost)) {
            throw new RuntimeException("Insufficient wallet balance. Required: " + totalCost + ", Available: " + user.getWalletBalance());
        }
        
        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(selectedRestaurant);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setDeliveryAddress(orderRequest.getDeliveryAddress() != null ? 
                                 orderRequest.getDeliveryAddress() : user.getAddress());
        order.setNotes(orderRequest.getNotes());
        order.setTotalAmount(totalCost);
        
        Order savedOrder = orderRepository.save(order);
        
        // Process order items and deduct from wallet
        if (orderRequest.getItems() != null && !orderRequest.getItems().isEmpty()) {
            for (OrderItemRequest itemRequest : orderRequest.getItems()) {
                // Find matching menu item in the selected restaurant
                Optional<MenuItem> menuItemOpt = selectedRestaurant.getMenuItems().stream()
                        .filter(item -> item.getName().equalsIgnoreCase(itemRequest.getItemName()))
                        .findFirst();
                
                MenuItem menuItem = menuItemOpt.get(); // We already validated it exists
                
                // Check if menu item is available
                if (!menuItem.isAvailable()) {
                    throw new RuntimeException("Menu item is not available: " + menuItem.getName());
                }
                
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setMenuItem(menuItem);
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setPrice(menuItem.getPrice());
                orderItem.setSpecialInstructions(itemRequest.getSpecialInstructions());
                
                orderItemRepository.save(orderItem);
            }
            
            // Deduct from user's wallet
            user.deductFromWallet(totalCost);
            userRepository.save(user);
        }
        
        // Update restaurant capacity
        selectedRestaurant.incrementCurrentOrders();
        restaurantRepository.save(selectedRestaurant);
        
        return convertToDto(savedOrder);
    }

    /**
     * Find eligible restaurants that can fulfill all items in the order
     */
    private List<Restaurant> findEligibleRestaurants(List<OrderItemRequest> items) {
        if (items == null || items.isEmpty()) {
            return List.of();
        }
        
        List<Restaurant> allRestaurants = restaurantRepository.findAllWithMenuItems();
        List<Restaurant> eligibleRestaurants = new ArrayList<>();
        
        for (Restaurant restaurant : allRestaurants) {
            if (!restaurant.hasCapacity()) {
                continue; // Skip restaurants at full capacity
            }
            
            boolean canFulfillAllItems = true;
            
            for (OrderItemRequest itemRequest : items) {
                // Check if restaurant has this menu item
                boolean hasItem = restaurant.getMenuItems().stream()
                        .anyMatch(menuItem -> 
                            menuItem.getName().equalsIgnoreCase(itemRequest.getItemName()) && 
                            menuItem.isAvailable());
                
                if (!hasItem) {
                    canFulfillAllItems = false;
                    break;
                }
            }
            
            if (canFulfillAllItems) {
                eligibleRestaurants.add(restaurant);
            }
        }
        
        return eligibleRestaurants;
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        // Validate user exists
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Validate restaurant exists
        Restaurant restaurant = restaurantRepository.findById(orderDto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        
        // Check if restaurant has capacity
        if (!restaurant.hasCapacity()) {
            throw new RuntimeException("Restaurant is at full capacity");
        }
        
        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setNotes(orderDto.getNotes());
        order.setTotalAmount(BigDecimal.ZERO);
        
        Order savedOrder = orderRepository.save(order);
        
        // Process order items
        if (orderDto.getOrderItems() != null && !orderDto.getOrderItems().isEmpty()) {
            BigDecimal total = BigDecimal.ZERO;
            
            for (OrderItemDto itemDto : orderDto.getOrderItems()) {
                MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId())
                        .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemDto.getMenuItemId()));
                
                // Check if menu item belongs to the selected restaurant
                if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
                    throw new RuntimeException("Menu item doesn't belong to the selected restaurant");
                }
                
                // Check if menu item is available
                if (!menuItem.isAvailable()) {
                    throw new RuntimeException("Menu item is not available: " + menuItem.getName());
                }
                
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setMenuItem(menuItem);
                orderItem.setQuantity(itemDto.getQuantity());
                orderItem.setPrice(menuItem.getPrice());
                orderItem.setSpecialInstructions(itemDto.getSpecialInstructions());
                
                orderItemRepository.save(orderItem);
                
                // Add to total
                total = total.add(menuItem.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
            }
            
            // Update order total
            savedOrder.setTotalAmount(total);
            orderRepository.save(savedOrder);
        }
        
        // Update restaurant capacity
        restaurant.incrementCurrentOrders();
        restaurantRepository.save(restaurant);
        
        return convertToDto(savedOrder);
    }

    @Transactional
    public OrderDto updateOrderStatus(Long id, OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            OrderStatus oldStatus = order.getStatus();
            
            // Validate status transition
            if (status == OrderStatus.CANCELLED && 
                (order.getStatus() == OrderStatus.DELIVERED || 
                 order.getStatus() == OrderStatus.OUT_FOR_DELIVERY)) {
                throw new RuntimeException("Cannot cancel an order that is already out for delivery or delivered");
            }
            
            order.setStatus(status);
            
            // If order is delivered or completed, set delivery date
            if (status == OrderStatus.DELIVERED || status == OrderStatus.COMPLETED) {
                order.setDeliveryDate(LocalDateTime.now());
                
                // If order is completed, free up restaurant capacity
                if (status == OrderStatus.COMPLETED && oldStatus != OrderStatus.COMPLETED) {
                    Restaurant restaurant = order.getRestaurant();
                    restaurant.decrementCurrentOrders();
                    restaurantRepository.save(restaurant);
                }
            }
            
            Order updatedOrder = orderRepository.save(order);
            return convertToDto(updatedOrder);
        }
        
        return null;
    }

    @Transactional
    public boolean deleteOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            
            // Only allow deletion of pending orders
            if (order.getStatus() != OrderStatus.PENDING) {
                throw new RuntimeException("Only pending orders can be deleted");
            }
            
            // Free up restaurant capacity
            Restaurant restaurant = order.getRestaurant();
            restaurant.decrementCurrentOrders();
            restaurantRepository.save(restaurant);
            
            orderRepository.delete(order);
            return true;
        }
        
        return false;
    }

    // Conversion methods
    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setRestaurantId(order.getRestaurant().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setNotes(order.getNotes());
        
        // Add restaurant name for reference
        dto.setRestaurantName(order.getRestaurant().getName());
        dto.setUserName(order.getUser().getName());
        
        // Convert order items
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            dto.setOrderItems(order.getOrderItems().stream()
                    .map(this::convertOrderItemToDto)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    private OrderItemDto convertOrderItemToDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setOrderId(item.getOrder().getId());
        dto.setMenuItemId(item.getMenuItem().getId());
        dto.setMenuItemName(item.getMenuItem().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSpecialInstructions(item.getSpecialInstructions());
        return dto;
    }
} 