package school.hei.examen_prog3.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesPoint {
    private static final String API_KEY = "RESTAURANT-API-KEY";

    public BestSales getBestSalesPDV() throws IOException, InterruptedException {
        Instant start = Instant.now();

        List<DishSold> salesPDV1 = getDishSoldFromPDV("http://localhost:8080/sales");
        List<DishSold> salesPDV2 = getDishSoldFromPDV("http://localhost:8082/sales");
        
        salesPDV1.forEach(dish -> {
            double price = getDishPrice(dish.getDish());
            dish.setTotal_amount(dish.getQuantitySold() * price);
        });

        salesPDV2.forEach(dish -> {
            double price = getDishPrice(dish.getDish());
            dish.setTotal_amount(dish.getQuantitySold() * price);
        });

        SalesElement salesElement1 = new SalesElement(1L, "Analamahitsy", salesPDV1);
        SalesElement salesElement2 = new SalesElement(2L, "Antanimena", salesPDV2);

        return new BestSales(1L, start, List.of(salesElement1, salesElement2));
    }

    private double getDishPrice(String dishName) {
        return switch (dishName) {
            case "Hot dog" -> 15000;
            case "Omelette" -> 5000;
            case "Saucisse frit" -> 3500;
            default -> 0;
        };
    }

    public BestProcessingTime getBestProcessingTimesPDV() throws IOException, InterruptedException, URISyntaxException {
        Instant start = Instant.now();
        List<BestProcessingTimeElement> allProcessingTimes = new ArrayList<>();

        try {
            allProcessingTimes.addAll(getProcessingTimesFromPDV("http://localhost:8080", "Analamahitsy"));
        } catch (Exception e) {
            System.err.println("Error fetching from PDV1: " + e.getMessage());
        }

        try {
            allProcessingTimes.addAll(getProcessingTimesFromPDV("http://localhost:8082", "Antanimena"));
        } catch (Exception e) {
            System.err.println("Error fetching from PDV2: " + e.getMessage());
        }

        return new BestProcessingTime(1L, start, allProcessingTimes);
    }

    private List<BestProcessingTimeElement> getProcessingTimesFromPDV(String baseUrl, String salesPointName) {
        HttpClient client = HttpClient.newHttpClient();
        List<BestProcessingTimeElement> processingTimes = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        for (int dishId = 1; dishId <= 3; dishId++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(baseUrl + "/dishes/" + dishId + "/processingTimes"))
                        .header("X-API-KEY", API_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200 && !response.body().isBlank()) {
                    try {
                        List<ProcessingTimeResponseDTO> dtos = mapper.readValue(
                                response.body(),
                                new TypeReference<>() {});

                        for (ProcessingTimeResponseDTO dto : dtos) {
                            if (dto.getPreparationDuration() == null) continue;

                            BestProcessingTimeElement element = new BestProcessingTimeElement(
                                    null,
                                    dto.getSalesPoint() != null ? dto.getSalesPoint() : salesPointName,
                                    dto.getDishName() != null ? dto.getDishName() : "Dish " + dishId,
                                    dto.getPreparationDuration(),
                                    parseDurationUnit(dto.getDurationUnit())
                            );
                            processingTimes.add(element);
                        }
                    } catch (Exception e) {
                        System.err.printf("Error parsing response for dish %d: %s%n", dishId, e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.printf("Error fetching dish %d: %s%n", dishId, e.getMessage());
            }
        }
        return processingTimes;
    }

    private DurationUnit parseDurationUnit(String unit) {
        if (unit == null) return DurationUnit.SECONDS;
        try {
            return DurationUnit.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DurationUnit.SECONDS;
        }
    }

    private List<DishSold> getDishSoldFromPDV(String url) throws IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("X-API-KEY", API_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<DishSold>>() {});
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDishNameById(String dishId) {
        return switch (dishId) {
            case "1" -> "Hot dog";
            case "2" -> "Saucisse frit";
            case "3" -> "Omelette";
            default -> "Unknown";
        };
    }

    private BestProcessingTimeElement convertToBestProcessingTimeElement(ProcessingTimeResponseDTO dto) {
        return new BestProcessingTimeElement(
                (long) dto.getDishIdentifier(),
                dto.getSalesPoint(),
                dto.getDishName(),
                dto.getPreparationDuration(),
                DurationUnit.valueOf(dto.getDurationUnit())
        );
    }
}