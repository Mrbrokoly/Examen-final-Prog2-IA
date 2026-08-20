package org.example.examenfinalprog2ia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class Transaction {
    private String id;
    private Instant createdAt;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String reason;
    private String accountId;
}