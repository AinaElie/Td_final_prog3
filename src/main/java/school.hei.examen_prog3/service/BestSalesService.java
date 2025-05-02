package school.hei.examen_prog3.service;

import org.springframework.stereotype.Service;
import school.hei.examen_prog3.controller.mapper.BestSalesRestMapper;
import school.hei.examen_prog3.controller.rest.BestSalesRest;
import school.hei.examen_prog3.dao.SalesPoint;
import school.hei.examen_prog3.dao.operations.BestSalesCrudOperations;
import school.hei.examen_prog3.dao.operations.SalesElementCrudOperations;
import school.hei.examen_prog3.model.BestSales;
import school.hei.examen_prog3.model.SalesElement;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BestSalesService {
    private final BestSalesCrudOperations bestSalesCrudOperations;
    private final SalesElementCrudOperations salesElementCrudOperations;
    private final BestSalesRestMapper bestSalesRestMapper;
    private final SalesPoint salesPoint;

    public BestSalesService(BestSalesCrudOperations bestSalesCrudOperations,
                            SalesElementCrudOperations salesElementCrudOperations,
                            BestSalesRestMapper bestSalesRestMapper,
                            SalesPoint salesPoint) {
        this.bestSalesCrudOperations = bestSalesCrudOperations;
        this.salesElementCrudOperations = salesElementCrudOperations;
        this.bestSalesRestMapper = bestSalesRestMapper;
        this.salesPoint = salesPoint;
    }

    public List<BestSalesRest> getAll(int top) throws IOException, InterruptedException {
        List<BestSales> bestSales = bestSalesCrudOperations.getAll();
        return bestSalesRestMapper.applyAll(bestSales).stream()
                .limit(top)
                .collect(Collectors.toList());
    }

    public void synchronize() throws IOException, InterruptedException {
        clearData();

        BestSales bestSales = salesPoint.getBestSalesPDV();
        BestSales savedBestSales = bestSalesCrudOperations.create(bestSales);

        for (SalesElement salesElement : bestSales.getSales()) {
            Long salesElementId = salesElementCrudOperations.create(salesElement, savedBestSales.getId());
            salesElement.getDishSoldList().forEach(dish ->
                    salesElementCrudOperations.createDishSold(dish, salesElementId));
        }
    }

    public void clearData() {
        bestSalesCrudOperations.deleteAll();
    }
}