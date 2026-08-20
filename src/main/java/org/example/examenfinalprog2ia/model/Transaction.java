package org.example.examenfinalprog2ia.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Transaction (
        String id,
        Instant createdAT,
        TransactionType transactionType,
        BigDecimal amount,
        String reason
){}
