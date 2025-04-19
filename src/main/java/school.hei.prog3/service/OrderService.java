package school.hei.prog3.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.hei.prog3.endpoint.mapper.OrderRestMapper;
import school.hei.prog3.endpoint.rest.DishOrderRest;
import school.hei.prog3.endpoint.rest.OrderRest;
import school.hei.prog3.dao.operations.DishOrderCrudOperations;
import school.hei.prog3.dao.operations.OrderCrudOperations;
import school.hei.prog3.model.DishOrder;
import school.hei.prog3.model.DishSale;
import school.hei.prog3.model.Order;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderCrudOperations orderCrudOperations;
    private final DishOrderCrudOperations dishOrderCrudOperations;
    private final OrderRestMapper orderRestMapper;

    public ResponseEntity<List<OrderRest>> getAllOrders(int page, int size) {
        if ((page == 0 || size == 0) || (page < 0 || size < 0)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderRestMapper.applyAll(orderCrudOperations.getAll(page, size)));
    }

    public ResponseEntity<OrderRest> getOrderByReference(String reference) {
        if (reference == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderRestMapper.apply(orderCrudOperations.findByReference(reference)));
    }

    public ResponseEntity<List<DishOrderRest>> updateOrder(String reference, OrderRest orderRest) {
        if (orderRest == null || reference == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }
        Order order = orderCrudOperations.findByReference(reference);
        orderCrudOperations.save(order, orderRest);
        return ResponseEntity.status(HttpStatus.OK).body(orderRestMapper.apply(orderCrudOperations.findByReference(reference)).getDishes());
    }

    public ResponseEntity<List<DishOrder>> nextStatusDishInOrder(String reference, String idDish) {
        if (idDish == null || reference == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Order order = orderCrudOperations.addNextStatus(reference, idDish);
        return ResponseEntity.status(HttpStatus.OK).body(order.getDishes());
    }

    public ResponseEntity<List<DishSale>> getBestSaleDishes(int top) {
        if (top == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderCrudOperations.getBestSale(top));
    }
}
