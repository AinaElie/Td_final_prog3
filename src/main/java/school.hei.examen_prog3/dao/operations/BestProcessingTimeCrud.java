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
    private final BestProcessingTimeElementCrud elementCrud;

    public BestProcessingTimeCrud(DatabaseConnection databaseConnection,
                                  BestProcessingTimeMapper bestProcessingTimeMapper, BestProcessingTimeElementCrud elementCrud) {
        this.databaseConnection = databaseConnection;
        this.bestProcessingTimeMapper = bestProcessingTimeMapper;
        this.elementCrud = elementCrud;
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

    public Optional<BestProcessingTime> findLatest() {
        String sql = "SELECT p.id_processing_time, p.update_at FROM processing_time p ORDER BY p.update_at DESC LIMIT 1";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id_processing_time");
                return Optional.of(new BestProcessingTime(
                        id,
                        rs.getTimestamp("update_at").toInstant(),
                        elementCrud.findByIdProcessingTime(id)
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find latest processing time", e);
        }
    }

    public List<BestProcessingTime> findAll() {
        String sql = "SELECT p.id_processing_time, p.update_at FROM processing_time p ORDER BY p.update_at DESC";
        List<BestProcessingTime> times = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id_processing_time");
                times.add(new BestProcessingTime(
                        id,
                        rs.getTimestamp("update_at").toInstant(),
                        elementCrud.findByIdProcessingTime(id)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all processing times", e);
        }
        return times;
    }

    public BestProcessingTime create(BestProcessingTime bestProcessingTime) {
        String sql = "INSERT INTO processing_time (update_at) VALUES (?) RETURNING id_processing_time";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, Timestamp.from(bestProcessingTime.getUpdateAt()));
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                bestProcessingTime.setId(rs.getLong("id_processing_time"));
                return bestProcessingTime;
            }

            throw new SQLException("Creating processing_time failed, no ID obtained.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create BestProcessingTime", e);
        }
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