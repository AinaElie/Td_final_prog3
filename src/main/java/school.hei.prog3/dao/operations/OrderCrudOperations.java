package school.hei.prog3.dao.operations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.dao.mapper.DishSaleMapper;
import school.hei.prog3.dao.mapper.OrderMapper;
import school.hei.prog3.endpoint.rest.OrderRest;
import school.hei.prog3.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Repository
@RequiredArgsConstructor
public class OrderCrudOperations implements CrudOperations<Order> {
    private final DatabaseConnection databaseConnection;
    private final DishOrderCrudOperations dishOrderCrudOperations;
    private final OrderMapper orderMapper;
    private final DishSaleMapper dishSaleMapper;
    private final StatusOrderCrudOperations statusOrderCrudOperations;

    @Override
    public List<Order> getAll(int page, int size) {
        String sql = "select \"order\".id_order, reference from \"order\" limit ? offset ? ";
        int offset = size * (page - 1);
        List<Order> orders = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, size);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(orderMapper.apply(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    public void save(Order order, OrderRest orderRest) {
        dishOrderCrudOperations.saveAll(orderRest.getDishes(), order.getIdOrder());
        if (order.getActualStatus() == Status.CREATE || order.getActualStatus() == Status.CONFIRMED) {
            throw new RuntimeException("Don't Create status");
        }
        statusOrderCrudOperations.createStatus(order.getIdOrder());
    }

    public Order findById(String idOrder) {
        String sql = "select \"order\".id_order, reference from \"order\" where \"order\".id_order = ?";
        Order order = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idOrder);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    order = orderMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    public Order findByReference(String reference) {
        String sql = "select \"order\".id_order, reference from \"order\" where reference = ?";
        Order order = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reference);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    order = orderMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    public Order addNextStatus(String reference, String idDish) {
        Order order = this.findByReference(reference);
        List<DishOrder> dishOrder = order.getDishes();
        for (DishOrder dish : dishOrder) {
            if (dish.getIdDishOrder().equals(idDish)) {
                dish.addNextSTapeStatus();
            }
        }
        order.addNextStatus();
        return this.findById(order.getIdOrder());
    }

    public List<DishSale> getBestSale(int limit) {
        String sql = "select name, sum(quantity) as total_quantity, sum(quantity * unit_price) as total_amount , status " +
                "from dish_order inner join dish on dish.id_dish = dish_order.id_dish inner join status_order on dish_order.id_order = status_order.id_order " +
                "where status = 'FINISHED' group by dish.name, status order by total_amount desc limit ?";

        List<DishSale> dishes = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DishSale dishSale = dishSaleMapper.apply(resultSet);
                    dishes.add(dishSale);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishes;
    }
}
