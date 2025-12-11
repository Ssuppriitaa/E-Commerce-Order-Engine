package com.ecommerce.order;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderQueue {
    private PriorityQueue<Order> orderQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.getType().getPriority()));
    private ExecutorService executor = Executors.newFixedThreadPool(5);
    private OrderProcessor orderProcessor;

    public OrderQueue(OrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    public void addOrder(Order order) {
        orderQueue.add(order);
        processNextOrder();
    }

    private void processNextOrder() {
        executor.submit(() -> {
            Order order = orderQueue.poll();
            if (order != null) {
                orderProcessor.processOrder(order);
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/