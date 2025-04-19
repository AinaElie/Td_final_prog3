package school.hei.prog3.dao.operations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.model.StatusMapper;
import school.hei.prog3.model.StatusOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Repository
@RequiredArgsConstructor
public class StatusOrderCrudOperations implements CrudOperations<StatusOrder> {
    private final DatabaseConnection databaseConnection;

    @Override
    public List<StatusOrder> getAll(int page, int size) {
        return List.of();
    }

    public List<StatusOrder> getListStatusByIdOrder(String idOrder) {
        String sql = "select id_order, status, date_status from status_order where id_order = ?";
        List<StatusOrder> statusOrders = new ArrayList<>();
        StatusOrder statusOrder = null;
        StatusMapper statusMapper = new StatusMapper();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idOrder);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    statusOrder = new StatusOrder(
                            statusMapper.mapFromResult(resultSet.getString("status")),
                            resultSet.getTimestamp("date_status").toLocalDateTime()
                    );
                    statusOrders.add(statusOrder);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return statusOrders;
    }

    public List<StatusOrder> createStatus (String idOrder, StatusOrder status) {
        String sql = "insert into status_order values (?,?::status,?)";

        List<StatusOrder> statusOrders = this.getListStatusByIdOrder(idOrder).stream().filter(element -> element.getStatus().equals(status.getStatus())).toList();

        if (statusOrders.isEmpty()) {
            try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, idOrder);
                statement.setString(2, status.getStatus().toString());
                statement.setTimestamp(3, Timestamp.valueOf(status.getDateStatus()));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Status already exists");
        }

        return this.getListStatusByIdOrder(idOrder);
    }

    public List<StatusOrder> createStatus (String idOrder) {
        String sql = "insert into status_order values (?)";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idOrder);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.getListStatusByIdOrder(idOrder);
    }
}
