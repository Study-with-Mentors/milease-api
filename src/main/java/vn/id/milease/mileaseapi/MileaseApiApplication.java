package vn.id.milease.mileaseapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MileaseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MileaseApiApplication.class, args);
    }

}
