package org.survey;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.selenium.LoginPage;
import org.survey.selenium.UserPage;
import org.survey.selenium.UsersPage;
import org.survey.service.user.UserService;
import jakarta.annotation.Resource;

public class SpringBootWebIT extends AbstractIntegrationTestBase {
  @Resource
  protected UserService userService;
  @Value("${loginUrl:http://localhost:8082}")
  private String loginUrl;
  @Resource
  protected WebDriver webDriver;
  private LoginPage loginPage;
  private UsersPage usersPage;
  private UserPage userPage;

  @BeforeEach
  public void setUp() {
    loginPage = new LoginPage(webDriver);
    usersPage = new UsersPage(webDriver);
    userPage = new UserPage(webDriver);
  }

  @AfterEach
  public void tearDown() {
    deleteUserFromRepository("admin_user");
    deleteUserFromRepository("user_user");
    webDriver.close();
    webDriver.quit();
  }

  @Test
  public void integrationTest() throws InterruptedException {
    webDriver.get(loginUrl);
    loginPage.login("admin", "admin");
    usersPage.clickAddUser();
    userPage.validateUser();
    usersPage.clickAddUser();
    userPage.addUser("admin_user", "admin_user@test.com", "another", Role.ROLE_ADMIN);
    usersPage.assertUserRole("admin_user", "Admin");
    usersPage.clickAddUser();
    userPage.addUser("user_user", "user_user@test.com", "another", Role.ROLE_USER);
    usersPage.assertUserRole("user_user", "User");
    usersPage.deleteUser("user_user");
    usersPage.logout();
    loginPage.login("admin_user", "another");
    usersPage.editUser("admin_user");
    userPage.editUser("admin_user", "newpassword");
    usersPage.logout();
    loginPage.login("admin_user", "newpassword");
    usersPage.deleteUser("admin_user");
    usersPage.logout();
  }

  private void deleteUserFromRepository(String username) {
    User user = userService.findByUsername(username);
    if (user != null) {
      userService.delete(user.getId());
    }
  }
}
