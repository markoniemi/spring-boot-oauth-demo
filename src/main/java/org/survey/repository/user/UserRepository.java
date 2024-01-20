package org.survey.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.survey.model.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findByEmail(@Param("email") String email);

  User findByUsername(@Param("username") String username);
}
