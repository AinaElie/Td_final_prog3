package school.hei.examen_prog3.service;

import org.springframework.stereotype.Service;
import school.hei.examen_prog3.dao.operations.DishSoldCrudOperations;
import school.hei.examen_prog3.dao.operations.SalesElementCrudOperations;
import school.hei.examen_prog3.model.DishSold;
import school.hei.examen_prog3.model.SalesElement;

@Service
public class SalesElementService {
    private final SalesElementCrudOperations salesElementCrudOperations;
    private final DishSoldCrudOperations dishSoldCrudOperations;

    public SalesElementService(SalesElementCrudOperations salesElementCrudOperations, DishSoldCrudOperations dishSoldCrudOperations) {
        this.salesElementCrudOperations = salesElementCrudOperations;
        this.dishSoldCrudOperations = dishSoldCrudOperations;
    }

    public void createSalesElement(SalesElement salesElement, Long idBestSales) {
        Long id = salesElementCrudOperations.create(salesElement, idBestSales);
        for (DishSold dishSold : salesElement.getDishSoldList()) {
            dishSoldCrudOperations.create(dishSold, id);
        }
    }
}
