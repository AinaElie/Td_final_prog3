package school.hei.prog3.endpoint.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class IngredientBasic {
    private String idIngredient;
    private String ingredientName;

    public IngredientBasic(String idIngredient, String ingredientName) {
        this.idIngredient = idIngredient;
        this.ingredientName = ingredientName;
    }
}
