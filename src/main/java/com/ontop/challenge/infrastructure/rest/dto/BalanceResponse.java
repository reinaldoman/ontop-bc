package com.ontop.challenge.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceResponse {
    @JsonProperty("balance")
    private BigDecimal balance;
    
    @JsonProperty("user_id")
    private Long userId;
}