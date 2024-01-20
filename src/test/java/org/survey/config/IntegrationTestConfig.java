package org.survey.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SeleniumConfig.class})
@EnableFeignClients(basePackages = {"org.survey.service.user"})
public class IntegrationTestConfig {
  @Bean
  public String url() {
    return "http://localhost:8082";
  }
}
