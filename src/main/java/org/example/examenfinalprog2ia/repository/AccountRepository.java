package org.example.examenfinalprog2ia.repository;

import lombok.AllArgsConstructor;
import org.example.examenfinalprog2ia.model.Account;
import org.example.examenfinalprog2ia.model.AccountType;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class AccountRepository {

    private final DatabaseConnection databaseConnection;

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account");
            while (resultSet.next()) {
                var account = Account.builder()
                        .id(resultSet.getString("id"))
                        .accountType(AccountType.valueOf(resultSet.getString("account_type").toUpperCase()))
                        .build();
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println("Error while fetching accounts: " + e.getMessage());
        }
        return null;
    }

    public Account findById(String id) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM account WHERE id = ?")
        ) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Account.builder()
                        .id(resultSet.getString("id"))
                        .accountType(AccountType.valueOf(resultSet.getString("account_type").toUpperCase()))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching account by id: " + e.getMessage());
        }
        return null;
    }

    public Account save(Account account) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO account (id, account_type) VALUES (?, ?::account_type)")
        ) {
            statement.setString(1, account.getId());
            statement.setString(2, account.getAccountType().name());
            statement.executeUpdate();
            return account;
        } catch (SQLException e) {
            System.out.println("Error while saving account: " + e.getMessage());
        }
        return null;
    }
}