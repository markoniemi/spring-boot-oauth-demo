package org.survey.config;

import org.springframework.web.filter.CommonsRequestLoggingFilter;
import jakarta.servlet.http.HttpServletRequest;

public class RequestLoggingFilter extends CommonsRequestLoggingFilter {
  public RequestLoggingFilter() {
    super.setBeforeMessagePrefix("[");
    super.setIncludeQueryString(true);
    super.setIncludePayload(true);
    super.setMaxPayloadLength(64000);
  }

  @Override
  protected void beforeRequest(HttpServletRequest request, String message) {
    super.beforeRequest(request, request.getMethod() + ":" + message);
  }

  @Override
  protected void afterRequest(HttpServletRequest request, String message) {
    // suppress after request logging
  }
}
