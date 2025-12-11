package com.ecommerce.alert;

import com.ecommerce.inventory.Product;

public class LowStockAlertServiceImpl implements LowStockAlertService {
    @Override
    public void sendLowStockAlert(Product product) {
        System.out.println("ALERT: Low stock for product " + product.getName() + " (ID: " + product.getId() + "), stock: " + product.getStock());
    }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/