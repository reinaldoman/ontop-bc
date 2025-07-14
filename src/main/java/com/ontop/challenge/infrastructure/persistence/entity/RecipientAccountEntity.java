package com.ontop.challenge.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipient_accounts")
public class RecipientAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accountId;
    private String firstName;
    private String lastName;
    private String routingNumber;
    private String nationalIdNumber;
    private String accountNumber;
    private String bankName;
    private Long userId;
}