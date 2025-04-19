package school.hei.prog3.model;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Data
@Builder
public class DishIngredient {
    private Ingredient ingredient;
    private double requiredQuantity;
    private Unit unit;

    public DishIngredient(Ingredient ingredient, double requiredQuantity, Unit unit) {
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
    }
}
