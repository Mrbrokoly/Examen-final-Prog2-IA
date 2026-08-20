package org.example.examenfinalprog2ia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Account {
    private String id;
    private AccountType accountType;
}