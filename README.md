# 🍔 FoodZap - Food Ordering System 🍕

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.0-green)
![License](https://img.shields.io/badge/license-MIT-orange)

## 📋 Overview
FoodZap is a comprehensive food ordering system built with Java and Spring Boot. It provides a robust backend for restaurant management, menu handling, and order processing, with smart restaurant selection based on different strategies.

## ✨ Features Implemented

### 👤 User Management
- **User Registration and Authentication**: Create and manage user accounts
- **Profile Management**: Update user information including name, email, address, and phone number
- **💰 Wallet System**: Each user has a wallet balance (initialized at 100.00) for payment transactions

### 🏪 Restaurant Management
- **Restaurant Registration**: Add new restaurants with detailed information
- **🔍 Restaurant Discovery**: Search restaurants by name, rating, or location
- **Restaurant Details**: View restaurant information including ratings, address, and opening hours
- **⚖️ Capacity Management**: Each restaurant has a maximum order capacity to prevent overloading

### 📝 Menu Management
- **Menu Item Creation**: Add menu items with name, description, price, and category
- **🥗 Menu Customization**: Set vegetarian options and availability status
- **Menu Item Updates**: Update prices, descriptions, and availability

### 🛒 Order System
- **🧠 Smart Restaurant Selection**: Two intelligent ordering strategies:
  - **💸 Lowest Cost Strategy**: Selects the restaurant offering the requested item at the lowest price
  - **⭐ Highest Rating Strategy**: Selects the highest rated restaurant that offers the requested item
- **Order Placement**: Place orders with specific menu items, quantities, and special instructions
- **💳 Wallet Integration**: Orders automatically deduct from user wallet balance with balance verification
- **📊 Order Status Tracking**: Track orders through statuses (PENDING, PREPARING, OUT_FOR_DELIVERY, DELIVERED, COMPLETED, CANCELLED)

### 🚀 Advanced Order Processing
- **🔢 Restaurant Capacity Check**: Ensures restaurants aren't overloaded with orders
- **✅ Menu Item Availability Check**: Verifies items are available before placing orders
- **💵 Wallet Balance Validation**: Ensures users have sufficient funds for orders

## 🛠️ Technical Stack

- **🧩 Backend**: Java with Spring Boot
- **🗄️ Database Access**: Spring Data JPA with Hibernate
- **🌐 API Layer**: RESTful web services with Spring MVC
- **✓ Data Validation**: Bean validation
- **🗃️ Database**: Compatible with various SQL databases (MySQL, PostgreSQL, H2)

## 🏗️ Architecture

The application follows a layered architecture:

1. **🎮 Controller Layer**: REST API endpoints for client interaction
2. **⚙️ Service Layer**: Business logic and transaction management
3. **📚 Repository Layer**: Data access and persistence
4. **📄 Model Layer**: Entity definitions and relationships
5. **🔄 DTO Layer**: Data transfer objects for API communication
6. **🔧 Utility Layer**: Helper classes and strategy implementations

## 🧩 Key Components

### 📋 Models
- `👤 User`: User account with wallet functionality
- `🏪 Restaurant`: Restaurant details with capacity management
- `🍕 MenuItem`: Food items with price and availability
- `🛍️ Order`: Order details with status tracking
- `🧾 OrderItem`: Individual items within an order

### 🧠 Strategies
- `🔀 OrderAssignmentStrategy`: Interface for restaurant selection algorithms
- `💰 LowestCostStrategy`: Selects restaurant with lowest price for menu items
- `🌟 HighestRatingStrategy`: Selects highest rated restaurant offering the menu item

## 🚀 Setup and Installation

1. Clone the repository:
```bash
git clone https://github.com/lakshaygeek511/FoodZap.git
cd foodzap-backend
```

2. Build the application:
```bash
./mvnw clean package
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

## 🔌 API Endpoints

### 👤 User Endpoints
- `GET /api/users`: Get all users
- `GET /api/users/{id}`: Get user by ID
- `GET /api/users/email/{email}`: Get user by email
- `POST /api/users`: Create new user
- `PUT /api/users/{id}`: Update user
- `DELETE /api/users/{id}`: Delete user

### 🏪 Restaurant Endpoints
- `GET /api/restaurants`: Get all restaurants
- `GET /api/restaurants/{id}`: Get restaurant by ID
- `GET /api/restaurants/name/{name}`: Get restaurant by name
- `GET /api/restaurants/rating/{rating}`: Get restaurants by minimum rating
- `POST /api/restaurants`: Create new restaurant
- `PUT /api/restaurants/{id}`: Update restaurant
- `DELETE /api/restaurants/{id}`: Delete restaurant

### 🍽️ Menu Item Endpoints
- `GET /api/restaurants/{id}/menu`: Get restaurant menu
- `POST /api/restaurants/{id}/menu`: Add menu item to restaurant
- `PUT /api/restaurants/{restaurantId}/menu/{menuItemId}`: Update menu item
- `DELETE /api/restaurants/{restaurantId}/menu/{menuItemId}`: Remove menu item

### 🛒 Order Endpoints
- `GET /api/orders`: Get all orders
- `GET /api/orders/{id}`: Get order by ID
- `GET /api/orders/user/{userId}`: Get orders by user
- `GET /api/orders/restaurant/{restaurantId}`: Get orders by restaurant
- `GET /api/orders/status/{status}`: Get orders by status
- `POST /api/orders/place`: Place order with smart restaurant selection
- `POST /api/orders`: Create order for specific restaurant
- `PUT /api/orders/{id}/status`: Update order status
- `DELETE /api/orders/{id}`: Delete order (only pending orders)

## 📄 License
[MIT License](LICENSE)

---

<p align="center">
  <img src="https://img.icons8.com/color/96/000000/pizza.png" alt="FoodZap Logo"/>
  <br>
  <em>Delicious food, delivered smart!</em>
</p>
