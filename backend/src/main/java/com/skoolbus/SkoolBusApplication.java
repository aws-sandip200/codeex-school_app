package com.skoolbus;

import org.springframework.boot.SpringApplication;
import com.skoolbus.config.DeviceLocationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties(DeviceLocationProperties.class)
@SpringBootApplication
public class SkoolBusApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkoolBusApplication.class, args);
    }
}
