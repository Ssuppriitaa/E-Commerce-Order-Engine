package com.ecommerce.order;

public abstract class Order {
    private String id;
    private String productId;
    private int quantity;
    private String customerId;
    private OrderType type;

    public Order(String id, String productId, int quantity, String customerId, OrderType type) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.customerId = customerId;
        this.type = type;
    }

    // Getters
    public String getId() { return id; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public String getCustomerId() { return customerId; }
    public OrderType getType() { return type; }

    public abstract double getTotalPrice(com.ecommerce.inventory.Product product);
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/