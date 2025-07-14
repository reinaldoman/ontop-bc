package com.ontop.challenge.infrastructure.persistence;

import com.ontop.challenge.domain.model.RecipientAccount;
import com.ontop.challenge.infrastructure.persistence.entity.RecipientAccountEntity;

public class RecipientAccountMapper {

    public static RecipientAccount toDomain(RecipientAccountEntity entity) {
        RecipientAccount account = new RecipientAccount();
        account.setAccountId(entity.getAccountId());
        account.setFirstName(entity.getFirstName());
        account.setLastName(entity.getLastName());
        account.setRoutingNumber(entity.getRoutingNumber());
        account.setNationalIdNumber(entity.getNationalIdNumber());
        account.setAccountNumber(entity.getAccountNumber());
        account.setBankName(entity.getBankName());
        account.setUserId(entity.getUserId());
        return account;
    }

    public static RecipientAccountEntity toEntity(RecipientAccount domain) {
        return RecipientAccountEntity.builder()
                .accountId(domain.getAccountId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .routingNumber(domain.getRoutingNumber())
                .nationalIdNumber(domain.getNationalIdNumber())
                .accountNumber(domain.getAccountNumber())
                .bankName(domain.getBankName())
                .userId(domain.getUserId())
                .build();
    }
}