# FoodZap
🍔 FoodZap - Food Ordering System 🍕
!Version
!Spring Boot
!License
📋 Overview
FoodZap is a comprehensive food ordering system built with Java and Spring Boot. It provides a robust backend for restaurant management, menu handling, and order processing, with smart restaurant selection based on different strategies.
✨ Features Implemented
👤 User Management
User Registration and Authentication: Create and manage user accounts
Profile Management: Update user information including name, email, address, and phone number
💰 Wallet System: Each user has a wallet balance (initialized at 100.00) for payment transactions
🏪 Restaurant Management
Restaurant Registration: Add new restaurants with detailed information
🔍 Restaurant Discovery: Search restaurants by name, rating, or location
Restaurant Details: View restaurant information including ratings, address, and opening hours
⚖️ Capacity Management: Each restaurant has a maximum order capacity to prevent overloading
📝 Menu Management
Menu Item Creation: Add menu items with name, description, price, and category
🥗 Menu Customization: Set vegetarian options and availability status
Menu Item Updates: Update prices, descriptions, and availability
🛒 Order System
🧠 Smart Restaurant Selection: Two intelligent ordering strategies:
💸 Lowest Cost Strategy: Selects the restaurant offering the requested item at the lowest price
⭐ Highest Rating Strategy: Selects the highest rated restaurant that offers the requested item
Order Placement: Place orders with specific menu items, quantities, and special instructions
💳 Wallet Integration: Orders automatically deduct from user wallet balance with balance verification
📊 Order Status Tracking: Track orders through statuses (PENDING, PREPARING, OUT_FOR_DELIVERY, DELIVERED, COMPLETED, CANCELLED)
🚀 Advanced Order Processing
🔢 Restaurant Capacity Check: Ensures restaurants aren't overloaded with orders
✅ Menu Item Availability Check: Verifies items are available before placing orders
💵 Wallet Balance Validation: Ensures users have sufficient funds for orders
🛠️ Technical Stack
🧩 Backend: Java with Spring Boot
🗄️ Database Access: Spring Data JPA with Hibernate
🌐 API Layer: RESTful web services with Spring MVC
✓ Data Validation: Bean validation
🗃️ Database: Compatible with various SQL databases (MySQL, PostgreSQL, H2)
🏗️ Architecture
The application follows a layered architecture:
🎮 Controller Layer: REST API endpoints for client interaction
⚙️ Service Layer: Business logic and transaction management
📚 Repository Layer: Data access and persistence
📄 Model Layer: Entity definitions and relationships
🔄 DTO Layer: Data transfer objects for API communication
🔧 Utility Layer: Helper classes and strategy implementations
🧩 Key Components
📋 Models
👤 User: User account with wallet functionality
🏪 Restaurant: Restaurant details with capacity management
🍕 MenuItem: Food items with price and availability
🛍️ Order: Order details with status tracking
🧾 OrderItem: Individual items within an order
🧠 Strategies
🔀 OrderAssignmentStrategy: Interface for restaurant selection algorithms
💰 LowestCostStrategy: Selects restaurant with lowest price for menu items
🌟 HighestRatingStrategy: Selects highest rated restaurant offering the menu item
