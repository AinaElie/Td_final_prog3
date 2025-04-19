package school.hei.examen_prog3.controller.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.controller.rest.BestSalesRest;
import school.hei.examen_prog3.model.BestSales;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class BestSalesRestMapper implements Function<BestSales, BestSalesRest> {
    private final SalesElementRestMapper salesElementRestMapper;
    public BestSalesRestMapper(SalesElementRestMapper salesElementRestMapper) {
        this.salesElementRestMapper = salesElementRestMapper;
    }

    @SneakyThrows
    @Override
    public BestSalesRest apply(BestSales bestSales) {
        return new BestSalesRest(
                bestSales.getUpdateAt(),
                salesElementRestMapper.applyAll(bestSales.getSales())
        );
    }

    @SneakyThrows
    public List<BestSalesRest> applyAll (List<BestSales> bestSales) {
        List<BestSalesRest> bestSalesRestList = new ArrayList<>();

        for (BestSales bestSale : bestSales) {
            bestSalesRestList.add(this.apply(bestSale));
        }

        return bestSalesRestList;
    }
}
