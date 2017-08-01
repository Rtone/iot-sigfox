package fr.rtone.iotsigfox.callback;


import fr.rtone.iotsigfox.domain.Device;
import fr.rtone.iotsigfox.domain.Measure;
import fr.rtone.iotsigfox.dto.CallbackDataDTO;
import fr.rtone.iotsigfox.dto.SigfoxDataDTO;
import fr.rtone.iotsigfox.repository.DeviceRepository;
import fr.rtone.iotsigfox.repository.MeasureRepository;
import fr.rtone.iotsigfox.util.Converter;
import fr.rtone.iotsigfox.websocket.SigfoxDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author: Hani
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CallbackEndpoint {

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SigfoxDataHandler dataHandler;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Works successfully");
    }

    @GetMapping("/messages")
    public List<SigfoxDataDTO> getData() {
        List<SigfoxDataDTO> sigfoxData = measureRepository.findAll(new Sort(Sort.Direction.DESC, "timestamp"))
                .stream()
                .filter(distinctByKey(Measure::getDevice))
                .map(SigfoxDataDTO::new)
                .collect(Collectors.toList());

        return sigfoxData;
    }


    @GetMapping("/device/messages")
    public List<SigfoxDataDTO> getDeviceData() {

        List<SigfoxDataDTO> sigfoxData = measureRepository.findAll(new Sort(Sort.Direction.DESC, "timestamp"))
                .stream()
                .filter(distinctByKey(Measure::getDevice))
                .map(SigfoxDataDTO::new)
                .collect(Collectors.toList());

        dataHandler.pushSigfoxData(sigfoxData);

        return sigfoxData;
    }


    @PostMapping("/webhook")
    public String uplinkEndpoint(CallbackDataDTO callbackData) {

        Measure measure = new Measure();

        // find device
        Device device = deviceRepository.findOne(callbackData.getDevice());

        // no device, no chocolate !
        if (device == null) {
            return null;
        }

        Long value = Long.valueOf(callbackData.getData());

        measure.setDevice(device);
        measure.setTimestamp(callbackData.getTime());
        measure.setMeasure(value);

        measureRepository.save(measure);
        measureRepository.flush();

        // push data through websocket connection
        List<SigfoxDataDTO> sigfoxData = measureRepository.findAll(new Sort(Sort.Direction.DESC, "timestamp"))
                .stream()
                .filter(distinctByKey(Measure::getDevice))
                .map(SigfoxDataDTO::new)
                .collect(Collectors.toList());
        dataHandler.pushSigfoxData(sigfoxData);
        Duration.between(LocalDateTime.now(),
                LocalDateTime.ofInstant(device.getLastTimeSync().toInstant(), ZoneId.systemDefault())).toDays();

        // Send downlink payload
        if (isLastUpdateBeforeWeek(device)) {
            long timestamp = ZonedDateTime.now().toEpochSecond();
            // payload is 8 bytes hex string
            String payload = Converter.longToBigEndianHex(timestamp, 16);
            return payload;
        }

        return null;
    }

    private boolean isLastUpdateBeforeWeek(Device device) {
        long days = Duration.between(LocalDateTime.now(),
                LocalDateTime.ofInstant(device.getLastTimeSync().toInstant(), ZoneId.systemDefault())).toDays();
        if (days > 7) {
            return true;
        }
        return false;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
