package com.ontop.challenge.infrastructure.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ontop.challenge.application.ports.out.PaymentPort;
import com.ontop.challenge.domain.model.PaymentResponse;
import com.ontop.challenge.domain.model.RecipientAccount;
import com.ontop.challenge.infrastructure.rest.dto.PaymentProviderRequest;
import com.ontop.challenge.infrastructure.rest.dto.PaymentProviderResponse;

import java.math.BigDecimal;

@Component
public class PaymentRestAdapter implements PaymentPort {

    private final RestTemplate restTemplate;
    private final String paymentsUrl;
    private final String companyAccountNumber;
    private final String companyRoutingNumber;

    @Autowired
    public PaymentRestAdapter(
            RestTemplate restTemplate,
            @Value("${mock.api.payments.url:http://localhost:3000/api/v1/payments}") String paymentsUrl,
            @Value("${ontop.company.account.number:0245253419}") String companyAccountNumber,
            @Value("${ontop.company.routing.number:028444018}") String companyRoutingNumber) {
        this.restTemplate = restTemplate;
        this.paymentsUrl = paymentsUrl;
        this.companyAccountNumber = companyAccountNumber;
        this.companyRoutingNumber = companyRoutingNumber;
    }

    @Override
    public PaymentResponse createPayment(Long userId, BigDecimal amount, RecipientAccount recipientAccount) {
        PaymentProviderRequest request = buildPaymentRequest(amount, recipientAccount);
        
        try {
            PaymentProviderResponse response = restTemplate.postForObject(
                paymentsUrl, 
                request, 
                PaymentProviderResponse.class
            );
            
            if (response == null || response.getPaymentInfo() == null) {
                throw new RuntimeException("Payment provider returned invalid response");
            }
            
            return new PaymentResponse(response.getPaymentInfo().getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to process payment: " + e.getMessage(), e);
        }
    }

    private PaymentProviderRequest buildPaymentRequest(BigDecimal amount, RecipientAccount recipientAccount) {
        PaymentProviderRequest request = new PaymentProviderRequest();
        request.setAmount(amount);
        
        // Set source (Ontop company account)
        PaymentProviderRequest.Source source = new PaymentProviderRequest.Source();
        source.setType("COMPANY");
        source.setSourceInformation(new PaymentProviderRequest.SourceInformation("ONTOP INC"));
        
        PaymentProviderRequest.Account sourceAccount = new PaymentProviderRequest.Account();
        sourceAccount.setAccountNumber(companyAccountNumber);
        sourceAccount.setCurrency("USD");
        sourceAccount.setRoutingNumber(companyRoutingNumber);
        source.setAccount(sourceAccount);
        request.setSource(source);
        
        // Set destination (recipient account)
        PaymentProviderRequest.Destination destination = new PaymentProviderRequest.Destination();
        destination.setName(recipientAccount.getFirstName() + " " + recipientAccount.getLastName());
        
        PaymentProviderRequest.Account destinationAccount = new PaymentProviderRequest.Account();
        destinationAccount.setAccountNumber(recipientAccount.getAccountNumber());
        destinationAccount.setCurrency("USD");
        destinationAccount.setRoutingNumber(recipientAccount.getRoutingNumber());
        destination.setAccount(destinationAccount);
        request.setDestination(destination);
        
        return request;
    }
}