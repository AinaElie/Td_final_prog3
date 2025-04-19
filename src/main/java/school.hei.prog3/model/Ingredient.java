package school.hei.prog3.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Ingredient {
    private String idIngredient;
    private String ingredientName;
    private List<Price> priceList;
    private Unit unit;
    private List<StockMovement> stockMovement;

    public Ingredient(String idIngredient, String ingredientName, List<Price> priceList, Unit unit, List<StockMovement> stockMovement) {
        this.idIngredient = idIngredient;
        this.ingredientName = ingredientName;
        this.priceList = priceList;
        this.unit = unit;
        this.stockMovement = stockMovement;
    }

    public double getPriceByDate(LocalDateTime date) {
        double priceResult = 0;

        for (Price price : priceList) {
            if (price.getUpdateDateTime().equals(date)) {
                priceResult += price.getPrice();
            }
        }

        return priceResult;
    }

    public double getAvailableQuantity (LocalDateTime date) {
        StockMovementManage stockMovementManage = new StockMovementManage(this.getStockMovement());
        List<StockMovement> stockEnter = stockMovementManage.getStockMovementsByTypeMovement(Movement.enter, date);
        List<StockMovement> stockExit = stockMovementManage.getStockMovementsByTypeMovement(Movement.exit, date);

        Double quantityEnter = stockEnter.stream().mapToDouble(element -> 0.0 + element.getRequiredQuantity()).sum();
        Double quantityExit = stockExit.stream().mapToDouble(element -> 0.0 + element.getRequiredQuantity()).sum();

        return quantityEnter - quantityExit;
    }

    public double getAvailableQuantity () {
        StockMovementManage stockMovementManage = new StockMovementManage(this.getStockMovement());
        List<StockMovement> stockEnter = stockMovementManage.getStockMovementsByTypeMovement(Movement.enter, LocalDateTime.now());
        List<StockMovement> stockExit = stockMovementManage.getStockMovementsByTypeMovement(Movement.exit, LocalDateTime.now());

        Double quantityEnter = stockEnter.stream().mapToDouble(element -> 0.0 + element.getRequiredQuantity()).sum();
        Double quantityExit = stockExit.stream().mapToDouble(element -> 0.0 + element.getRequiredQuantity()).sum();

        return quantityEnter - quantityExit;
    }

    public double getActualPrice() {
        return priceList.getLast().getPrice();
    }
}
