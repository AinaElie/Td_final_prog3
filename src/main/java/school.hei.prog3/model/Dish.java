package school.hei.prog3.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Data
@Builder
public class Dish {
    private String idDish;
    private String dishName;
    private double unitPrice;
    private List<DishIngredient> ingredients;

    public Dish(String idDish, String dishName, double unitPrice, List<DishIngredient> ingredients) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.unitPrice = unitPrice;
        this.ingredients = ingredients;
    }

    public double getIngredientsCost (LocalDateTime date) {
        return ingredients.stream().mapToDouble(element -> element.getIngredient().getPriceByDate(date) * element.getRequiredQuantity()).sum();
    }

    public double getGrossMargin (LocalDateTime date) {
        return unitPrice - this.getIngredientsCost(date);
    }

    public double getAvalaibleQuantity (LocalDateTime date) {
        return ingredients.stream().mapToDouble(
                element -> element.getIngredient().getAvailableQuantity(date) / element.getRequiredQuantity()
        ).min().orElse(0);
    }

    public double getAvalaibleQuantity () {
        return ingredients.stream().mapToDouble(
                element -> element.getIngredient().getAvailableQuantity() / element.getRequiredQuantity()
        ).min().orElse(0.0);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "idDish='" + idDish + '\'' +
                ", dishName='" + dishName + '\'' +
                ", unitPrice=" + unitPrice +
                ", ingredients=" + ingredients +
                '}';
    }
}
