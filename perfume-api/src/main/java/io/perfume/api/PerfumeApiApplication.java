package io.perfume.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PerfumeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerfumeApiApplication.class, args);
    }
}
