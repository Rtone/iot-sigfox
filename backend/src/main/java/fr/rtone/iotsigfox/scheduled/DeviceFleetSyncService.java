package fr.rtone.iotsigfox.scheduled;

import fr.rtone.iotsigfox.domain.Device;
import fr.rtone.iotsigfox.repository.DeviceRepository;
import fr.rtone.sigfoxclient.SigfoxClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: Hani
 */
@Component
public class DeviceFleetSyncService {

    private final Logger logger = LoggerFactory.getLogger(DeviceFleetSyncService.class);

    private static final Integer LIST_SIZE = 1000;

    @Autowired
    private SigfoxClient sigfoxClient;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * Scheduled service (every 30 minutes) to update Device list.
     */
    @Scheduled(cron = "0 0/30 * * * *")
    public void updateDeviceList() {

        String deviceTypeId = "123456";
        sigfoxClient.getDeviceList(deviceTypeId, "", LIST_SIZE, 0)
                .subscribe(data -> {
                            data.getData().forEach(this::checkAndSaveDevice);
                        },
                        error -> logger.error("some thing goes wrong with sigfox", error));

    }

    private void checkAndSaveDevice(fr.rtone.sigfoxclient.model.Device sigfoxDevice) {
        Device device = deviceRepository.findOne(sigfoxDevice.getId());
        if (device == null) {
            device = new Device();
            device.setId(sigfoxDevice.getId());
            device.setName(sigfoxDevice.getName());
            device.setLastTimeSync(new Date());
            device.setLat(sigfoxDevice.getLat());
            device.setLng(sigfoxDevice.getLng());
        }
    }
}

