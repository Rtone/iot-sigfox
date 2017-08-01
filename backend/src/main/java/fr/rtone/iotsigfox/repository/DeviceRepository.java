package fr.rtone.iotsigfox.repository;

import fr.rtone.iotsigfox.domain.Device;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author: Hani
 */
public interface DeviceRepository extends CrudRepository<Device, String> {

}
