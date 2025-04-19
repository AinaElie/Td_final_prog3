package school.hei.prog3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class StockMovementManage {
    private List<StockMovement> stockMovements;

    public StockMovementManage(List<StockMovement> stockMovements) {
        this.stockMovements = stockMovements;
    }

    public List<StockMovement> getStockMovementsByTypeMovement (Movement movement, LocalDateTime date) {
        return stockMovements.stream().filter(element -> element.getTypeMovement().equals(movement)
                && element.getDateMovement().isBefore(date)).toList();
    }
}