package school.hei.prog3.dao.operations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.dao.mapper.StockMovementMapper;
import school.hei.prog3.endpoint.rest.StockMovementRest;
import school.hei.prog3.model.Ingredient;
import school.hei.prog3.model.StockMovement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Repository
@RequiredArgsConstructor
public class StockCrudOperations {
    private final DatabaseConnection databaseConnection;
    private final StockMovementMapper stockMovementMapper;

    public List<StockMovement> createStockMovement (StockMovement stockMovement) {
        String sql = "insert into stock_movement values (?,?,?,?::unit,?,?) on conflict do nothing";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, stockMovement.getIdStock());
            statement.setString(2, stockMovement.getIngredient().getIdIngredient());
            statement.setDouble(3, stockMovement.getRequiredQuantity());
            statement.setString(4, stockMovement.getUnit().toString());
            statement.setString(5, stockMovement.getTypeMovement().toString());
            statement.setTimestamp(6, Timestamp.valueOf(stockMovement.getDateMovement()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.findStockMovementIngredientById(stockMovement.getIngredient().getIdIngredient());
    }

    public List<StockMovement> findStockMovementIngredientById (String idIngredient) {
        String sql = "select id_stock, id_ingredient, quantity, unit, movement, date_movement from stock_movement where id_ingredient = ?";
        List<StockMovement> stocks = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, idIngredient);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    StockMovement stockMovement = stockMovementMapper.apply(resultSet);
                    stocks.add(stockMovement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stocks;
    }

    public List<StockMovement> saveAll(List<StockMovementRest> stockMovements, String idIngredient) {
        for (StockMovementRest stockMovement : stockMovements) {
            String sql = "insert into stock_movement values (?,?,?,?::unit,?,?) on conflict do nothing";

            try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setString(1, stockMovement.getIdStock());
                statement.setString(2, idIngredient);
                statement.setDouble(3, stockMovement.getRequiredQuantity());
                statement.setString(4, stockMovement.getUnit().toString());
                statement.setString(5, stockMovement.getTypeMovement().toString());
                statement.setTimestamp(6, Timestamp.valueOf(stockMovement.getDateMovement()));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return this.findStockMovementIngredientById(idIngredient);
    }
}