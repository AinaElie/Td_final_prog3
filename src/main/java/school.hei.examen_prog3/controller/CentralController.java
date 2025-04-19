package school.hei.examen_prog3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.examen_prog3.controller.rest.BestProcessingTimeRest;
import school.hei.examen_prog3.controller.rest.BestSalesRest;
import school.hei.examen_prog3.security.ApiKeyManager;
import school.hei.examen_prog3.service.CentralService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/central")
public class CentralController {
    private final CentralService centralService;
    private final ApiKeyManager apiKeyManager;

    public CentralController(CentralService centralService, ApiKeyManager apiKeyManager) {
        this.centralService = centralService;
        this.apiKeyManager = apiKeyManager;
    }

    @GetMapping("/ping")
    public String getPong() {
        return "pong";
    }

    @GetMapping("/bestSales")
    public ResponseEntity<List<BestSalesRest>> getBestSales(
            @RequestParam(name = "top") int top,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) throws IOException, InterruptedException {

        if (!apiKeyManager.isValidApiKey(apiKey)) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(centralService.getAllBestSales(top));
    }

    @GetMapping("/dishes/{id}/bestProcessingTime")
    public ResponseEntity<BestProcessingTimeRest> getBestProcessingTime(
            @PathVariable String id,
            @RequestParam(name = "top") int top,
            @RequestParam(name = "durationUnit", defaultValue = "SECONDS") String durationUnit,
            @RequestParam(name = "calculationMode", defaultValue = "AVERAGE") String calculationMode,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) throws IOException, InterruptedException, URISyntaxException {

        if (!apiKeyManager.isValidApiKey(apiKey)) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(centralService.getBestProcessingTime(id, top, durationUnit, calculationMode));
    }

    @PostMapping("/synchronization")
    public ResponseEntity<String> synchronization(@RequestHeader("X-API-KEY") String apiKey)
            throws IOException, InterruptedException, URISyntaxException {

        if (!apiKeyManager.isValidApiKey(apiKey)) {
            return ResponseEntity.status(401).build();
        }

        centralService.synchronizeData();
        return ResponseEntity.ok("Synchronization completed successfully");
    }
}