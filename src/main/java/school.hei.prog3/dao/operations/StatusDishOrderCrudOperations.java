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
public class StatusDishOrderCrudOperations implements CrudOperations<StatusOrder> {
    private final DatabaseConnection databaseConnection;

    public List<StatusOrder> getListStatusByIdDishOrder(String idDishOrder) {
        String sql = "select id_dish_order, status, date_status from status_dish_order where id_dish_order = ?";
        List<StatusOrder> statusOrders = new ArrayList<>();
        StatusMapper statusMapper = new StatusMapper();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idDishOrder);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    StatusOrder statusOrder = new StatusOrder(
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

    public List<StatusOrder> createStatus (String idDishOrder, StatusOrder status) {
        String sql = "insert into status_dish_order values (?,?::status,?)";

        List<StatusOrder> statusOrders = this.getListStatusByIdDishOrder(idDishOrder).stream().filter(element -> element.getStatus().equals(status.getStatus())).toList();

        if (statusOrders.isEmpty()) {
            try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, idDishOrder);
                statement.setString(2, status.getStatus().toString());
                statement.setTimestamp(3, Timestamp.valueOf(status.getDateStatus()));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Status already exists");
        }
        return this.getListStatusByIdDishOrder(idDishOrder);
    }

    public List<StatusOrder> createStatus (String idDishOrder) {
        String sql = "insert into status_dish_order values (?)";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idDishOrder);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.getListStatusByIdDishOrder(idDishOrder);
    }

    @Override
    public List<StatusOrder> getAll(int page, int size) {
        return List.of();
    }
}
