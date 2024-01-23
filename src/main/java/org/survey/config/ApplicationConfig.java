package org.survey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JpaConfig.class, WebMvcConfig.class, WebServiceConfig.class, AuthorizationServerConfig.class})
public class ApplicationConfig {
}
