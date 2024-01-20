package org.survey.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.survey.service.user.UserService;
import jakarta.xml.ws.Endpoint;

@Configuration
@ComponentScan(basePackages = "org.survey")
public class WebServiceConfig {
  @Autowired
  UserService userService;
  @Autowired
  private Bus bus;

  @Bean
  public Endpoint endpoint() {
    Endpoint endpoint = new EndpointImpl(bus, userService);
    endpoint.publish("/users");
    return endpoint;
  }
}
