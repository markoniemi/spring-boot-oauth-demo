package org.survey.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.repository.user.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class DatabaseInitBean implements InitializingBean {
  @Resource
  private UserRepository userRepository;
  @Value("${initial.username:admin}")
  private String username;

  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug("Creating user {}", username);
    userRepository.save(new User(username, "admin", "email", Role.ROLE_ADMIN));
  }
}
