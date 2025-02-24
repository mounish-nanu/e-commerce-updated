package com.e_commerce.paymentservice.adapters;

import com.e_commerce.paymentservice.adapters.Razorpay.RazorpayPaymentGatewayAdapter;
import com.e_commerce.paymentservice.adapters.Stripe.StripePaymentGatewayAdapter;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentGatewayStrategy {
    private final StripePaymentGatewayAdapter stripePaymentGatewayAdapter;
    public PaymentGatewayStrategy(StripePaymentGatewayAdapter stripePaymentGatewayAdapter){
        this.stripePaymentGatewayAdapter = stripePaymentGatewayAdapter;
    }
    public PaymentGatewayAdapter getpaymentstrategy() {
//        int number = new Random().nextInt(10);
        return stripePaymentGatewayAdapter;
//        if(number % 2 == 0) {
//            return new RazorpayPaymentGatewayAdapter();
//        }
//        else{
//            return new StripePaymentGatewayAdapter();
//        }
    }
}
