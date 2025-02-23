package com.e_commerce.paymentservice.controllers;

import com.e_commerce.paymentservice.dtos.CreatePaymentLinkRequestDto;
import com.e_commerce.paymentservice.dtos.CreatePaymentLinkResponseDto;
import com.e_commerce.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/")
    public CreatePaymentLinkResponseDto createPaymentLink(@RequestBody CreatePaymentLinkRequestDto request) {
        String url = paymentService.createpaymentlink(request.getOrderid());
        CreatePaymentLinkResponseDto response = new CreatePaymentLinkResponseDto();
        response.setUrl(url);
        return response;
    }
}
