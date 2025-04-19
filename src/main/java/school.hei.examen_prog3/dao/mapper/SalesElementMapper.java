package school.hei.examen_prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.dao.operations.DishSoldCrudOperations;
import school.hei.examen_prog3.model.SalesElement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Component
public class SalesElementMapper implements Function<ResultSet, SalesElement> {
    private final DishSoldCrudOperations dishSoldCrudOperations;
    public SalesElementMapper(DishSoldCrudOperations dishSoldCrudOperations) {
        this.dishSoldCrudOperations = dishSoldCrudOperations;
    }

    @SneakyThrows
    @Override
    public SalesElement apply(ResultSet resultSet) {
        try{
            return new SalesElement(
                    resultSet.getLong("id_sales_element"),
                    resultSet.getString("sales_point"),
                    dishSoldCrudOperations.findAllByIdSalesElement(resultSet.getLong("id_sales_element"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
