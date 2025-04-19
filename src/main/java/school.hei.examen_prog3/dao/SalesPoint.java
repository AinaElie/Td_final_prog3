package school.hei.examen_prog3.dao;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.stream.Collectors;

@Repository
public class SalesPoint {
    private static final String API_KEY = "RESTAURANT-API-KEY";

    public BestSales getBestSalesPDV() throws IOException, InterruptedException {
        Instant start = Instant.now();

        List<DishSold> salesPDV1 = getDishSoldFromPDV("https://ad67-197-158-81-35.ngrok-free.app/sales");
        List<DishSold> salesPDV2 = getDishSoldFromPDV("https://ed2d-197-158-81-35.ngrok-free.app/sales");
        
        SalesElement salesElement1 = new SalesElement(1L, "Analamahitsy", salesPDV1);
        SalesElement salesElement2 = new SalesElement(2L, "Antanimena", salesPDV2);

        return new BestSales(1L, start, List.of(salesElement1, salesElement2));
    }

    public BestProcessingTime getBestProcessingTimesPDV() throws IOException, InterruptedException, URISyntaxException {
        Instant start = Instant.now();

        List<BestProcessingTimeElement> processingTimesPDV1 =
                getProcessingTimesFromPDV("https://ad67-197-158-81-35.ngrok-free.app/processing-times");
        List<BestProcessingTimeElement> processingTimesPDV2 =
                getProcessingTimesFromPDV("https://ed2d-197-158-81-35.ngrok-free.app/processing-times");

        List<BestProcessingTimeElement> allProcessingTimes = new ArrayList<>();
        allProcessingTimes.addAll(processingTimesPDV1);
        allProcessingTimes.addAll(processingTimesPDV2);

        return new BestProcessingTime(1L, start, allProcessingTimes);
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

    private List<BestProcessingTimeElement> getProcessingTimesFromPDV(String url)
            throws IOException, InterruptedException, URISyntaxException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("X-API-KEY", API_KEY)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (responseBody.trim().startsWith("[")) {
                List<ProcessingTimeResponseDTO> dtos = mapper.readValue(responseBody,
                        new TypeReference<List<ProcessingTimeResponseDTO>>() {});

                return dtos.stream()
                        .map(this::convertToBestProcessingTimeElement)
                        .collect(Collectors.toList());
            } else {
                ProcessingTimeResponseDTO dto = mapper.readValue(responseBody, ProcessingTimeResponseDTO.class);
                return List.of(convertToBestProcessingTimeElement(dto));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse processing times response: " + responseBody, e);
        }
    }

    private BestProcessingTimeElement convertToBestProcessingTimeElement(ProcessingTimeResponseDTO dto) {
        return new BestProcessingTimeElement(
                dto.getDishIdentifier(),
                dto.getSalesPoint(),
                dto.getDishName(),
                dto.getPreparationDuration(),
                DurationUnit.valueOf(dto.getDurationUnit())
        );
    }
}