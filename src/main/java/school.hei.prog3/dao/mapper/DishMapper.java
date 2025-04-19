package school.hei.prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.prog3.dao.operations.DishIngredientCrudOperations;
import school.hei.prog3.model.Dish;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishMapper implements Function<ResultSet,Dish> {
    private final DishIngredientCrudOperations crudDishIngredientOperations;

    @SneakyThrows
    @Override
    public Dish apply(ResultSet resultSet) {
        return new Dish(
                resultSet.getString("id_dish"),
                resultSet.getString("name"),
                resultSet.getDouble("unit_price"),
                crudDishIngredientOperations.getDishIngredientsByIdDish(resultSet.getString("id_dish"))
        );
    }
}
