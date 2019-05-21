package ru.antonov.smartfridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
public class SmartFridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartFridgeApplication.class, args);
    }

}
