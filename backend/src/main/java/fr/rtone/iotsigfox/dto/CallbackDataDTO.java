package fr.rtone.iotsigfox.dto;

/**
 * @Author: Hani
 */
public class CallbackDataDTO {

    private String device;
    private Long time;
    private boolean duplicate;
    private Double snr;
    private String station;
    private String data;
    private Double lat;
    private Double lng;
    private Double rssi;

    public CallbackDataDTO() {
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public Double getSnr() {
        return snr;
    }

    public void setSnr(Double snr) {
        this.snr = snr;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getRssi() {
        return rssi;
    }

    public void setRssi(Double rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "CallbackDataDTO{" +
                "device='" + device + '\'' +
                ", time=" + time +
                ", duplicate=" + duplicate +
                ", snr=" + snr +
                ", station='" + station + '\'' +
                ", data='" + data + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", rssi=" + rssi +
                '}';
    }
}
