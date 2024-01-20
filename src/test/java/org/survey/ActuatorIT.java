package org.survey;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import jakarta.annotation.Resource;

public class ActuatorIT extends AbstractIntegrationTestBase {
  private static final String STATUS_UP = "\"status\":\"UP\"";
  private TestRestTemplate testRestTemplate = new TestRestTemplate();
  @Resource
  private String url;

  @Test
  void test() {
    assertTrue(
        testRestTemplate.getForObject(url + "/actuator/health", String.class).contains(STATUS_UP));
    assertTrue(testRestTemplate.getForObject(url + "/actuator/health/liveness", String.class)
        .contains(STATUS_UP));
    assertTrue(testRestTemplate.getForObject(url + "/actuator/health/readiness", String.class)
        .contains(STATUS_UP));
  }
}
