package school.hei.examen_prog3.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class BestProcessingTime {
    private Long id;
    private Instant updateAt;
    private List<BestProcessingTimeElement> bestProcessingTimes;

    public BestProcessingTime(Long id, Instant updateAt, List<BestProcessingTimeElement> bestProcessingTimes) {
        this.id = id;
        this.updateAt = updateAt;
        this.bestProcessingTimes = bestProcessingTimes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public List<BestProcessingTimeElement> getBestProcessingTimes() {
        return bestProcessingTimes;
    }

    public void setBestProcessingTimes(List<BestProcessingTimeElement> bestProcessingTimes) {
        this.bestProcessingTimes = bestProcessingTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestProcessingTime that = (BestProcessingTime) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUpdateAt(), that.getUpdateAt()) && Objects.equals(getBestProcessingTimes(), that.getBestProcessingTimes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUpdateAt(), getBestProcessingTimes());
    }

    @Override
    public String toString() {
        return "BestProcessingTime{" +
                "id=" + id +
                ", updateAt=" + updateAt +
                ", bestProcessingTimes=" + bestProcessingTimes +
                '}';
    }
}
