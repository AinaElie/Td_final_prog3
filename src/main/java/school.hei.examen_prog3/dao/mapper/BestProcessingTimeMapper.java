package school.hei.examen_prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.dao.operations.BestProcessingTimeElementCrud;
import school.hei.examen_prog3.model.BestProcessingTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BestProcessingTimeMapper implements Function<ResultSet, BestProcessingTime> {
    private final BestProcessingTimeElementCrud elementCrud;

    @Override
    @SneakyThrows
    public BestProcessingTime apply(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id_processing_time");
            return new BestProcessingTime(
                    id,
                    resultSet.getTimestamp("update_at").toInstant(),
                    elementCrud.findByIdProcessingTime(id)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping BestProcessingTime", e);
        }
    }
}