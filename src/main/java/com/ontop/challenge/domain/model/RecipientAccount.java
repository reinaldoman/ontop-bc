package com.ontop.challenge.domain.model;

import lombok.Data;

@Data
public class RecipientAccount {
    private String accountId;
    private String firstName;
    private String lastName;
    private String routingNumber;
    private String nationalIdNumber;
    private String accountNumber;
    private String bankName;
    private Long userId;
}