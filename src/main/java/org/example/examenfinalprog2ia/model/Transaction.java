package org.example.examenfinalprog2ia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String id;
    private Instant createdAt;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String reason;
    private String accountId;
}