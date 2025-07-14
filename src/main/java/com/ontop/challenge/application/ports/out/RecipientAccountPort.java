package com.ontop.challenge.application.ports.out;

import com.ontop.challenge.domain.model.RecipientAccount;

public interface RecipientAccountPort {
    RecipientAccount getRecipientAccountById(String accountId, Long userId);
}
