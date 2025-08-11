package com.e_commerce.paymentservice.service;

import com.e_commerce.paymentservice.adapters.PaymentGatewayAdapter;
import com.e_commerce.paymentservice.adapters.PaymentGatewayStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentGatewayStrategy paymentGatewayStrategy;

    public PaymentService(PaymentGatewayStrategy paymentGatewayStrategy) {
        this.paymentGatewayStrategy = paymentGatewayStrategy;
    }
    public String createpaymentlink(Long orderid){
//        assume we already have order object
        Long price = 123L;
        PaymentGatewayAdapter paymentGatewayAdapter = paymentGatewayStrategy.getpaymentstrategy();

        String url = "";
        try{
            url = paymentGatewayAdapter.createpaymentlink(price);
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;

    }
}
