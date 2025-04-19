package school.hei.examen_prog3.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.hei.examen_prog3.controller.rest.BestSalesRest;

import java.io.IOException;
import java.util.List;

@Service
public class CentralService {
    private final BestSalesService bestSalesService;
    public CentralService(BestSalesService bestSalesService) {
        this.bestSalesService = bestSalesService;
    }

    public ResponseEntity<List<BestSalesRest>> getAllBestSales(int top) throws IOException, InterruptedException {
        return ResponseEntity.ok().body(bestSalesService.getAll(top));
    }

    public void saveAll() throws IOException, InterruptedException {
        bestSalesService.saveAll();
    }
}
