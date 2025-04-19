package school.hei.prog3.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UnitMapper {
    public Unit mapFromResult(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        List<Unit> unitList = Arrays.stream(Unit.values()).toList();
        return unitList.stream().filter(unit -> stringValue.equals(unit.toString())).findAny().orElseThrow(() -> new IllegalArgumentException("Unknown unit: " + stringValue));
    }
}
