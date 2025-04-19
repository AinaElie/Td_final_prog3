package school.hei.prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.prog3.model.UnitMapper;
import school.hei.prog3.model.MovementMapper;
import school.hei.prog3.model.StockMovement;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class StockMovementMapper implements Function<ResultSet, StockMovement> {
    private final UnitMapper unitMapper;
    private final MovementMapper movementMapper;

    @SneakyThrows
    @Override
    public StockMovement apply(ResultSet resultSet) {
        return new StockMovement(
                resultSet.getString("id_stock"),
                resultSet.getDouble("quantity"),
                unitMapper.mapFromResult(resultSet.getString("unit")),
                resultSet.getTimestamp("date_movement").toLocalDateTime(),
                movementMapper.mapFromResult(resultSet.getString("movement"))
        );
    }
}
