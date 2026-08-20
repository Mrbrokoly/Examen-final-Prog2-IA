package org.example.examenfinalprog2ia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account {
    private String id;
    private AccountType accountType;
}