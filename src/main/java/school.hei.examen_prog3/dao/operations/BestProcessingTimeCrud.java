package school.hei.examen_prog3.dao.operations;

import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;
import school.hei.examen_prog3.dao.mapper.BestProcessingTimeMapper;
import school.hei.examen_prog3.model.BestProcessingTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BestProcessingTimeCrud {
    private final DatabaseConnection databaseConnection;
    private final BestProcessingTimeMapper bestProcessingTimeMapper;

    public BestProcessingTimeCrud(DatabaseConnection databaseConnection, BestProcessingTimeMapper bestProcessingTimeMapper) {
        this.databaseConnection = databaseConnection;
        this.bestProcessingTimeMapper = bestProcessingTimeMapper;
    }

    public BestProcessingTime findById (Long id) {
        BestProcessingTime bestProcessingTime = null;
        String sql = "select id_processing_time, update_at from processing_time where id_processing_time = ?";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bestProcessingTime = bestProcessingTimeMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bestProcessingTime;
    }

    public List<BestProcessingTime> findAll() {
        String sql = "select id_processing_time, update_at from processing_time";
        List<BestProcessingTime> bestProcessingTimes = new ArrayList<>();
        BestProcessingTime bestProcessingTime;

        try (Connection connection = databaseConnection.getConnection(); Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    bestProcessingTime = bestProcessingTimeMapper.apply(resultSet);
                    bestProcessingTimes.add(bestProcessingTime);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bestProcessingTimes;
    }

    public BestProcessingTime create (BestProcessingTime bestProcessingTime) {
        String sql = "insert into processing_time (update_at) values (?)";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.from(bestProcessingTime.getUpdateAt()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bestProcessingTime;
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
