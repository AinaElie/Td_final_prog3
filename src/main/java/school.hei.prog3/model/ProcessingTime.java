package school.hei.prog3.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProcessingTime {
    private List<LocalTime> times;
    private String duration;

    public ProcessingTime(List<LocalTime> times, String duration) {
        this.times = times;
        this.duration = duration;
    }

    public List<Double> average() {
        return List.of(switch (duration.toLowerCase()) {
            case "seconde" -> times.stream().mapToInt(LocalTime::toSecondOfDay).average().orElse(0);
            case "minute" -> times.stream().mapToInt(t -> t.getHour() * 60 + t.getMinute()).average().orElse(0);
            case "heure" -> times.stream().mapToInt(LocalTime::getHour).average().orElse(0);
            default -> throw new RuntimeException("Invalid duration: " + duration);
        });
    }

    public List<Double> minus() {
        return List.of(switch (duration.toLowerCase()) {
            case "seconde" -> (double) times.stream().mapToInt(LocalTime::toSecondOfDay).min().orElse(0);
            case "minute" -> (double) times.stream().mapToInt(t -> t.getHour() * 60 + t.getMinute()).min().orElse(0);
            case "heure" -> (double) times.stream().mapToInt(LocalTime::getHour).min().orElse(0);
            default -> throw new RuntimeException("Invalid duration: " + duration);
        });
    }

    public List<Double> max() {
        return List.of(switch (duration.toLowerCase()) {
            case "seconde" -> (double) times.stream().mapToInt(LocalTime::toSecondOfDay).max().orElse(0);
            case "minute" -> (double) times.stream().mapToInt(t -> t.getHour() * 60 + t.getMinute()).max().orElse(0);
            case "heure" -> (double) times.stream().mapToInt(LocalTime::getHour).max().orElse(0);
            default -> throw new RuntimeException("Invalid duration: " + duration);
        });
    }

    public ProcessingTimeMapper toDto(List<Double> values) {
        double value = values.isEmpty() ? 0 : values.get(0);
        return new ProcessingTimeMapper(duration, value);
    }
}
