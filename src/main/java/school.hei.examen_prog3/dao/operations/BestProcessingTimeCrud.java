package school.hei.examen_prog3.dao.operations;

import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;
import school.hei.examen_prog3.dao.mapper.BestProcessingTimeMapper;
import school.hei.examen_prog3.model.BestProcessingTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BestProcessingTimeCrud {
    private final DatabaseConnection databaseConnection;
    private final BestProcessingTimeMapper bestProcessingTimeMapper;

    public BestProcessingTimeCrud(DatabaseConnection databaseConnection,
                                  BestProcessingTimeMapper bestProcessingTimeMapper) {
        this.databaseConnection = databaseConnection;
        this.bestProcessingTimeMapper = bestProcessingTimeMapper;
    }

    public Optional<BestProcessingTime> findById(Long id) {
        String sql = "SELECT id_processing_time, update_at FROM processing_time WHERE id_processing_time = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(bestProcessingTimeMapper.apply(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find BestProcessingTime by id: " + id, e);
        }
        return Optional.empty();
    }

    public List<BestProcessingTime> findAll() {
        String sql = "SELECT id_processing_time, update_at FROM processing_time ORDER BY update_at DESC";
        List<BestProcessingTime> bestProcessingTimes = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                bestProcessingTimes.add(bestProcessingTimeMapper.apply(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all BestProcessingTimes", e);
        }
        return bestProcessingTimes;
    }

    public BestProcessingTime create(BestProcessingTime bestProcessingTime) {
        String sql = "INSERT INTO processing_time (update_at) VALUES (?) RETURNING id_processing_time";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, Timestamp.from(bestProcessingTime.getUpdateAt()));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id_processing_time");
                    return new BestProcessingTime(
                            id,
                            bestProcessingTime.getUpdateAt(),
                            bestProcessingTime.getBestProcessingTimes()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create BestProcessingTime", e);
        }
        throw new RuntimeException("Failed to create BestProcessingTime - no ID returned");
    }

    public void deleteAll() {
        String sql = "DELETE FROM processing_time";

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete all processing times", e);
        }
    }
}