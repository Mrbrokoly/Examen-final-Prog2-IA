package org.example.examenfinalprog2ia.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.examenfinalprog2ia.model.AccountType;

@Getter
@AllArgsConstructor
public class Account {
    private String id;
    private AccountType accountType;
}