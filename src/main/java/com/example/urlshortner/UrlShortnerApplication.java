package com.example.urlshortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * UrlShortnerApplication
 *
 * @author  Prasanth Omanakuttan
 * @version 1.0
 * @since   2023-09-27
 */
@SpringBootApplication
public class UrlShortnerApplication {
    private static final Logger LOGGER = Logger.getLogger(UrlShortnerApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(UrlShortnerApplication.class, args);
    }

    @Bean
    TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            LOGGER.log(Level.ALL,"Configuring {0} to use VirtualThreadPerTaskExecutor",protocolHandler);
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

}
