package school.hei.prog3.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.dao.operations.StatusOrderCrudOperations;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Order {
    private String idOrder;
    private String reference;
    private List<DishOrder> dishes;
    private List<StatusOrder> listStatus;

    public Order(String idOrder, String reference, List<DishOrder> dishes, List<StatusOrder> listStatus) {
        this.idOrder = idOrder;
        this.reference = reference;
        this.dishes = dishes;
        this.listStatus = listStatus;
    }

    public Status getActualStatus() {
        return listStatus.getLast().getStatus();
    }

    public double getTotalAmount() {
        return dishes.stream().mapToDouble(element -> element.getQuantity() * element.getDish().getUnitPrice()).sum();
    }

    public List<StatusOrder> addNextStatus() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Status statsActual = getActualStatus();
        switch (statsActual) {
            case CREATE -> {
                int dishCount = dishes.stream().filter(element -> element.getActualStatus().equals(Status.CONFIRMED)).toList().size();
                if (dishCount == dishes.size()) {
                    listStatus.add(new StatusOrder(Status.CONFIRMED, LocalDateTime.now()));
                }
                throw new RuntimeException("Insufficient quantity");
            }
            case CONFIRMED -> {
                int dishCount = dishes.stream().filter(element -> element.getActualStatus().equals(Status.IN_PREPARATION)).toList().size();
                if (dishCount == dishes.size()) {
                    listStatus.add(new StatusOrder(Status.IN_PREPARATION, LocalDateTime.now()));
                }
            }
            case IN_PREPARATION -> {
                int dishCount = dishes.stream().filter(element -> element.getActualStatus().equals(Status.FINISHED)).toList().size();
                if (dishCount == dishes.size()) {
                    listStatus.add(new StatusOrder(Status.FINISHED, LocalDateTime.now()));
                }
            }
            case FINISHED -> {
                int dishCount = dishes.stream().filter(element -> element.getActualStatus().equals(Status.SERVE)).toList().size();
                if (dishCount == dishes.size()) {
                    listStatus.add(new StatusOrder(Status.SERVE, LocalDateTime.now()));
                }
            }
            case SERVE -> throw new RuntimeException("Order already served");
        }

        StatusOrderCrudOperations crudStatus = new StatusOrderCrudOperations(databaseConnection);

        for (StatusOrder statusOrder : listStatus) {
            crudStatus.createStatus(this.getIdOrder(), statusOrder);
        }

        return listStatus;
    }
}
