package fr.rtone.iotsigfox.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: Hani
 */
@Entity
public class Measure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    private Device device;
    private Long timestamp;
    private Long measure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getMeasure() {
        return measure;
    }

    public void setMeasure(Long measure) {
        this.measure = measure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Measure measure1 = (Measure) o;

        if (!id.equals(measure1.id)) return false;
        if (!device.equals(measure1.device)) return false;
        if (!timestamp.equals(measure1.timestamp)) return false;
        return measure.equals(measure1.measure);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + device.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + measure.hashCode();
        return result;
    }
}
