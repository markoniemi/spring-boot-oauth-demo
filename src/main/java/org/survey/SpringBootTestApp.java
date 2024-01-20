package org.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.survey.config.ApplicationConfig;

@SpringBootApplication
public class SpringBootTestApp extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfig.class, args);
  }
}
