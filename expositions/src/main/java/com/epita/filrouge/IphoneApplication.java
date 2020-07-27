package com.epita.filrouge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class IphoneApplication {

    private static final Logger LOG = LoggerFactory.getLogger(IphoneApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(IphoneApplication.class, args);
        LOG.info("Application Gestion des Iphones is running!\n ");
    }
}
