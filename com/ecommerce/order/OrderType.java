package com.ecommerce.order;

public enum OrderType {
    STANDARD(1), VIP(2);

    private int priority;

    OrderType(int priority) { this.priority = priority; }
    public int getPriority() { return priority; }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/