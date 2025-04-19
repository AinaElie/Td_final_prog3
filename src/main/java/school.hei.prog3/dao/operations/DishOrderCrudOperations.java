package school.hei.prog3.dao.operations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.dao.mapper.DishOrderMapper;
import school.hei.prog3.endpoint.mapper.DishOrderRestMapper;
import school.hei.prog3.endpoint.rest.DishOrderRest;
import school.hei.prog3.model.DishOrder;
import school.hei.prog3.model.StatusOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Repository
@RequiredArgsConstructor
public class DishOrderCrudOperations implements CrudOperations<DishOrder> {
    private final DatabaseConnection databaseConnection;
    private final DishOrderMapper dishOrderMapper;
    private final DishOrderRestMapper dishOrderRestMapper;

    @Override
    public List<DishOrder> getAll(int page, int size) {
        List<DishOrder> dishOrders = new ArrayList<>();
        int offset = size * (page - 1);
        String sql = "select id_dish_order, id_dish, quantity from dish_order limit ? offset ?";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, size);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DishOrder dishOrder = dishOrderMapper.apply(resultSet);
                    dishOrders.add(dishOrder);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrders;
    }

    public DishOrder findById(String idDishOrder) {
        String sql = "select id_dish_order, id_dish, quantity from dish_order where id_dish_order = ?";
        DishOrder dishOrder = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idDishOrder);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dishOrder = dishOrderMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrder;
    }

    public List<DishOrder> findDishesByIdDish(String idDish) {
        String sql = "select id_dish_order, id_dish, quantity from dish_order where id_dish = ?";
        List<DishOrder> dishOrders = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idDish);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DishOrder dishOrder = dishOrderMapper.apply(resultSet);
                    dishOrders.add(dishOrder);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrders;
    }

    public List<DishOrder> findDishesById(String idOrder) {
        String sql = "select id_dish_order, id_dish, quantity from dish_order where id_order = ?";
        DishOrder dishOrder;
        List<DishOrder> dishOrders = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idOrder);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dishOrder = dishOrderMapper.apply(resultSet);
                    dishOrders.add(dishOrder);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrders;
    }

    public List<DishOrder> findByReference (String reference) {
        String sql = "select id_dish_order, id_dish, quantity from dish_order inner join \"order\" " +
                "on dish_order.id_order = \"order\".id_order where reference = ?";
        List<DishOrder> dishOrders = new ArrayList<>();
        DishOrder dishOrder;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reference);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dishOrder = dishOrderMapper.apply(resultSet);
                    dishOrders.add(dishOrder);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrders;
    }

    public DishOrder create(DishOrderRest dishOrder, String idOrder) {
        String sql = "insert into dish_order values (?,?,?,?) on conflict do nothing";
        StatusDishOrderCrudOperations crudStatus = new StatusDishOrderCrudOperations(databaseConnection);

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dishOrder.getIdDishOrder());
            statement.setString(2, dishOrder.getDishName());
            statement.setDouble(3, dishOrder.getDishQuantity());
            statement.setString(4, idOrder);
            statement.executeUpdate();

            if (!dishOrder.getStatus().isEmpty()) {
                for (StatusOrder status : dishOrder.getStatus()) {
                    crudStatus.createStatus(dishOrder.getIdDishOrder(), status);
                }
            } else {
                crudStatus.createStatus(dishOrder.getIdDishOrder());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.findById(dishOrder.getIdDishOrder());
    }

    public void saveAll(List<DishOrderRest> dishOrderRests, String idOrder) {
        dishOrderRests.forEach(dishOrderRest -> this.create(dishOrderRest, idOrder));
    }

    public DishOrder update(DishOrder dishOrder, String idOrder) {
        String sql = "update dish_order set quantity = ? where id_dish_order = ? and id_order = ?";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, dishOrder.getQuantity());
            statement.setString(2, dishOrder.getIdDishOrder());
            statement.setString(3, idOrder);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.findById(dishOrder.getIdDishOrder());
    }
}
