package school.hei.examen_prog3.model;

import org.springframework.stereotype.Component;

@Component
public class DurationUnitMapper {
    public DurationUnit mapFromResult(String stringValue) {
        return switch (stringValue) {
            case "SECONDS" -> DurationUnit.SECONDS;
            case "MINUTES" -> DurationUnit.MINUTES;
            case "HOUR" -> DurationUnit.HOUR;
            default -> throw new IllegalStateException("Unexpected value: " + stringValue);
        };
//        List<Movement> movementList = Arrays.stream(Movement.values()).toList();
//        return movementList.stream().filter(movement -> stringValue.equals(movement.toString())).findFirst().orElse(null);
    }
}
