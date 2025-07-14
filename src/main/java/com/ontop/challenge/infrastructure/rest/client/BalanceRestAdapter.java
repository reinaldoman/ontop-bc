package com.ontop.challenge.infrastructure.rest.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.ontop.challenge.application.ports.out.BalancePort;
import com.ontop.challenge.domain.exception.BalanceServiceException;
import com.ontop.challenge.infrastructure.rest.dto.BalanceResponse;

import java.math.BigDecimal;

@Slf4j
@Component
public class BalanceRestAdapter implements BalancePort {

    private final RestTemplate restTemplate;
    private final String balanceUrl;

    public BalanceRestAdapter(
            RestTemplate restTemplate,
            @Value("${mock.api.balance-url}") String balanceUrl) {
        this.restTemplate = restTemplate;
        this.balanceUrl = balanceUrl;
    }

    @Override
    public BigDecimal getBalance(Long userId) {
        try {
            String url = String.format("%s?user_id=%d", balanceUrl, userId);
            BalanceResponse response = restTemplate.getForObject(url, BalanceResponse.class);
            
            if (response == null) {
                throw new BalanceServiceException("Empty response from balance service");
            }
            return response.getBalance();
            
        } catch (ResourceAccessException e) {
            log.error("Connection to balance service failed: {}", e.getMessage());
            throw new BalanceServiceException("Balance service unavailable", e);
        } catch (Exception e) {
            log.error("Error getting balance: {}", e.getMessage());
            throw new BalanceServiceException("Failed to retrieve balance", e);
        }
    }
}