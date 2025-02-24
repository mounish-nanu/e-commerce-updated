package com.e_commerce.paymentservice.adapters.Stripe;

import com.stripe.Stripe;
import com.stripe.StripeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfiguration {
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    @Bean
    public StripeClient stripeClient() {
        Stripe.apiKey = stripeApiKey;
        return new StripeClient(stripeApiKey);
    }
}
