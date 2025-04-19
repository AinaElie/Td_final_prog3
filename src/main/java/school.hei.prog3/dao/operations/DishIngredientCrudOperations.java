package school.hei.prog3.dao.operations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.dao.mapper.DishIngredientMapper;
import school.hei.prog3.endpoint.rest.DishIngredientRest;
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
public class DishIngredientCrudOperations {
    private final DatabaseConnection databaseConnection;
    private final DishIngredientMapper dishIngredientMapper;

    public List<DishIngredient> getDishIngredientsByIdDish (String idDish) {
        List<DishIngredient> dishIngredients = new ArrayList<>();

        String sql = "select id_ingredient, required_quantity, unit from dish_ingredient where id_dish = ?";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idDish);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DishIngredient dishIngredient = dishIngredientMapper.apply(resultSet);
                    dishIngredients.add(dishIngredient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishIngredients;
    }

    public List<DishIngredient> createDishIngredient (List<DishIngredientRest> dishIngredients, String idDish) {
        String insert = "insert into dish_ingredient values (?,?,?,?::unit) on conflict do nothing";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(insert)) {
            for (DishIngredientRest ingredient : dishIngredients) {
                statement.setString(1, idDish);
                statement.setString(2, ingredient.getIngredient().getIdIngredient());
                statement.setDouble(3, ingredient.getRequiredQuantity());
                statement.setString(4, ingredient.getUnit().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.getDishIngredientsByIdDish(idDish);
    }
}
