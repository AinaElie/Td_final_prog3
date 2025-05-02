package school.hei.examen_prog3.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.hei.examen_prog3.controller.rest.BestProcessingTimeRest;
import school.hei.examen_prog3.controller.rest.BestSalesRest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class CentralService {
    private final BestSalesService bestSalesService;
    private final BestProcessingTimeService bestProcessingTimeService;

    public CentralService(BestSalesService bestSalesService, BestProcessingTimeService bestProcessingTimeService) {
        this.bestSalesService = bestSalesService;
        this.bestProcessingTimeService = bestProcessingTimeService;
    }

    public List<BestSalesRest> getAllBestSales(int top) throws IOException, InterruptedException {
        return bestSalesService.getAll(top);
    }

    public BestProcessingTimeRest getBestProcessingTime(String dishId, int top, String durationUnit, String calculationMode)
            throws IOException, InterruptedException, URISyntaxException {
        return bestProcessingTimeService.getBestProcessingTime(dishId, top, durationUnit, calculationMode);
    }

    public void synchronizeData() throws IOException, InterruptedException, URISyntaxException {
        // Clear existing data first
        bestSalesService.clearData();
        bestProcessingTimeService.clearData();

        // Then fetch and save new data
        bestSalesService.synchronize();
        bestProcessingTimeService.synchronize();
    }
}