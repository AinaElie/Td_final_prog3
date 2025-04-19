package school.hei.prog3.endpoint;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.prog3.endpoint.rest.DishOrderRest;
import school.hei.prog3.endpoint.rest.OrderRest;
import school.hei.prog3.model.DishOrder;
import school.hei.prog3.model.DishSale;
import school.hei.prog3.service.OrderService;

import java.util.List;

@Getter
@Setter

@RestController
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderRest>> getOrders(@RequestParam(name = "page") int page,
                                                     @RequestParam(name = "size") int size) {
        return orderService.getAllOrders(page, size);
    }

    @GetMapping("/orders/{reference}")
    public ResponseEntity<OrderRest> getOrderByReference(@PathVariable(name = "reference") String reference) {
        return orderService.getOrderByReference(reference);
    }

    @GetMapping("/orders/dishes/bestSales")
    public ResponseEntity<List<DishSale>> getBestSales(@RequestParam(name = "top") int top) {
        return orderService.getBestSaleDishes(top);
    }

    @PutMapping("/orders/{reference}/dishes")
    public ResponseEntity<List<DishOrderRest>> updateListDish(@PathVariable(name = "reference") String reference, @RequestBody OrderRest orderRest) {
        return orderService.updateOrder(reference, orderRest);
    }

    @PutMapping("/orders/{reference}/dishes/{idDish}")
    public ResponseEntity<List<DishOrder>> nextStatusOrder(@PathVariable(name = "reference") String reference,
                                                           @PathVariable(name = "idDish") String idDish) {
        return orderService.nextStatusDishInOrder(reference, idDish);
    }
}
