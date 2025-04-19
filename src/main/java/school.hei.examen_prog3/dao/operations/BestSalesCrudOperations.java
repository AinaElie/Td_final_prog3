package school.hei.examen_prog3.dao.operations;

import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;
import school.hei.examen_prog3.dao.mapper.BestSalesMapper;
import school.hei.examen_prog3.model.BestSales;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BestSalesCrudOperations {
    private final DatabaseConnection databaseConnection;
    private final BestSalesMapper bestSalesMapper;
    public BestSalesCrudOperations(DatabaseConnection databaseConnection, BestSalesMapper bestSalesMapper) {
        this.databaseConnection = databaseConnection;
        this.bestSalesMapper = bestSalesMapper;
    }

    public BestSales findByUpdateAt (Instant instant) {
        String sql = "select id_best_sales, update_at from best_sales where update_at = ?";
        BestSales bestSales = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.from(instant));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bestSales = bestSalesMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bestSales;
    }

    public List<BestSales> getAll() {
        String sql = "select id_best_sales, update_at from best_sales";
        List<BestSales> bestSalesList = new ArrayList<>();
        BestSales bestSales;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bestSales = bestSalesMapper.apply(resultSet);
                    bestSalesList.add(bestSales);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bestSalesList;
    }

    public BestSales create(BestSales bestSales) {
        String sql = "insert into best_sales (update_at) values (?) RETURNING id_best_sales";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.from(bestSales.getUpdateAt()));

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong("id_best_sales");
                    bestSales.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bestSales;
    }

    public void deleteAll() {
        String sql = "DELETE FROM best_sales";

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete all best sales", e);
        }
    }
}
