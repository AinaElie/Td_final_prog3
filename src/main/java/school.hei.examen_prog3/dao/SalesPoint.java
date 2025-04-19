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
import java.util.List;

@Repository
public class SalesPoint {
    public SalesPoint() {
    }

    public List<DishSold> getDishSoldOne() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://4094-102-16-234-221.ngrok-free.app/sales"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<DishSold>>() {});
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DishSold> getDishSoldTwo() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://4094-102-16-234-221.ngrok-free.app/sales"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<DishSold>>() {});
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    Instant start = Instant.now();
    public BestSales getBestSalesPDV () throws IOException, InterruptedException {
        SalesElement salesElementOne = new SalesElement(1L, "Analamahitsy", this.getDishSoldOne());
        SalesElement salesElementTwo = new SalesElement(2L, "Analakely", this.getDishSoldTwo());
        return new BestSales(1L, start, List.of(salesElementOne, salesElementTwo));
    }

}
