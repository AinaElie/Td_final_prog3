package school.hei.examen_prog3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProcessingTimeResponseDTO {
    @JsonProperty("dishIdentifier")
    private Long dishIdentifier;

    @JsonProperty("dishName")
    private String dishName;

    @JsonProperty("preparationDuration")
    private double preparationDuration;

    @JsonProperty("durationUnit")
    private String durationUnit;

    @JsonProperty("salesPoint")
    private String salesPoint;
}
