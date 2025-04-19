package school.hei.prog3.dao.operations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.model.Ingredient;
import school.hei.prog3.model.Price;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Repository
@RequiredArgsConstructor
public class PriceCrudOperations implements CrudOperations<Price> {
    private final DatabaseConnection databaseConnection;

    @Override
    public List<Price> getAll(int page, int size) {

        return List.of();
    }

    public List<Price> getListPriceByIdIngredient (String idIngredient) {
        String sql = "select id_unit_price, price, update_datetime from unit_price where id_ingredient = ?";
        Price price;
        List<Price> prices = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, idIngredient);

            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    price = new Price(
                            resultSet.getString("id_unit_price"),
                            resultSet.getDouble("price"),
                            resultSet.getTimestamp("update_datetime").toLocalDateTime()
                    );
                    prices.add(price);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prices;
    }

    public Price findById(String idIngredient, String idPrice) {
        String sql = "select price, update_datetime from unit_price where id_ingredient = ? and id_unit_price = ?";
        Price price = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, idIngredient);
            statement.setString(2, idPrice);

            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    price = new Price(
                            idPrice,
                            resultSet.getDouble("price"),
                            resultSet.getTimestamp("update_datetime").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return price;
    }

    public Price createPrice (Price price, String idIngredient) {
        String sql = "insert into unit_price values (?,?,?,?) on conflict do nothing";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, price.getIdPrice());
            statement.setString(2, idIngredient);
            statement.setDouble(3, price.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(price.getUpdateDateTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.findById(idIngredient, price.getIdPrice());
    }

    public List<Price> saveAll(List<Price> prices, String idIngredient) {
        Price price;
        List<Price> newPrices = new ArrayList<>();

        for (Price element : prices) {
            price = this.findById(element.getIdPrice(), idIngredient);
            if (price == null) {
                Price priceInsert = this.createPrice(element, idIngredient);
                newPrices.add(priceInsert);
            }
        }
        return newPrices;
    }
}