package school.hei.prog3.endpoint.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.prog3.endpoint.rest.DishOrderRest;
import school.hei.prog3.model.DishOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishOrderRestMapper implements Function<DishOrder, DishOrderRest> {

    @SneakyThrows
    @Override
    public DishOrderRest apply(DishOrder dishOrder) {
        return new DishOrderRest(
                dishOrder.getIdDishOrder(),
                dishOrder.getDish().getDishName(),
                dishOrder.getDish().getUnitPrice(),
                dishOrder.getQuantity(),
                dishOrder.getListStatus()
        );
    }

    public List<DishOrderRest> applyAll(List<DishOrder> dishOrders) {
        List<DishOrderRest> dishOrderRestList = new ArrayList<>();
        for (DishOrder dishOrder : dishOrders) {
            dishOrderRestList.add(apply(dishOrder));
        }
        return dishOrderRestList;
    }
}
