package school.hei.prog3.endpoint.rest;

import lombok.*;
import school.hei.prog3.model.Movement;
import school.hei.prog3.model.Unit;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StockMovementRest {
    private String idStock;
    private Double requiredQuantity;
    private Unit unit;
    private LocalDateTime dateMovement;
    private Movement typeMovement;

    public StockMovementRest(String idStock, Double requiredQuantity, Unit unit, LocalDateTime dateMovement, Movement typeMovement) {
        this.idStock = idStock;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
        this.dateMovement = dateMovement;
        this.typeMovement = typeMovement;
    }
}
