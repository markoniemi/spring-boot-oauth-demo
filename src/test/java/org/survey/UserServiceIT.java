package org.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.survey.model.user.User;
import org.survey.service.user.UserClient;
import org.survey.service.user.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.xml.ws.Service;

public class UserServiceIT extends AbstractIntegrationTestBase {
  private TestRestTemplate testRestTemplate = new TestRestTemplate();
  @Resource
  private String url;
  @Resource
  private UserClient userClient;

  public UserService getUserWsClient() throws MalformedURLException {
    URL wsdlURL = new URL("http://localhost:8082/api/soap/users?wsdl");
    QName qname = new QName("http://user.service.survey.org/", "UserService");
    Service service = Service.create(wsdlURL, qname);
    return service.getPort(UserService.class);
  }

  @Test
  public void getUsersRest() throws JsonParseException, JsonMappingException, IOException {
    ResponseEntity<String> responseString =
        testRestTemplate.getForEntity(url + "/api/rest/users", String.class);
    assertNotNull(responseString);
    List<User> users = parseResponse(responseString);
    assertNotNull(users);
    assertEquals(1, users.size());
  }

  @Test
  public void getUserRest() throws JsonParseException, JsonMappingException, IOException {
    User user = testRestTemplate.getForObject(url + "/api/rest/users?username=admin", User.class);
    assertEquals("admin", user.getUsername());
  }

  @Test
  public void getUsersFeign() throws JsonParseException, JsonMappingException, IOException {
    User[] users = userClient.findAll();
    assertNotNull(users);
    assertEquals(1, users.length);
  }

  @Test
  @Disabled
  public void getUsersWs() throws JsonParseException, JsonMappingException, IOException {
    UserService userService = getUserWsClient();
    User[] users = userService.findAll();
    assertNotNull(users);
    assertEquals(1, users.length);
  }

  private List<User> parseResponse(ResponseEntity<String> responseString)
      throws IOException, JsonParseException, JsonMappingException {
    return new ObjectMapper().readValue(responseString.getBody(),
        new TypeReference<List<User>>() {});
  }
}
