package fr.rtone.iotsigfox.repository;

import fr.rtone.iotsigfox.domain.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Hani
 */
public interface MeasureRepository extends JpaRepository<Measure, Long> {

}
