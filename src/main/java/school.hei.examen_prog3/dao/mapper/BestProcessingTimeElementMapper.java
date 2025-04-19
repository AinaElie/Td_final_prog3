package school.hei.examen_prog3.dao.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.model.BestProcessingTimeElement;
import school.hei.examen_prog3.model.DurationUnitMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Component
public class BestProcessingTimeElementMapper implements Function<ResultSet, BestProcessingTimeElement> {
    private final DurationUnitMapper durationUnitMapper;
    public BestProcessingTimeElementMapper(DurationUnitMapper durationUnitMapper) {
        this.durationUnitMapper = durationUnitMapper;
    }

    @Override
    public BestProcessingTimeElement apply(ResultSet resultSet) {
        try {
            return new BestProcessingTimeElement(
                    resultSet.getLong("id_time_element"),
                    resultSet.getString("sales_point"),
                    resultSet.getString("dish_name"),
                    resultSet.getDouble("duration"),
                    durationUnitMapper.mapFromResult(resultSet.getString("duration_unit"))
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map BestProcessingTimeElement from ResultSet", e);
        }
    }
}
