package org.example.examenfinalprog2ia.service;




import lombok.RequiredArgsConstructor;
import org.example.examenfinalprog2ia.model.Transaction;
import org.example.examenfinalprog2ia.model.TransactionType;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final DataSource dataSource;

    // Récupère toutes les transactions d'un compte via son ID
    public List<Transaction> findByAccountId(String accountId) {
        String sql = "SELECT id, created_at, transaction_type, amount, reason, account_id " +
                "FROM transaction WHERE account_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(mapResultSetToTransaction(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des transactions pour le compte ID: " + accountId, e);
        }

        return transactions;
    }

    // Récupère toutes les transactions ou filtre par type (IN / OUT) si non null
    public List<Transaction> findAll(TransactionType type) {
        StringBuilder sql = new StringBuilder(
                "SELECT id, created_at, transaction_type, amount, reason, account_id FROM transaction"
        );
        boolean hasFilter = (type != null);

        if (hasFilter) {
            sql.append(" WHERE transaction_type = ?");
        }

        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            if (hasFilter) {
                statement.setString(1, type.name());
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(mapResultSetToTransaction(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des transactions", e);
        }

        return transactions;
    }

    // Sauvegarde une ou plusieurs transactions
    public List<Transaction> saveAll(List<Transaction> transactions) {
        String sql = "INSERT INTO transaction (id, created_at, transaction_type, amount, reason, account_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            // Gestion manuelle de la transaction SQL
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (Transaction transaction : transactions) {
                    if (transaction.getId() == null) {
                        transaction.setId(UUID.randomUUID().toString());
                    }
                    if (transaction.getCreatedAt() == null) {
                        transaction.setCreatedAt(Instant.now());
                    }

                    statement.setString(1, transaction.getId());
                    statement.setTimestamp(2, Timestamp.from(transaction.getCreatedAt()));
                    statement.setString(3, transaction.getTransactionType().name());
                    statement.setBigDecimal(4, transaction.getAmount());
                    statement.setString(5, transaction.getReason());
                    statement.setString(6, transaction.getAccountId());

                    statement.addBatch();
                }

                statement.executeBatch();
                connection.commit(); // Validation
            } catch (SQLException e) {
                connection.rollback(); // Annulation en cas d'erreur
                throw new RuntimeException("Erreur lors de l'insertion des transactions", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de connexion à la base de données", e);
        }

        return transactions;
    }

    // Méthode utilitaire de mapping ResultSet -> Object
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getString("id"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            transaction.setCreatedAt(timestamp.toInstant());
        }

        String typeStr = rs.getString("transaction_type");
        if (typeStr != null) {
            transaction.setTransactionType(TransactionType.valueOf(typeStr));
        }

        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setReason(rs.getString("reason"));
        transaction.setAccountId(rs.getString("account_id"));

        return transaction;
    }
}
