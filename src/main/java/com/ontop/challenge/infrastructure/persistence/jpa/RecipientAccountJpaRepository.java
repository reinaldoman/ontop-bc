package com.ontop.challenge.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.challenge.infrastructure.persistence.entity.RecipientAccountEntity;

import java.util.Optional;

@Repository
public interface RecipientAccountJpaRepository extends JpaRepository<RecipientAccountEntity, Long> {
    Optional<RecipientAccountEntity> findByAccountIdAndUserId(String accountId, Long userId);
}