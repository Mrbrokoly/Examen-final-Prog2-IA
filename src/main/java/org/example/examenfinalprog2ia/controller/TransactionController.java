package org.example.examenfinalprog2ia.controller;

import lombok.RequiredArgsConstructor;
import org.example.examenfinalprog2ia.model.Transaction;
import org.example.examenfinalprog2ia.model.TransactionType;
import org.example.examenfinalprog2ia.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(
            @RequestParam(value = "type", required = false) TransactionType type) {

        List<Transaction> transactions = transactionService.findAll(type);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transaction")
    public ResponseEntity<List<Transaction>> createTransactions(
            @RequestBody List<Transaction> transactions) {

        List<Transaction> created = transactionService.saveAll(transactions);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}