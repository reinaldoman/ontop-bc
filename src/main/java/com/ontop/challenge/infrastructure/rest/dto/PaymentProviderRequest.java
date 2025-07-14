package com.ontop.challenge.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentProviderRequest {
    private Source source;
    private Destination destination;
    private BigDecimal amount;
    
    @Data
    public static class Source {
        private String type;
        
        @JsonProperty("sourceInformation")
        private SourceInformation sourceInformation;
        private Account account;
    }
    
    @Data
    public static class SourceInformation {
        private String name;
        
        public SourceInformation() {}
        
        public SourceInformation(String name) {
            this.name = name;
        }
    }
    
    @Data
    public static class Account {
        @JsonProperty("accountNumber")
        private String accountNumber;
        private String currency;
        @JsonProperty("routingNumber")
        private String routingNumber;
    }
    
    @Data
    public static class Destination {
        private String name;
        private Account account;
    }
}