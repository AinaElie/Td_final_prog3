package school.hei.prog3.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StockMovement {
    private String idStock;
    private Ingredient ingredient;
    private Double requiredQuantity;
    private Unit unit;
    private LocalDateTime dateMovement;
    private Movement typeMovement;

    public StockMovement(String idStock, Double requiredQuantity, Unit unit, LocalDateTime dateMovement, Movement typeMovement) {
        this.idStock = idStock;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
        this.dateMovement = dateMovement;
        this.typeMovement = typeMovement;
    }
}