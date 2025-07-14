package com.ontop.challenge.infrastructure.rest.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentProviderResponse {
    @JsonProperty("requestInfo")
    private RequestInfo requestInfo;
    
    @JsonProperty("paymentInfo")
    private PaymentInfo paymentInfo;
    
    @Data
    public static class RequestInfo {
        private String status;
        private String error;
    }
    
    @Data
    public static class PaymentInfo {
        private BigDecimal amount;
        private String id;
    }
}