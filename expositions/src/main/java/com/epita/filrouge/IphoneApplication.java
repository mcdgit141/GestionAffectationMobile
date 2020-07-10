package com.epita.filrouge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.epita.filrouge")
public class IphoneApplication {

    public static void main(final String[] args) {
        SpringApplication.run(IphoneApplication.class, args);
    }
}
