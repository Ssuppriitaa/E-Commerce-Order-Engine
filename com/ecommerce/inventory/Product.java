package com.ecommerce.inventory;

public class Product {
    private String id;
    private String name;
    private double price;
    private int stock;
    private double discount; // Default 10% if not set

    public Product(String id, String name, double price, int stock) {
        this(id, name, price, stock, 10.0); // Default discount
    }

    public Product(String id, String name, double price, int stock, double discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.discount = discount;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/