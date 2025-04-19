package school.hei.examen_prog3.dao.operations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;
import school.hei.examen_prog3.dao.mapper.BestProcessingTimeElementMapper;
import school.hei.examen_prog3.model.BestProcessingTimeElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BestProcessingTimeElementCrud {
    private final DatabaseConnection databaseConnection;
    private final BestProcessingTimeElementMapper bestProcessingTimeElementMapper;
    public BestProcessingTimeElementCrud(DatabaseConnection databaseConnection, BestProcessingTimeElementMapper bestProcessingTimeElementMapper) {
        this.databaseConnection = databaseConnection;
        this.bestProcessingTimeElementMapper = bestProcessingTimeElementMapper;
    }

    public List<BestProcessingTimeElement> findByIdProcessingTime(Long id) {
        List<BestProcessingTimeElement> bestProcessingTimeElements = new ArrayList<>();
        String sql = "select id_time_element, sales_point, dish_name, duration, duration_unit from time_element where id_processing_time = ?";
        BestProcessingTimeElement bestProcessingTimeElement;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bestProcessingTimeElement = bestProcessingTimeElementMapper.apply(resultSet);
                    bestProcessingTimeElements.add(bestProcessingTimeElement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bestProcessingTimeElements;
    }

    public void create (BestProcessingTimeElement bestProcessingTimeElement, Long idProcessingTime) {
        String sql = "insert into time_element values (?,?,?,?,?) on conflict do nothing ";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bestProcessingTimeElement.getSalesPoint());
            statement.setString(2, bestProcessingTimeElement.getDish());
            statement.setDouble(3, bestProcessingTimeElement.getPreparationDuration());
            statement.setString(4, bestProcessingTimeElement.getDurationUnit().toString());
            statement.setLong(5, idProcessingTime);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
