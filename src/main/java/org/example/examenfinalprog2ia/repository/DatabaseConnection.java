package org.example.examenfinalprog2ia.repository;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    public Connection getConnection() {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bank_transaction_db",
                    "postgres",
                    "");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error during database connection: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}