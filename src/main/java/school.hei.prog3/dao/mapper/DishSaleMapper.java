package school.hei.prog3.dao.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.prog3.model.DishSale;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
public class DishSaleMapper implements Function<ResultSet, DishSale> {

    @SneakyThrows
    @Override
    public DishSale apply(ResultSet resultSet) {
        return new DishSale(
                resultSet.getString("name"),
                resultSet.getDouble("total_quantity"),
                resultSet.getDouble("total_amount")
        );
    }
}
