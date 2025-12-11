package com.ecommerce.order;

public class BuyOrder extends Order {
    public BuyOrder(String id, String productId, int quantity, String customerId, OrderType type) {
        super(id, productId, quantity, customerId, type);
    }

    @Override
    public double getTotalPrice(com.ecommerce.inventory.Product product) {
        return product.getPrice() * getQuantity();
    }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/