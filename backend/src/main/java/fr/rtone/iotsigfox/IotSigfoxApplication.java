package fr.rtone.iotsigfox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@SpringBootApplication
@EnableScheduling
public class IotSigfoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotSigfoxApplication.class, args);
	}
}
