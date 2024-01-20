package org.survey.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public abstract class AbstractPage {
  protected WebDriver webDriver;

  public AbstractPage(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  public void logout() {
    click(By.id("logout"));
    assertTitle("Login");
  }

  public void users() {
    click(By.id("menu-users"));
    assertTitle("Users");
  }

  public void polls() {
    click(By.id("menu-polls"));
    assertTitle("Polls");
  }

  public void files() {
    click(By.id("menu-files"));
    assertTitle("Files");
  }

  protected void setText(By by, String value) {
    webDriver.findElement(by).clear();
    webDriver.findElement(by).sendKeys(value);
  }

  protected void click(By by) {
    webDriver.findElement(by).click();
  }

  protected void assertTitle(String title) {
    assertEquals(title, webDriver.getTitle(), webDriver.getPageSource());
  }

  protected void selectByValue(By by, String value) {
    new Select(webDriver.findElement(by)).selectByValue(value);
  }
}
