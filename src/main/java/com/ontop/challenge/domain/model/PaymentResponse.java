package com.ontop.challenge.domain.model;

import lombok.Data;

@Data
public class PaymentResponse {
    private String paymentId;
    
    public PaymentResponse() {}
    
    public PaymentResponse(String paymentId) {
        this.paymentId = paymentId;
    }
}