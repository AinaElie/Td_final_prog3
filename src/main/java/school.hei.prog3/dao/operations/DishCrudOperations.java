package school.hei.prog3.dao.operations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.dao.mapper.DishMapper;
import school.hei.prog3.endpoint.rest.DishIngredientRest;
import school.hei.prog3.endpoint.rest.DishRest;
import school.hei.prog3.model.Dish;
import school.hei.prog3.model.DishIngredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Repository
@RequiredArgsConstructor
public class DishCrudOperations implements CrudOperations<Dish> {
    private final DatabaseConnection databaseConnection;
    private final DishMapper dishMapper;
    private final DishIngredientCrudOperations dishIngredientCrudOperations;

    @Override
    public List<Dish> getAll(int page, int size) {
        String sql = "select d.id_dish, d.name, d.unit_price from dish d limit ? offset ?";
        int offset = size * (page - 1);
        List<Dish> dishes = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, size);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Dish dish = dishMapper.apply(resultSet);
                    dishes.add(dish);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishes;
    }

    public Dish createDish(DishRest dish) {
        String insertDish = "insert into dish values (?, ?, ?) on conflict do nothing";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statementInsertDish = connection.prepareStatement(insertDish)) {
            statementInsertDish.setString(1, dish.getIdDish());
            statementInsertDish.setString(2, dish.getNameDish());
            statementInsertDish.setDouble(3, dish.getActualPrice());
            statementInsertDish.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.getDishWithIngredient(dish.getIdDish());
    }

    public Dish getDishWithIngredient(String idDish) {
        Dish dish = null;

        String sql = "select id_dish, name, unit_price from dish where id_dish = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idDish);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dish = dishMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dish;
    }

    public List<DishIngredient> saveAll(List<DishIngredientRest> dishes, String idDish) {
        return dishIngredientCrudOperations.createDishIngredient(dishes, idDish);
    }
}