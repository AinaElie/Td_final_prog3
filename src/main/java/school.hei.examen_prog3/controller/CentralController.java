package school.hei.examen_prog3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.examen_prog3.controller.rest.BestSalesRest;
import school.hei.examen_prog3.service.CentralService;

import java.io.IOException;
import java.util.List;

@RestController
public class CentralController {
    private final CentralService centralService;
    public CentralController(CentralService centralService) {
        this.centralService = centralService;
    }

    @GetMapping("/ping")
    public String getPong() {
        return "pong";
    }

    @GetMapping("/bestSales")
    public ResponseEntity<List<BestSalesRest>> getBestSales(@RequestParam(name = "top") int top) throws IOException, InterruptedException {
        return centralService.getAllBestSales(top);
    }

    @PostMapping("/syncrhonization")
    public void syncrhonization() throws IOException, InterruptedException {
        centralService.saveAll();
    }
}
