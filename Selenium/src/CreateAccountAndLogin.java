import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateAccountAndLogin {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:3000";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreateAccountAndLogin() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.linkText("create an account")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("test");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("test");
    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
    driver.findElement(By.linkText("Logout")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("test");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("test");
    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
    driver.findElement(By.linkText("Logout")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
