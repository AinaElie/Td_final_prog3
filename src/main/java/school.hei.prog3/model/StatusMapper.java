package school.hei.prog3.model;

import java.util.Arrays;
import java.util.List;

public class StatusMapper {
    public Status mapFromResult(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        List<Status> statusList = Arrays.stream(Status.values()).toList();
        return statusList.stream().filter(status -> stringValue.equals(status.toString())).findAny().orElseThrow(() -> new IllegalArgumentException("Unknown status: " + stringValue));
    }
}
