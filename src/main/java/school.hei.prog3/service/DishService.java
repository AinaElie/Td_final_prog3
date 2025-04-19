package school.hei.prog3.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.hei.prog3.dao.operations.DishOrderCrudOperations;
import school.hei.prog3.endpoint.mapper.DishIngredientRestMapper;
import school.hei.prog3.endpoint.mapper.DishRestMapper;
import school.hei.prog3.endpoint.rest.DishIngredientRest;
import school.hei.prog3.endpoint.rest.DishRest;
import school.hei.prog3.dao.operations.DishIngredientCrudOperations;
import school.hei.prog3.dao.operations.IngredientCrudOperations;
import school.hei.prog3.dao.operations.DishCrudOperations;
import school.hei.prog3.model.DishOrder;
import school.hei.prog3.model.ProcessingTime;
import school.hei.prog3.model.ProcessingTimeMapper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishCrudOperations dishCrudOperations;
    private final IngredientCrudOperations ingredientCrudOperations;
    private final DishIngredientCrudOperations dishIngredientCrudOperations;
    private final DishRestMapper dishRestMapper;
    private final DishOrderCrudOperations dishOrderCrudOperations;
    private final DishIngredientRestMapper dishIngredientRestMapper;

    public ResponseEntity<List<DishRest>> getAllDish(int page, int size) {
        if ((page != 0 && size != 0)) {
            return ResponseEntity.status(HttpStatus.OK).body(dishRestMapper.applyAll(dishCrudOperations.getAll(page, size)));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<DishRest> getDishById(String id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(dishRestMapper.apply(dishCrudOperations.getDishWithIngredient(id)));
    }

    public ResponseEntity<List<DishIngredientRest>> addNewDishIngredients(DishRest dish, String idDish) {
        if (dish == null || dish.getIngredients().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }
        return ResponseEntity.status(HttpStatus.OK).body(dishIngredientRestMapper.applyAll(dishCrudOperations.saveAll(dish.getIngredients(), idDish)));
    }

    public ResponseEntity<ProcessingTimeMapper> getDuration(String idDish, String duration, String note) {
        List<DishOrder> dishOrders = dishOrderCrudOperations.findDishesByIdDish(idDish);
        List<LocalTime> times = dishOrders.stream()
                .map(DishOrder::getDuration)
                .filter(Objects::nonNull)
                .toList();

        if (times.isEmpty() || idDish == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        ProcessingTime processingTime = new ProcessingTime(times, duration);

        return switch (note == null ? "MOYEN" : note.toUpperCase()) {
            case "MOYEN" -> ResponseEntity.status(HttpStatus.OK).body(processingTime.toDto(processingTime.average()));
            case "MINIMUM" -> ResponseEntity.status(HttpStatus.OK).body(processingTime.toDto(processingTime.minus()));
            case "MAXIMUM" -> ResponseEntity.status(HttpStatus.OK).body(processingTime.toDto(processingTime.max()));
            default -> ResponseEntity.status(HttpStatus.OK).body(processingTime.toDto(processingTime.average()));
        };
    }
}
