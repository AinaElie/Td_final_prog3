package school.hei.examen_prog3.dao.operations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;
import school.hei.examen_prog3.dao.mapper.BestProcessingTimeElementMapper;
import school.hei.examen_prog3.model.BestProcessingTimeElement;
import school.hei.examen_prog3.model.DurationUnit;

import java.sql.*;
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

    public void create(BestProcessingTimeElement element, Long idProcessingTime) {
        String sql = "INSERT INTO time_element (sales_point, dish_name, duration, duration_unit, id_processing_time) " +
                "VALUES (?, ?, ?, ?::duration_unit, ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, element.getSalesPoint());
            statement.setString(2, element.getDish());
            statement.setDouble(3, element.getPreparationDuration());
            statement.setString(4, element.getDurationUnit().name());
            statement.setLong(5, idProcessingTime);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating time_element failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create BestProcessingTimeElement: " + e.getMessage(), e);
        }
    }

    public List<BestProcessingTimeElement> findByIdProcessingTime(Long id) {
        String sql = "SELECT * FROM time_element WHERE id_processing_time = ?";
        List<BestProcessingTimeElement> elements = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                elements.add(new BestProcessingTimeElement(
                        rs.getLong("id_time_element"),
                        rs.getString("sales_point"),
                        rs.getString("dish_name"),
                        rs.getDouble("duration"),
                        DurationUnit.valueOf(rs.getString("duration_unit"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find elements by processing time id", e);
        }
        return elements;
    }
}
