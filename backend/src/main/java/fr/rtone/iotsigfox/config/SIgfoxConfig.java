package fr.rtone.iotsigfox.config;

import fr.rtone.sigfoxclient.SigfoxClient;
import fr.rtone.sigfoxclient.SigfoxClientFactory;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Hani
 */
@Configuration
public class SIgfoxConfig {

    @Value("${application.sigfox.api.username}")
    private String username;
    @Value("${application.sigfox.api.password}")
    private String password;

    @Bean
    SigfoxClient sigfoxClient() {
        return SigfoxClientFactory.create()
                .setLoggingLevel(HttpLoggingInterceptor.Level.BODY)
                .setCredentials(username, password)
                .build();
    }

}
