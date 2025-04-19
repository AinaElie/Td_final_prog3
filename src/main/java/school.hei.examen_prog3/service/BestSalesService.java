package school.hei.examen_prog3.service;

import org.springframework.stereotype.Service;
import school.hei.examen_prog3.controller.mapper.BestSalesRestMapper;
import school.hei.examen_prog3.controller.rest.BestSalesRest;
import school.hei.examen_prog3.dao.SalesPoint;
import school.hei.examen_prog3.dao.operations.BestSalesCrudOperations;
import school.hei.examen_prog3.model.BestSales;
import school.hei.examen_prog3.model.SalesElement;

import java.io.IOException;
import java.util.List;

@Service
public class BestSalesService {
    private final BestSalesCrudOperations bestSalesCrudOperations;
    private final BestSalesRestMapper bestSalesRestMapper;
    private final SalesElementService salesElementService;
    private final SalesPoint salesPoint;
    public BestSalesService(BestSalesCrudOperations bestSalesCrudOperations, BestSalesRestMapper bestSalesRestMapper, SalesElementService salesElementService, SalesPoint salesPoint) throws IOException, InterruptedException {
        this.bestSalesCrudOperations = bestSalesCrudOperations;
        this.bestSalesRestMapper = bestSalesRestMapper;
        this.salesElementService = salesElementService;
        this.salesPoint = salesPoint;
    }

    public List<BestSalesRest> getAll(int top) throws IOException, InterruptedException {
        this.createBestSales(salesPoint.getBestSalesPDV());
        return bestSalesRestMapper.applyAll(bestSalesCrudOperations.getAll());
    }

    public void createBestSales(BestSales bestSales) {
        BestSales newBestSales = bestSalesCrudOperations.create(bestSales);
        for (SalesElement salesElement : bestSales.getSales()) {
            salesElementService.createSalesElement(salesElement, newBestSales.getId());
        }
    }

    public void saveAll() throws IOException, InterruptedException {
        this.createBestSales(salesPoint.getBestSalesPDV());
    }
}
