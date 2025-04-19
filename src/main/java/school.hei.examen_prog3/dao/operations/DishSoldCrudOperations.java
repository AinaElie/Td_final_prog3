package school.hei.examen_prog3.dao.operations;

import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;
import school.hei.examen_prog3.dao.mapper.DishSoldMapper;
import school.hei.examen_prog3.model.DishSold;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishSoldCrudOperations {
    private final DatabaseConnection databaseConnection;
    private final DishSoldMapper dishSoldMapper;

    public DishSoldCrudOperations(DatabaseConnection databaseConnection, DishSoldMapper dishSoldMapper) {
        this.databaseConnection = databaseConnection;
        this.dishSoldMapper = dishSoldMapper;
    }

    public DishSold findById (Long id) {
        String sql = "select id_dish_sold, dish_name, quantity, total_amount from dish_sold where id_dish_sold = ? order by total_amount desc";
        DishSold dishSold = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dishSold = dishSoldMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishSold;
    }

    public List<DishSold> findAllByIdSalesElement (Long id) {
        String sql = "select id_dish_sold, dish_name, quantity, total_amount from dish_sold where id_sales_element = ? order by total_amount desc";
        List<DishSold> dishSoldList = new ArrayList<>();
        DishSold dishSold;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dishSold = dishSoldMapper.apply(resultSet);
                    dishSoldList.add(dishSold);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishSoldList;
    }

    public void create(DishSold dishSold, Long idSalesElement) {
        String sql = "insert into dish_sold (id_dish_sold, dish_name, quantity, total_amount, id_sales_element) " +
                "values (?, ?, ?, ?, ?) ON CONFLICT (id_dish_sold) DO NOTHING";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, dishSold.getId());
            statement.setString(2, dishSold.getDish());
            statement.setDouble(3, dishSold.getQuantitySold());
            statement.setDouble(4, dishSold.getTotal_amount());
            statement.setLong(5, idSalesElement);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create DishSold: " + e.getMessage(), e);
        }
    }
}
