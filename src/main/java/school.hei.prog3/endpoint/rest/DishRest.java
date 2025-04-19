package school.hei.prog3.endpoint.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DishRest {
    private String idDish;
    private String nameDish;
    private double availableQuantity;
    private double actualPrice;
    private List<DishIngredientRest> ingredients;

    public DishRest(String idDish, String nameDish, double availableQuantity, double actualPrice, List<DishIngredientRest> ingredients) {
        this.idDish = idDish;
        this.nameDish = nameDish;
        this.availableQuantity = availableQuantity;
        this.actualPrice = actualPrice;
        this.ingredients = ingredients;
    }
}
