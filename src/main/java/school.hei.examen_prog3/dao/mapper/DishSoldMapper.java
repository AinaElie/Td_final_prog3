package school.hei.examen_prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.model.DishSold;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishSoldMapper implements Function<ResultSet, DishSold> {

    @SneakyThrows
    @Override
    public DishSold apply(ResultSet resultSet) {
        try{
            return new DishSold(
                    resultSet.getLong("id_dish_sold"),
                    resultSet.getString("dish_name"),
                    resultSet.getDouble("quantity"),
                    resultSet.getDouble("total_amount")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
