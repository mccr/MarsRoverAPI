package com.nasa.marsroverproblem;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarsroverproblemApplication {
    public static void main(String[] args) {
        Flyway.configure();
        SpringApplication.run(MarsroverproblemApplication.class, args);
    }
}
