package com.e_commerce.paymentservice.adapters.Stripe;

import com.e_commerce.paymentservice.adapters.PaymentGatewayAdapter;
import com.stripe.StripeClient;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGatewayAdapter implements PaymentGatewayAdapter {
    private StripeClient stripeClient;
    public StripePaymentGatewayAdapter(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }
    public String createpaymentlink(Long price) throws Exception {
//    ProductCreateParams params =
//            ProductCreateParams.builder()
//                    .setDescription("(created by Stripe Shell)")
//                    .setName("charity")
//                    .setActive(true)
//                    .setDefaultPriceData(
//                            ProductCreateParams.DefaultPriceData.builder()
//                                    .setCurrency("usd")
//                                    .setUnitAmount(1000000L)
//                                    .build()
//                    )
//                    .build();
//
//    Product product = Product.create(params);
//    PriceCreateParams params =
//            PriceCreateParams.builder()
//                    .setCurrency("usd")
//                    .setProduct("prod_RpQa43Aks17Sjx")
//                    .setUnitAmount(1000000L)
//                    .build();
//
//    Price price = Price.create(params);

        PaymentLinkCreateParams params =
                PaymentLinkCreateParams.builder()
                        .setCurrency("usd")
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice("price_1QvloVInNRh3dReaULDVIvME")
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://youtube.com")
                                                        .build()
                                        )
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(params);
        return paymentLink.getUrl();
    }
}
