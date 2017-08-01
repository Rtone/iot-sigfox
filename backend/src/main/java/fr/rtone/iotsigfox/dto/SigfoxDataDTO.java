package fr.rtone.iotsigfox.dto;

import fr.rtone.iotsigfox.domain.Measure;

/**
 * @Author: Hani
 */
public class SigfoxDataDTO {

    private String device;
    private Long timestamp;
    private Long measure;

    public SigfoxDataDTO() {
    }

    public SigfoxDataDTO(Measure measure) {
        this.device = measure.getDevice().getName();
        this.timestamp = measure.getTimestamp();
        this.measure = measure.getMeasure();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
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
    public String toString() {
        return "SigfoxDataDTO{" +
                "device='" + device + '\'' +
                ", timestamp=" + timestamp +
                ", measure=" + measure +
                '}';
    }
}
