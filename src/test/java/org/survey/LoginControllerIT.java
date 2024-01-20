package org.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import jakarta.annotation.Resource;

public class LoginControllerIT extends AbstractIntegrationTestBase {
  @Resource
  private String url;

  @Test
  public void login() throws Exception {
    HttpHeaders headers = createHeaders();
    MultiValueMap<String, String> body = createBody("admin", "admin");
    ResponseEntity<String> entity =
        new TestRestTemplate().exchange(url + "/j_spring_security_check", HttpMethod.POST,
            new HttpEntity<>(body, headers), String.class);
    assertEquals(HttpStatus.FOUND, entity.getStatusCode());
    List<String> cookies = entity.getHeaders().get("Set-Cookie");
    assertTrue(cookies.toString().contains("JSESSIONID"));
    assertEquals(url + "/user/users", entity.getHeaders().getLocation().toString());
  }

  @Test
  @Disabled
  public void redirectToLogin() throws Exception {

    HttpHeaders headers = createHeaders();
    ResponseEntity<String> entity = new TestRestTemplate().exchange(url + "/user/users",
        HttpMethod.GET, new HttpEntity<>(headers), String.class);
    assertEquals(HttpStatus.FOUND, entity.getStatusCode());
    assertEquals(url + "/login", entity.getHeaders().getLocation().toString());
  }

  @Test
  public void failedLogin() throws Exception {
    HttpHeaders headers = createHeaders();
    MultiValueMap<String, String> body = createBody("admin", "wrong");
    ResponseEntity<String> entity =
        new TestRestTemplate().exchange(url + "/j_spring_security_check", HttpMethod.POST,
            new HttpEntity<>(body, headers), String.class);
    assertEquals(HttpStatus.FOUND, entity.getStatusCode());
    assertEquals(url + "/login?error=true", entity.getHeaders().getLocation().toString());
  }

  private MultiValueMap<String, String> createBody(String username, String password) {
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.set("j_username", username);
    form.set("j_password", password);
    return form;
  }

  private HttpHeaders createHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    return headers;
  }
}
