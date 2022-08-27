package edu.cming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Boot09Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot09Application.class, args);
    }

}
