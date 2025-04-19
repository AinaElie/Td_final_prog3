package school.hei.prog3.endpoint.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.prog3.endpoint.rest.StockMovementRest;
import school.hei.prog3.model.StockMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class StockMovementRestMapper implements Function<StockMovement, StockMovementRest> {

    @Override
    public StockMovementRest apply(StockMovement stockMovement) {
        return new StockMovementRest(
                stockMovement.getIdStock(),
                stockMovement.getRequiredQuantity(),
                stockMovement.getUnit(),
                stockMovement.getDateMovement(),
                stockMovement.getTypeMovement()
        );
    }

    public List<StockMovementRest> applyAll(List<StockMovement> stockMovements) {
        List<StockMovementRest> stockMovementRests = new ArrayList<>();
        for (StockMovement stockMovement : stockMovements) {
            stockMovementRests.add(apply(stockMovement));
        }
        return stockMovementRests;
    }
}
