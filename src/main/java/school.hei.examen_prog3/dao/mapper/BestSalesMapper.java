package school.hei.examen_prog3.dao.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.dao.operations.SalesElementCrudOperations;
import school.hei.examen_prog3.model.BestSales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Component
public class BestSalesMapper implements Function<ResultSet, BestSales> {
    private final SalesElementCrudOperations salesElementCrudOperations;
    public BestSalesMapper(SalesElementCrudOperations salesElementCrudOperations) {
        this.salesElementCrudOperations = salesElementCrudOperations;
    }

    @SneakyThrows
    @Override
    public BestSales apply(ResultSet resultSet) {
        try{
            return new BestSales(
                    resultSet.getLong("id_best_sales"),
                    resultSet.getTimestamp("update_at").toInstant(),
                    salesElementCrudOperations.findAllByIdBestSales(resultSet.getLong("id_best_sales"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
