package school.hei.prog3.endpoint.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.prog3.endpoint.rest.OrderRest;
import school.hei.prog3.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OrderRestMapper implements Function<Order, OrderRest> {
    private final DishOrderRestMapper dishOrderRestMapper;

    @Override
    public OrderRest apply(Order order) {
        return new OrderRest(
                order.getReference(),
                dishOrderRestMapper.applyAll(order.getDishes()),
                order.getListStatus()
        );
    }

    public List<OrderRest> applyAll(List<Order> orders) {
        List<OrderRest> orderRests = new ArrayList<>();
        for (Order order : orders) {
            orderRests.add(apply(order));
        }
        return orderRests;
    }
}
