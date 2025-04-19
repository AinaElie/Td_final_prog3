package school.hei.examen_prog3.controller.mapper;

import lombok.RequiredArgsConstructor;
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
    public SalesElementRestMapper() {
    }

    @SneakyThrows
    @Override
    public SalesElementRest apply(SalesElement salesElement) {
        // Calculate total quantities and amounts across all dishes
        double totalQuantity = salesElement.getDishSoldList().stream()
                .mapToDouble(DishSold::getQuantitySold)
                .sum();

        double totalAmount = salesElement.getDishSoldList().stream()
                .mapToDouble(DishSold::getTotal_amount)
                .sum();

        return new SalesElementRest(
                salesElement.getSalesPoint(),
                "Multiple Dishes", // or pick the top dish
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
