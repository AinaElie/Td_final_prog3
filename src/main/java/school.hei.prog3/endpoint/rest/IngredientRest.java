package school.hei.prog3.endpoint.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import school.hei.prog3.model.Price;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class IngredientRest {
    private String idIngredient;
    private String ingredientName;
    private double availableQuantity;
    private double actualPrice;
    private List<Price> priceList;
    private List<StockMovementRest> stockMovement;

    public IngredientRest(String idIngredient, String ingredientName, double availableQuantity, double actualPrice, List<Price> priceList, List<StockMovementRest> stockMovement) {
        this.idIngredient = idIngredient;
        this.ingredientName = ingredientName;
        this.availableQuantity = availableQuantity;
        this.actualPrice = actualPrice;
        this.priceList = priceList;
        this.stockMovement = stockMovement;
    }
}
