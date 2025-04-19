package school.hei.examen_prog3.dao.operations;

import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;
import school.hei.examen_prog3.model.BestProcessingTime;

import java.sql.*;

@Repository
public class BestProcessingTimeCrudOperations {
    private final DatabaseConnection databaseConnection;

    public BestProcessingTimeCrudOperations(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public BestProcessingTime create(BestProcessingTime bestProcessingTime) {
        String sql = "insert into processing_time (update_at) values (?) RETURNING id_processing_time";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.from(bestProcessingTime.getUpdateAt()));

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong("id_processing_time");
                    bestProcessingTime.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bestProcessingTime;
    }
}
