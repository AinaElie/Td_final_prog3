package school.hei.prog3.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.examen_prog3.model.DishSold;

import java.util.List;

@RestController
public class HealthRestController {
    @GetMapping("/ping")
    public String pong() {
        return "Pong";
    }

    @GetMapping("/sales")
    public List<DishSold> getSales () {
        return List.of(new DishSold(1L, "Hot dog", 5, 50000), new DishSold(2L, "Coffee", 5, 50000));
    }
}
