package school.hei.prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.prog3.dao.operations.DishCrudOperations;
import school.hei.prog3.dao.operations.StatusDishOrderCrudOperations;
import school.hei.prog3.model.DishOrder;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishOrderMapper implements Function<ResultSet, DishOrder> {
    private final DishCrudOperations dishCrudOperations;
    private final StatusDishOrderCrudOperations status;

    @SneakyThrows
    @Override
    public DishOrder apply(ResultSet resultSet) {
        return new DishOrder(
                resultSet.getString("id_dish_order"),
                dishCrudOperations.getDishWithIngredient(resultSet.getString("id_dish")),
                resultSet.getDouble("quantity"),
                status.getListStatusByIdDishOrder(resultSet.getString("id_dish_order"))
        );
    }
}
