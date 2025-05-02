package school.hei.examen_prog3.controller.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.controller.rest.SalesElementRest;
import school.hei.examen_prog3.model.DishSold;
import school.hei.examen_prog3.model.SalesElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class SalesElementRestMapper implements Function<SalesElement, SalesElementRest> {

    @SneakyThrows
    @Override
    public SalesElementRest apply(SalesElement salesElement) {
        double totalQuantity = salesElement.getDishSoldList().stream()
                .mapToDouble(DishSold::getQuantitySold)
                .sum();

        double totalAmount = salesElement.getDishSoldList().stream()
                .mapToDouble(DishSold::getTotal_amount)
                .sum();

        String dishes = String.join(", ",
                salesElement.getDishSoldList().stream()
                        .map(DishSold::getDish)
                        .toList());

        return new SalesElementRest(
                salesElement.getSalesPoint(),
                dishes.isEmpty() ? "No Dishes" : dishes,
                totalQuantity,
                totalAmount
        );
    }

    @SneakyThrows
    public List<SalesElementRest> applyAll(List<SalesElement> sales) {
        List<SalesElementRest> salesElementRestList = new ArrayList<>();
        for (SalesElement salesElement : sales) {
            salesElementRestList.add(this.apply(salesElement));
        }
        return salesElementRestList;
    }
}