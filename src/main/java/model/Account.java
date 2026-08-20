package model;

import java.util.List;

public record Account (
        String id,
        AccountType accountType,
        List<Transaction> transactions
){}
