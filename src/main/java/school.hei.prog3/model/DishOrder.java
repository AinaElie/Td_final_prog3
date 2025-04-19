package school.hei.prog3.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import school.hei.prog3.dao.DatabaseConnection;
import school.hei.prog3.dao.operations.StatusDishOrderCrudOperations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DishOrder {
    private String idDishOrder;
    private Dish dish;
    private double quantity;
    private List<StatusOrder> listStatus;

    public DishOrder(String idDishOrder, Dish dish, double quantity, List<StatusOrder> listStatus) {
        this.idDishOrder = idDishOrder;
        this.dish = dish;
        this.quantity = quantity;
        this.listStatus = listStatus;
    }

    public Status getActualStatus() {
        return listStatus.getLast().getStatus();
    }

    public List<StatusOrder> addNextSTapeStatus() {
        Status statsActual = getActualStatus();
        switch (statsActual) {
            case CREATE -> {
                if (dish.getAvalaibleQuantity() >= quantity) {
                    listStatus.add(new StatusOrder(Status.CONFIRMED, LocalDateTime.now()));
                }
                throw new RuntimeException("Insufficient quantity");
            }
            case CONFIRMED -> listStatus.add(new StatusOrder(Status.IN_PREPARATION, LocalDateTime.now()));
            case IN_PREPARATION -> listStatus.add(new StatusOrder(Status.FINISHED, LocalDateTime.now()));
            case FINISHED -> listStatus.add(new StatusOrder(Status.SERVE, LocalDateTime.now()));
            case SERVE -> throw new RuntimeException("Dish already served");
        }

        DatabaseConnection databaseConnection = new DatabaseConnection();
        StatusDishOrderCrudOperations crudStatus = new StatusDishOrderCrudOperations(databaseConnection);

        for (StatusOrder status : listStatus) {
            crudStatus.createStatus(this.getIdDishOrder(), status);
        }

        return listStatus;
    }

    public double getAmountDish() {
        return dish.getUnitPrice() * quantity;
    }

    public LocalTime getDuration() {
        Optional<LocalDateTime> startOpt = listStatus.stream()
                .filter(statusOrder -> statusOrder.getStatus() == Status.IN_PREPARATION)
                .map(StatusOrder::getDateStatus)
                .findFirst();

        Optional<LocalDateTime> endOpt = listStatus.stream()
                .filter(statusOrder -> statusOrder.getStatus() == Status.FINISHED)
                .map(StatusOrder::getDateStatus)
                .reduce((first, second) -> second);

        if (startOpt.isEmpty() || endOpt.isEmpty()) {
            throw new RuntimeException("Date time null");
        }

        Duration duration = Duration.between(startOpt.get(), endOpt.get());
        return LocalTime.of(duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }
}
