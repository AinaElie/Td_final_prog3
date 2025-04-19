package school.hei.prog3.endpoint.rest;

import lombok.*;
import school.hei.prog3.model.Unit;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class DishIngredientRest {
    private IngredientBasic ingredient;
    private Double requiredQuantity;
    private Unit unit;
    private double actualPrice;
    private StockMovementRest actualStockMovement;
}
