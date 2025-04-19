package school.hei.prog3.endpoint.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.prog3.endpoint.rest.IngredientRest;
import school.hei.prog3.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class IngredientRestMapper implements Function<Ingredient, IngredientRest> {
    private final StockMovementRestMapper stockMovementRestMapper;

    @Override
    public IngredientRest apply(Ingredient ingredient) {
        return new IngredientRest(
                ingredient.getIdIngredient(),
                ingredient.getIngredientName(),
                ingredient.getAvailableQuantity(),
                ingredient.getActualPrice(),
                ingredient.getPriceList(),
                stockMovementRestMapper.applyAll(ingredient.getStockMovement())
        );
    }

    public List<IngredientRest> applyAll(List<Ingredient> ingredients) {
        List<IngredientRest> ingredientRests = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientRests.add(apply(ingredient));
        }
        return ingredientRests;
    }
}
