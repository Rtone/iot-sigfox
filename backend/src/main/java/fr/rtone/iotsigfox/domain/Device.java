package fr.rtone.iotsigfox.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: Hani
 */
@Entity
public class Device {

    @Id
    @NotNull
    private String id;
    @NotNull
    private String name;
    private float lat;
    private float lng;
    private Date lastTimeSync;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Date getLastTimeSync() {
        return lastTimeSync;
    }

    public void setLastTimeSync(Date lastTimeSync) {
        this.lastTimeSync = lastTimeSync;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (!id.equals(device.id)) return false;
        return name.equals(device.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
