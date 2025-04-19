package school.hei.prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.prog3.dao.operations.IngredientCrudOperations;
import school.hei.prog3.model.DishIngredient;
import school.hei.prog3.model.UnitMapper;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishIngredientMapper implements Function<ResultSet, DishIngredient> {
    private final UnitMapper unitMapper;
    private final IngredientCrudOperations ingredientCrudOperations;

    @SneakyThrows
    @Override
    public DishIngredient apply(ResultSet resultSet) {
        return new DishIngredient(
                ingredientCrudOperations.findById(resultSet.getString("id_ingredient")),
                resultSet.getDouble("required_quantity"),
                unitMapper.mapFromResult(resultSet.getString("unit"))
        );
    }
}
