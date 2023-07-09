package io.perfume.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan()
public class PerfumeApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(PerfumeApiApplication.class, args);
  }
}
