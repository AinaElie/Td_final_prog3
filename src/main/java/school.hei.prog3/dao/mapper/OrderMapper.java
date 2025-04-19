package school.hei.prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.prog3.dao.operations.DishOrderCrudOperations;
import school.hei.prog3.dao.operations.StatusOrderCrudOperations;
import school.hei.prog3.model.Order;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OrderMapper implements Function<ResultSet, Order> {
    private final DishOrderCrudOperations dishOrderCrudOperations;
    private final StatusOrderCrudOperations statusOrderCrudOperations;

    @SneakyThrows
    @Override
    public Order apply(ResultSet resultSet) {
        return new Order(
                resultSet.getString("id_order"),
                resultSet.getString("reference"),
                dishOrderCrudOperations.findDishesById(resultSet.getString("id_order")),
                statusOrderCrudOperations.getListStatusByIdOrder(resultSet.getString("id_order"))
        );
    }
}
