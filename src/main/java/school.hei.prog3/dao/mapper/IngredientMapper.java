package school.hei.prog3.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.prog3.dao.operations.PriceCrudOperations;
import school.hei.prog3.dao.operations.StockCrudOperations;
import school.hei.prog3.model.Ingredient;
import school.hei.prog3.model.Price;
import school.hei.prog3.model.UnitMapper;
import school.hei.prog3.model.StockMovement;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class IngredientMapper implements Function<ResultSet, Ingredient> {
    private final PriceCrudOperations priceCrudOperations;
    private final StockCrudOperations stockMovementCrudOperations;
    private final UnitMapper unitMapper;

    @SneakyThrows
    @Override
    public Ingredient apply(ResultSet resultSet) {
        String idIngredient = resultSet.getString("id_ingredient");
        List<Price> ingredientPrices = priceCrudOperations.getListPriceByIdIngredient(idIngredient);
        List<StockMovement> ingredientStockMovements = stockMovementCrudOperations.findStockMovementIngredientById(idIngredient);

        return new Ingredient(
                idIngredient,
                resultSet.getString("name"),
                ingredientPrices,
                unitMapper.mapFromResult(resultSet.getString("unit")),
                ingredientStockMovements
        );
    }
}
