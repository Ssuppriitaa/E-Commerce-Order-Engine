package com.ecommerce.payment;

import java.util.Random;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public boolean processPayment(double amount) {
        // Simulate payment: 90% success rate
        return new Random().nextInt(10) < 9;
    }
}
/*
NAME: SUPRITA THAKUR
EMAIL: Thakursuprita30@gmail.com
*/