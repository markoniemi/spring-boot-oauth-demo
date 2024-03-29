package org.survey.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class VersionController {
  @Value("${app.version:empty}")
  private String applicationVersion;

  @ModelAttribute("applicationVersion")
  public String getApplicationVersion() {
    return applicationVersion;
  }
}
