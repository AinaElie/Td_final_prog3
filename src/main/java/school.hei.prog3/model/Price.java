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
public class Price {
    private String idPrice;
    private double price;
    private LocalDateTime updateDateTime;

    public Price(String idPrice, double price, LocalDateTime updateDateTime) {
        this.idPrice = idPrice;
        this.price = price;
        this.updateDateTime = updateDateTime;
    }
}