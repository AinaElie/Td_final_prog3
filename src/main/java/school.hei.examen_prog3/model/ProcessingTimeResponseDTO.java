package school.hei.examen_prog3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessingTimeResponseDTO {
    @JsonProperty(value = "dishIdentifier")
    private Integer dishIdentifier;

    @JsonProperty("dishName")
    private String dishName;

    @JsonProperty("preparationDuration")
    private Double preparationDuration;

    @JsonProperty("salesPoint")
    private String salesPoint;

    @JsonProperty(value = "durationUnit", defaultValue = "SECONDS")
    private String durationUnit;
}