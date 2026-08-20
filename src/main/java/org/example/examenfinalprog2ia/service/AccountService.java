package org.example.examenfinalprog2ia.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final DataSource dataSource;

    // Calcule le solde : somme des transactions IN moins les transactions OUT
    public BigDecimal calculateBalance(String accountId) {
        String sql = "SELECT COALESCE(SUM(CASE " +
                "  WHEN transaction_type = 'IN' THEN amount " +
                "  WHEN transaction_type = 'OUT' THEN -amount " +
                "  ELSE 0 END), 0) AS balance " +
                "FROM transaction WHERE account_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("balance");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du solde pour le compte ID: " + accountId, e);
        }

        return BigDecimal.ZERO;
    }
}
