package school.hei.prog3.endpoint.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.prog3.endpoint.rest.DishIngredientRest;
import school.hei.prog3.model.DishIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishIngredientRestMapper implements Function<DishIngredient, DishIngredientRest> {
    private final IngredientBasicMapper ingredientMapper;
    private final StockMovementRestMapper stockMovementRestMapper;

    @Override
    public DishIngredientRest apply(DishIngredient dishIngredient) {
        return new DishIngredientRest(
                ingredientMapper.apply(dishIngredient.getIngredient()),
                dishIngredient.getRequiredQuantity(),
                dishIngredient.getUnit(),
                dishIngredient.getIngredient().getActualPrice(),
                stockMovementRestMapper.apply(dishIngredient.getIngredient().getStockMovement().getLast())
        );
    }

    public List<DishIngredientRest> applyAll(List<DishIngredient> dishIngredients) {
        List<DishIngredientRest> dishIngredientRestList = new ArrayList<>();
        for (DishIngredient dishIngredient : dishIngredients) {
            dishIngredientRestList.add(apply(dishIngredient));
        }
        return dishIngredientRestList;
    }
}
