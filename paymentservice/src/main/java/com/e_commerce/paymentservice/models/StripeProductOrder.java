package com.e_commerce.paymentservice.models;

//import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
public class StripeProductOrder extends BaseModel {
    private long productid;
    private String stripePriceId;
    private String stripeProductId;
}
