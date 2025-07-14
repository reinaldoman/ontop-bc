package com.ontop.challenge.application.ports.out;

import java.math.BigDecimal;

import com.ontop.challenge.domain.model.PaymentResponse;
import com.ontop.challenge.domain.model.RecipientAccount;

public interface PaymentPort {
    PaymentResponse createPayment(Long userId, BigDecimal amount, RecipientAccount recipientAccount);
}