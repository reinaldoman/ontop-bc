package com.ontop.challenge.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ontop.challenge.infrastructure.persistence.entity.TransactionEntity;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {
}