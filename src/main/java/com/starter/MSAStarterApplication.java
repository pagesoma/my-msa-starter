package com.starter;

import com.starter.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class MSAStarterApplication {

  public static void main(String[] args) {
    SpringApplication.run(MSAStarterApplication.class, args);
  }

}
