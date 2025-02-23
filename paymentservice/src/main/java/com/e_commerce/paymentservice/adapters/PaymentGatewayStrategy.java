package com.e_commerce.paymentservice.adapters;

import java.util.Random;

public class PaymentGatewayStrategy {
    public static PaymentGatewayAdapter getpaymentstrategy() {
        int number = new Random().nextInt(10);
        if(number % 2 == 0) {
            return new RazorpayPaymentGatewayAdapter();
        }
        else{
            return new StripePaymentGatewayAdapter();
        }
    }
}
