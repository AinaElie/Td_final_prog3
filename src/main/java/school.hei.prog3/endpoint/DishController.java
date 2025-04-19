package school.hei.prog3.endpoint;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.prog3.endpoint.rest.DishIngredientRest;
import school.hei.prog3.endpoint.rest.DishRest;
import school.hei.prog3.model.ProcessingTimeMapper;
import school.hei.prog3.service.DishService;

import java.util.List;

@Getter
@Setter

@RestController
public class DishController {
    private DishService dishService;
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public ResponseEntity<List<DishRest>> getAllDish(@RequestParam(name = "page") int page,
                                                     @RequestParam(name = "size") int size) {
        return dishService.getAllDish(page, size);
    }

    @GetMapping("/dishes/{id}/processingTime")
    public ResponseEntity<ProcessingTimeMapper> getDurationDish(@PathVariable(name = "id") String idDish,
                                                                @RequestParam(name = "duration", required = false) String duration,
                                                                @RequestParam(name = "note", required = false) String note) {
        return dishService.getDuration(idDish, duration, note);
    }

    @GetMapping("/dishes/{id}")
    public ResponseEntity<DishRest> getDish(@PathVariable(name = "id") String idDish) {
        return dishService.getDishById(idDish);
    }

    @PutMapping("/dishes/{id}/ingredients")
    public ResponseEntity<List<DishIngredientRest>> addNewIngredients(@PathVariable (name = "id") String idDish,
                                                                      @RequestBody DishRest dish) {
        return dishService.addNewDishIngredients(dish, idDish);
    }
}
