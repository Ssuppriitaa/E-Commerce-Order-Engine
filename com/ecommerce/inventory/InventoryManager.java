package com.ecommerce.inventory;

import com.ecommerce.alert.LowStockAlertService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryManager {
    private ConcurrentHashMap<String, Product> inventory = new ConcurrentHashMap<>();
    private TreeMap<Integer, List<Product>> stockSorted = new TreeMap<>();
    private LowStockAlertService alertService;

    public InventoryManager(LowStockAlertService alertService) {
        this.alertService = alertService;
    }

    public void addProduct(Product product) {
        inventory.put(product.getId(), product);
        stockSorted.computeIfAbsent(product.getStock(), k -> new ArrayList<>()).add(product);
        checkLowStock(product);
    }

    public void deleteProduct(String id) {
        Product product = inventory.remove(id);
        if (product != null) {
            stockSorted.get(product.getStock()).remove(product);
        }
    }

    public void updatePrice(String id, double newPrice) {
        Product product = inventory.get(id);
        if (product != null) {
            product.setPrice(newPrice);
        }
    }

    public void updateDiscount(String id, double newDiscount) {
        Product product = inventory.get(id);
        if (product != null) {
            product.setDiscount(newDiscount);
        }
    }

    public Product getProduct(String id) {
        return inventory.get(id);
    }

    public boolean checkStock(String productId, int quantity) {
        Product product = getProduct(productId);
        return product != null && product.getStock() >= quantity;
    }

    public void reduceStock(String productId, int quantity) {
        Product product = getProduct(productId);
        if (product != null) {
            synchronized (this) {
                stockSorted.get(product.getStock()).remove(product);
                product.setStock(product.getStock() - quantity);
                stockSorted.computeIfAbsent(product.getStock(), k -> new ArrayList<>()).add(product);
            }
            checkLowStock(product);
        }
    }

    public void increaseStock(String productId, int quantity) {
        Product product = getProduct(productId);
        if (product != null) {
            synchronized (this) {
                stockSorted.get(product.getStock()).remove(product);
                product.setStock(product.getStock() + quantity);
                stockSorted.computeIfAbsent(product.getStock(), k -> new ArrayList<>()).add(product);
            }
        }
    }

    public void viewInventory() {
        System.out.println("Current Inventory:");
        for (Product p : inventory.values()) {
            System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Price: $" + p.getPrice() + ", Stock: " + p.getStock() + ", Discount: " + p.getDiscount() + "%");
        }
    }

    private void checkLowStock(Product product) {
        if (product.getStock() < 5) {
            alertService.sendLowStockAlert(product);
        }
    }

    public TreeMap<Integer, List<Product>> getProductsByStock() {
        return stockSorted;
    }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/