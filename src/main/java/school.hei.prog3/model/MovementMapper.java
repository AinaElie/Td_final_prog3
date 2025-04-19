package school.hei.prog3.model;

import org.springframework.stereotype.Component;

@Component
public class MovementMapper {
    public Movement mapFromResult(String stringValue) {
        return switch (stringValue) {
            case "enter" -> Movement.enter;
            case "exit " -> Movement.exit;
            default -> null;
        };
//        List<Movement> movementList = Arrays.stream(Movement.values()).toList();
//        return movementList.stream().filter(movement -> stringValue.equals(movement.toString())).findFirst().orElse(null);
    }
}
