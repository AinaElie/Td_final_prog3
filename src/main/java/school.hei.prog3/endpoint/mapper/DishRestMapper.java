package school.hei.prog3.endpoint.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.prog3.endpoint.rest.DishRest;
import school.hei.prog3.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishRestMapper implements Function<Dish, DishRest> {
    private final DishIngredientRestMapper ingredientRestMapper;

    @Override
    public DishRest apply(Dish dish) {
        return new DishRest(
                dish.getIdDish(),
                dish.getDishName(),
                dish.getAvalaibleQuantity(),
                dish.getUnitPrice(),
                ingredientRestMapper.applyAll(dish.getIngredients())
        );
    }

    public List<DishRest> applyAll(List<Dish> dishes) {
        List<DishRest> dishRests = new ArrayList<>();
        for (Dish dish : dishes) {
            dishRests.add(apply(dish));
        }
        return dishRests;
    }
}
