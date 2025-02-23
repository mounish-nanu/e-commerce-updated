package com.e_commerce.paymentservice.service;

import com.e_commerce.paymentservice.adapters.PaymentGatewayAdapter;
import com.e_commerce.paymentservice.adapters.PaymentGatewayStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String createpaymentlink(Long orderid){
//        assume we already have oder object
        Long price = 123L;
        PaymentGatewayAdapter paymentGatewayAdapter = PaymentGatewayStrategy.getpaymentstrategy();
        String url = paymentGatewayAdapter.createpaymentlink(price);
        return url;

    }
}
