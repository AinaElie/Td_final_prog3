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
        DishSold dishSold = salesElement.getDishSoldList().getFirst();

        return new SalesElementRest(
                salesElement.getSalesPoint(),
                dishSold.getDish(),
                dishSold.getQuantitySold(),
                dishSold.getTotal_amount()
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
