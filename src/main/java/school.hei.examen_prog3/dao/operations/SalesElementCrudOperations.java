package school.hei.examen_prog3.dao.operations;

import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;
import school.hei.examen_prog3.dao.mapper.SalesElementMapper;
import school.hei.examen_prog3.model.SalesElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesElementCrudOperations {
    private final DatabaseConnection databaseConnection;
    private final SalesElementMapper salesElementMapper;
    public SalesElementCrudOperations(DatabaseConnection databaseConnection, SalesElementMapper salesElementMapper) {
        this.databaseConnection = databaseConnection;
        this.salesElementMapper = salesElementMapper;
    }

    public SalesElement findById (Long id) {
        String sql = "select id_sales_element, sales_point from sales_element where id_sales_element = ?";
        SalesElement salesElement = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    salesElement = salesElementMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salesElement;
    }

    public List<SalesElement> findAllByIdBestSales (Long id) {
        String sql = "select id_sales_element, sales_point from sales_element where id_best_sales = ?";
        List<SalesElement> salesElements = new ArrayList<>();
        SalesElement salesElement;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    salesElement = salesElementMapper.apply(resultSet);
                    salesElements.add(salesElement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salesElements;
    }

    public Long create (SalesElement salesElement, Long idBestSales) {
        String sql = "insert into sales_element (sales_point, id_best_sales) values (?,?) on conflict do nothing returning id_sales_element";
        Long id = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, salesElement.getSalesPoint());
            statement.setDouble(2, idBestSales);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getLong("id_sales_element");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    public void saveAll(List<SalesElement> salesElementList, Long idBestSales) {
        for (SalesElement salesElement : salesElementList) {
            this.create(salesElement, idBestSales);
        }
    }
}
