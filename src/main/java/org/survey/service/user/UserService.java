package org.survey.service.user;

import org.springframework.web.bind.annotation.RestController;
import org.survey.model.user.User;
import jakarta.jws.WebService;

@RestController
@WebService
public interface UserService {
  User[] findAll();

  User create(User user);

  User update(User user);

  User findByUsername(String username);

  User findById(Long id);

  User findByEmail(String email);

  boolean exists(Long id);

  void delete(Long id);

  long count();
}
