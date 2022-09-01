package com.opus.dapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DAppApplication.class, args);
    }

}
