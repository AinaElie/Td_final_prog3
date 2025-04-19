package school.hei.prog3.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.hei.prog3.endpoint.mapper.IngredientRestMapper;
import school.hei.prog3.endpoint.rest.IngredientRest;
import school.hei.prog3.dao.operations.PriceCrudOperations;
import school.hei.prog3.dao.operations.StockCrudOperations;
import school.hei.prog3.endpoint.rest.StockMovementRest;
import school.hei.prog3.model.Ingredient;
import school.hei.prog3.dao.operations.IngredientCrudOperations;
import school.hei.prog3.model.Price;

import java.util.List;

@Getter
@Setter

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientCrudOperations ingredientCrudOperations;
    private final PriceCrudOperations priceCrudOperations;
    private final StockCrudOperations stockCrudOperations;
    private final IngredientRestMapper ingredientRestMapper;

    public ResponseEntity<List<IngredientRest>> getAll(int page, int size) {
        if ((page == 0 || size == 0) || (page < 0 || size < 0)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(ingredientRestMapper.applyAll(ingredientCrudOperations.getAll(page, size)));
    }

    public ResponseEntity<Ingredient> addNewIngredient (Ingredient ingredient) {
        if (ingredient == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(ingredientCrudOperations.createIngredient(ingredient));
    }

    public ResponseEntity<IngredientRest> addNewPrices (List<Price> prices, String idIngredient) {
        if (prices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        priceCrudOperations.saveAll(prices, idIngredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientRestMapper.apply(ingredientCrudOperations.findById(idIngredient)));
    }

    public ResponseEntity<IngredientRest> addStockMovement (List<StockMovementRest> stockMovements, String idIngredient) {
        if (stockMovements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        stockCrudOperations.saveAll(stockMovements, idIngredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientRestMapper.apply(ingredientCrudOperations.findById(idIngredient)));
    }

//    public ResponseEntity<Object> findByPrice(int priceMinFilter, int priceMaxFilter) {
//        List<Ingredient> ingredients = ingredientCrudOperations.findByPrice(priceMinFilter, priceMaxFilter);
//        if (priceMinFilter < 0 || priceMaxFilter < 0){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price cannot be negative");
//        } else if (priceMinFilter >= priceMaxFilter && priceMaxFilter != 0){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("priceFilterMin " + priceMinFilter + " cannot be greater than priceMaxFilter " + priceMaxFilter);
//        }
//        return ResponseEntity.ok(ingredients);
//    }

    public ResponseEntity<IngredientRest> findById (String idIngredient) {
        Ingredient ingredient = ingredientCrudOperations.findById(idIngredient);
        return ingredient == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok().body(ingredientRestMapper.apply(ingredient));
    }
}
