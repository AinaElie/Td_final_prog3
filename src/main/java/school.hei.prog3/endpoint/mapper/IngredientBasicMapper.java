package school.hei.prog3.endpoint.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.prog3.endpoint.rest.IngredientBasic;
import school.hei.prog3.model.Ingredient;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class IngredientBasicMapper implements Function<Ingredient, IngredientBasic> {

    @Override
    public IngredientBasic apply(Ingredient ingredient) {
        return new IngredientBasic(
                ingredient.getIdIngredient(),
                ingredient.getIngredientName()
        );
    }
}
