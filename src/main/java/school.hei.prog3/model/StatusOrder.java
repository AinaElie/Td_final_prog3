package school.hei.prog3.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StatusOrder {
    private Status status;
    private LocalDateTime dateStatus;

    public StatusOrder(Status status, LocalDateTime dateStatus) {
        this.status = status;
        this.dateStatus = dateStatus;
    }
}
