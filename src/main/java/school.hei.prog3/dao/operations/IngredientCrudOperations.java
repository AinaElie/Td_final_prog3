package school.hei.prog3.dao.operations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.dao.mapper.IngredientMapper;
import school.hei.prog3.model.Criteria;
import school.hei.prog3.model.Ingredient;
import school.hei.prog3.model.UnitMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString

@Repository
@RequiredArgsConstructor
public class IngredientCrudOperations implements CrudOperations<Ingredient> {
    private final DatabaseConnection databaseConnection;
    private final IngredientMapper ingredientMapper;

    @Override
    public List<Ingredient> getAll(int page, int size) {
        String sql = "select id_ingredient, name, unit from ingredient limit ? offset ?";
        Ingredient ingredient;
        List<Ingredient> ingredients = new ArrayList<>();
        int offset = size * (page - 1);

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, size);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ingredient = ingredientMapper.apply(resultSet);
                    ingredients.add(ingredient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public List<Ingredient> getAll() {
        String sql = "select id_ingredient, name, unit from ingredient";
        Ingredient ingredient;
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ingredient = ingredientMapper.apply(resultSet);
                    ingredients.add(ingredient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public Ingredient findById (String idIngredient) {
        String sql = "select id_ingredient, name, unit from ingredient where id_ingredient = ?";
        Ingredient ingredient = null;

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idIngredient);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ingredient = ingredientMapper.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredient;
    }

    public Ingredient createIngredient (Ingredient ingredient) {
        String sql = "insert into ingredient values (?,?,?::unit) on conflict do nothing";

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ingredient.getIdIngredient());
            statement.setString(2, ingredient.getIngredientName());
            statement.setString(3, ingredient.getUnit().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.findById(ingredient.getIdIngredient());
    }

    public List<Ingredient> saveAll(List<Ingredient> ingredients) {
        Ingredient ingredient;
        List<Ingredient> newIngredients = new ArrayList<>();

        for (Ingredient element : ingredients) {
            ingredient = this.findById(element.getIdIngredient());

            if (ingredient == null) {
                Ingredient ingredientInsert = this.createIngredient(element);
                newIngredients.add(ingredientInsert);
            } else {
                return null;
            }
        }

        return newIngredients;
    }

    public List<Ingredient> findByCriteria (List<Criteria> criteria, String column, int page, int size) {
        StringBuilder sql = new StringBuilder("select i.id_ingredient, i.name, i.unit from ingredient i inner join unit_price up on i.id_ingredient = up.id_ingredient where 1=1");
        int offset = size * (page - 1);
        Ingredient ingredient;
        List<Ingredient> ingredients = new ArrayList<>();

        for (Criteria c : criteria) {
            if ("name".equals(c.getNameColumn())) {
                sql.append(" ").append(c.getOperator().toString()).append(" i.").append(c.getNameColumn()).append(" ilike '%").append(c.getValue().toString()).append("%'");
            } else if ("unit".equals(c.getNameColumn())) {
                sql.append(" ").append(c.getOperator().toString()).append(" i.").append(c.getNameColumn()).append(" = '").append(c.getValue().toString()).append("'");
            } else if ("price".equals(c.getNameColumn()) || "update_datetime".equals(c.getNameColumn())) {
                sql.append(" ").append(c.getOperator1().toString()).append(" up.").append(c.getNameColumn()).append(" >= '").append(c.getValueMin().toString()).append("' ").append(c.getOperator2().toString()).append(" up.").append(c.getNameColumn()).append(" <= '").append(c.getValueMax().toString()).append("'");
            }
        }
        sql.append(" order by i.").append(column).append(" limit ").append(size).append(" offset ").append(offset);

        try (Connection connection = databaseConnection.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql.toString())) {
            while (resultSet.next()) {
                ingredient = ingredientMapper.apply(resultSet);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredients;
    }

    public List<Ingredient> findByPrice(int priceMinFilter, int priceMaxFilter) {
        String sql = "select id_ingredient from unit_price where 1=1";
        List<Ingredient> ingredients = new ArrayList<>();

        if (priceMinFilter == 0 & priceMaxFilter == 0) {
            return this.getAll();
        } else if (priceMinFilter == 0) {
            sql += " and price <= '" + priceMaxFilter + "'";
            try (Connection connection = databaseConnection.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    if (!ingredients.contains(this.findById(resultSet.getString("id_ingredient")))) {
                        ingredients.add(this.findById(resultSet.getString("id_ingredient")));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (priceMaxFilter == 0) {
            sql += " and price >= '" + priceMinFilter + "'";
            try (Connection connection = databaseConnection.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    if (!ingredients.contains(this.findById(resultSet.getString("id_ingredient")))) {
                        ingredients.add(this.findById(resultSet.getString("id_ingredient")));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (priceMinFilter != 0 & priceMinFilter != 0){
            sql += " and price between '" + priceMinFilter + "' and '" + priceMaxFilter + "'";
            try (Connection connection = databaseConnection.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    if (!ingredients.contains(this.findById(resultSet.getString("id_ingredient")))) {
                        ingredients.add(this.findById(resultSet.getString("id_ingredient")));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return ingredients;
    }
}