package org.example.examenfinalprog2ia.controller;


import lombok.RequiredArgsConstructor;
import org.example.examenfinalprog2ia.model.Transaction;
import org.example.examenfinalprog2ia.service.AccountService;
import org.example.examenfinalprog2ia.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    // GET /accounts/{id}/transactions
    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable("id") String id) {
        List<Transaction> transactions = transactionService.findByAccountId(id);
        return ResponseEntity.ok(transactions);
    }

    // GET /account/{id}/balance
    @GetMapping("/account/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable("id") String id) {
        BigDecimal balance = accountService.calculateBalance(id);
        return ResponseEntity.ok(balance);
    }
}
