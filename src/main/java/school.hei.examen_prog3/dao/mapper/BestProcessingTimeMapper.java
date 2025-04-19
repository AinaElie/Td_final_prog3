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
public class BestProcessingTimeMapper implements Function<ResultSet, BestProcessingTime> {
    private final BestProcessingTimeElementCrud bestProcessingTimeElementCrud;

    public BestProcessingTimeMapper(BestProcessingTimeElementCrud bestProcessingTimeElementCrud) {
        this.bestProcessingTimeElementCrud = bestProcessingTimeElementCrud;
    }

    @SneakyThrows
    @Override
    public BestProcessingTime apply(ResultSet resultSet) {
        try{
            return new BestProcessingTime(
                    resultSet.getLong("id_processing_time"),
                    resultSet.getTimestamp("update_at").toInstant(),
                    bestProcessingTimeElementCrud.findByIdProcessingTime(resultSet.getLong("id_processing_time"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
