package com.jumbochips.poml_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PomlJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PomlJpaApplication.class, args);
    }

}
