package com.ecommerce;

import com.ecommerce.alert.LowStockAlertService;
import com.ecommerce.alert.LowStockAlertServiceImpl;
import com.ecommerce.inventory.InventoryManager;
import com.ecommerce.inventory.Product;
import com.ecommerce.order.*;
import com.ecommerce.payment.PaymentService;
import com.ecommerce.payment.PaymentServiceImpl;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, String> ownerCredentials = new HashMap<>(); // In-memory for simplicity
    private static LowStockAlertService alertService = new LowStockAlertServiceImpl();
    private static InventoryManager inventoryManager = new InventoryManager(alertService);
    private static PaymentService paymentService = new PaymentServiceImpl();
    private static OrderProcessor orderProcessor = new OrderProcessor(inventoryManager, paymentService);
    private static OrderQueue orderQueue = new OrderQueue(orderProcessor);

    public static void main(String[] args) {
        // Initialize sample products
        inventoryManager.addProduct(new Product("P1", "Laptop", 1000.0, 10));
        inventoryManager.addProduct(new Product("P2", "Phone", 500.0, 3));

        while (true) {
            System.out.println("\nWelcome to ECommerceOrderEngine");
            System.out.println("1. Buyer");
            System.out.println("2. Owner");
            System.out.println("3. Exit");
            System.out.print("Choose your role: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    buyerMenu();
                    break;
                case 2:
                    if (ownerLogin()) {
                        ownerMenu();
                    }
                    break;
                case 3:
                    orderQueue.shutdown();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void buyerMenu() {
        Map<String, Integer> cart = new HashMap<>();
        while (true) {
            System.out.println("\nBuyer Menu:");
            System.out.println("1. Buy Something (Add to Cart)");
            System.out.println("2. View Cart and Checkout");
            System.out.println("3. Return an Item");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addToCart(cart);
                    break;
                case 2:
                    checkout(cart);
                    cart.clear();
                    break;
                case 3:
                    returnItem();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addToCart(Map<String, Integer> cart) {
        inventoryManager.viewInventory();
        System.out.print("Enter Product ID to add: ");
        String id = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int qty = scanner.nextInt();
        scanner.nextLine();
        
        Product product = inventoryManager.getProduct(id);
        if (product == null) {
            System.out.println("ALERT: Product not found.");
            return;
        }
        
        if (!inventoryManager.checkStock(id, qty)) {
            System.out.println("ALERT: Insufficient stock for " + product.getName() + " (ID: " + id + "). Available: " + product.getStock());
            System.out.print("Do you want to add the available quantity (" + product.getStock() + ") instead? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("y") || response.equals("yes")) {
                qty = product.getStock(); // Add available quantity
            } else {
                return; // Do not add to cart
            }
        }
        
        cart.put(id, cart.getOrDefault(id, 0) + qty);
        System.out.println("Added to cart.");
    }

    private static void checkout(Map<String, Integer> cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        double total = 0;
        System.out.println("Cart Items:");
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Product p = inventoryManager.getProduct(entry.getKey());
            if (p != null) {
                double itemTotal = p.getPrice() * entry.getValue() * (1 - p.getDiscount() / 100);
                total += itemTotal;
                System.out.println(p.getName() + " x" + entry.getValue() + " = $" + itemTotal);
                // Simulate order processing
                Order order = new BuyOrder("O" + System.currentTimeMillis(), entry.getKey(), entry.getValue(), "Buyer", OrderType.STANDARD);
                orderQueue.addOrder(order);
            }
        }
        System.out.println("Total Bill: $" + total);
        // Wait briefly for processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Show updated inventory after purchase
        System.out.println("\nUpdated Inventory after Purchase:");
        inventoryManager.viewInventory();
    }

    private static void returnItem() {
        System.out.print("Enter Product ID to return: ");
        String id = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int qty = scanner.nextInt();
        scanner.nextLine();
        orderProcessor.processReturn(id, qty);
        // Show updated inventory after return
        System.out.println("\nUpdated Inventory after Return:");
        inventoryManager.viewInventory();
    }

    private static boolean ownerLogin() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        if (!ownerCredentials.containsKey(username)) {
            String password;
            do {
                System.out.print("First-time login. Set Password (minimum 6 characters): ");
                password = scanner.nextLine();
                if (password.length() < 6) {
                    System.out.println("Password must be at least 6 characters long. Please try again.");
                }
            } while (password.length() < 6);
            ownerCredentials.put(username, password);
            System.out.println("Account created.");
            return true;
        } else {
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            if (ownerCredentials.get(username).equals(password)) {
                return true;
            } else {
                System.out.println("Invalid password.");
                return false;
            }
        }
    }

    private static void ownerMenu() {
        while (true) {
            System.out.println("\nOwner Menu:");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Item");
            System.out.println("3. Delete Item");
            System.out.println("4. Change Price");
            System.out.println("5. Change Discount");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    inventoryManager.viewInventory();
                    break;
                case 2:
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Stock: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine();
                    inventoryManager.addProduct(new Product(id, name, price, stock));
                    break;
                case 3:
                    System.out.print("ID to delete: ");
                    id = scanner.nextLine();
                    inventoryManager.deleteProduct(id);
                    break;
                case 4:
                    System.out.print("ID: ");
                    id = scanner.nextLine();
                    System.out.print("New Price: ");
                    price = scanner.nextDouble();
                    scanner.nextLine();
                    inventoryManager.updatePrice(id, price);
                    break;
                case 5:
                    System.out.print("ID: ");
                    id = scanner.nextLine();
                    System.out.print("New Discount (%): ");
                    double discount = scanner.nextDouble();
                    scanner.nextLine();
                    inventoryManager.updateDiscount(id, discount);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/