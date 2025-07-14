package com.ontop.challenge.application.ports.out;

import java.math.BigDecimal;

public interface BalancePort {
    BigDecimal getBalance(Long userId);
}