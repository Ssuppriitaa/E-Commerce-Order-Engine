package com.ecommerce.order;

import com.ecommerce.inventory.InventoryManager;
import com.ecommerce.payment.PaymentService;

public class OrderProcessor {
    private InventoryManager inventoryManager;
    private PaymentService paymentService;

    public OrderProcessor(InventoryManager inventoryManager, PaymentService paymentService) {
        this.inventoryManager = inventoryManager;
        this.paymentService = paymentService;
    }

    public boolean processOrder(Order order) {
        if (inventoryManager.checkStock(order.getProductId(), order.getQuantity())) {
            com.ecommerce.inventory.Product product = inventoryManager.getProduct(order.getProductId());
            double total = order.getTotalPrice(product) * (1 - product.getDiscount() / 100);
            if (paymentService.processPayment(total)) {
                inventoryManager.reduceStock(order.getProductId(), order.getQuantity());
                System.out.println("Order " + order.getId() + " processed successfully. Total: $" + total);
                return true;
            } else {
                System.out.println("Payment failed for order " + order.getId());
            }
        } else {
            System.out.println("Insufficient stock for order " + order.getId());
        }
        return false;
    }

    public void processReturn(String productId, int quantity) {
        inventoryManager.increaseStock(productId, quantity);
        System.out.println("Return processed: " + quantity + " units of " + productId + " added back to stock.");
    }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/