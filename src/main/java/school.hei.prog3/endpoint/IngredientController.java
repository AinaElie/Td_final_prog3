package school.hei.prog3.endpoint;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.prog3.endpoint.rest.IngredientRest;
import school.hei.prog3.endpoint.rest.StockMovementRest;
import school.hei.prog3.model.Ingredient;
import school.hei.prog3.model.Price;
import school.hei.prog3.model.StockMovement;
import school.hei.prog3.service.IngredientService;

import java.util.List;

@Getter
@Setter

@RestController
public class IngredientController {
    private IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientRest>> getAllIngredients(@RequestParam(name = "page") int page,
                                                                  @RequestParam(name = "size") int size) {
        return ingredientService.getAll(page, size);
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientRest> getIngredient(@PathVariable (name = "id") String idIngredient) {
        return ingredientService.findById(idIngredient);
    }

    @PutMapping("/ingredients/{id}/prices")
    public ResponseEntity<IngredientRest> addNewPricesIngredient(@PathVariable(name = "id") String id, @RequestBody List<Price> prices) {
        return ingredientService.addNewPrices(prices, id);
    }

    @PutMapping("/ingredients/{id}/stockMovements")
    public ResponseEntity<IngredientRest> addNewStockMovementsIngredient(@PathVariable(name = "id") String id, @RequestBody List<StockMovementRest> stockMovements) {
        return ingredientService.addStockMovement(stockMovements, id);
    }

    @PostMapping("/ingredient")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addNewIngredient(ingredient);
    }

    //    @GetMapping("/ingredients")
//    public ResponseEntity<Object> getIngredientsByPrice(@RequestParam (name = "priceMinFilter", defaultValue = "0", required = false) int priceMinFilter,
//                                                   @RequestParam (name = "priceMaxFilter", defaultValue = "0", required = false) int priceMaxFilter) {
//        return ingredientService.findByPrice(priceMinFilter, priceMaxFilter);
//    }
}
