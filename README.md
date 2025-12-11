# ECommerceOrderEngine

A high-performance e-commerce inventory and order processing system built in Java.

## Features
- **User Roles**: Buyer (no login, cart system, returns) and Owner (login, inventory management).
- **Inventory Management**: Uses HashMap for fast lookups and TreeMap for sorted stock access. CRUD operations for owners.
- **Order Processing**: PriorityQueue-based order matching with concurrency simulation. Discounts applied.
- **Payment Simulation**: Randomized payment success/failure.
- **Low-Stock Alerts**: Automatic alerts when stock drops below threshold.
- **Cart System**: Temporary cart for buyers with bill generation.
- **Returns**: Buyers can return items, adding back to stock.
- **Multithreading**: Thread-safe operations with ExecutorService.

## Structure
- `inventory/`: Product and inventory management.
- `order/`: Order entities, processing, and queue.
- `payment/`: Payment simulation service.
- `alert/`: Low-stock alert service.
- `Main.java`: Entry point with interactive menus.



## Example Interaction
- Choose "2" for Owner, login/create account, view/add items.
- Choose "1" for Buyer, add to cart, checkout (with discount), or return items.

BY: 
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
