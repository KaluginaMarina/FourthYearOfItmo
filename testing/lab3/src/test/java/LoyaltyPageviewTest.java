// Generated by Selenium IDE

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class LoyaltyPageviewTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        String driverType = System.getenv("DRIVER");
        if(driverType.equals("CHROME")) {
            driver = new ChromeDriver();
        }else if(driverType.equals("FIREFOX")){
            driver = new FirefoxDriver();
        }
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void loyaltyPageview() {
        driver.get("https://dfiles.eu/");
        vars.put("window_handle", driver.getWindowHandle());
        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, \'flag_ru\')]"));
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, \'flag_ru\')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, \'active\') and contains(@class, \'flag_ru\')]")));
        }
        driver.manage().window().setSize(new Dimension(960, 1053));
        driver.findElement(By.xpath("//div[@id=\'main\']/div/ul/li[4]/a")).click();
        assertThat(driver.findElement(By.xpath("//div[@id=\'main\']/div[4]/div[2]/div/div/h1/strong")).getText(), is("Loyalty Program!"));
        driver.findElement(By.xpath("//a[contains(text(),\'U-поинты\')]")).click();
        driver.switchTo().window(vars.get("window_handle").toString());
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\'main\']/div[4]/div[2]/div/div/p[contains(text(),\'U-Points\')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id=\'main\']/div[4]/div[2]/div/div/p")).getText(), is("U-Points - are Points you get for your files (uploaded by you to Depositfiles.com earlier) being downloaded."));
        driver.findElement(By.xpath("//a[contains(text(),\'D-поинты\')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\'main\']/div[4]/div[2]/div/div/p[contains(text(),\'D-Points\')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id=\'main\']/div[4]/div[2]/div/div/p")).getText(), is("D-Points - are Points you get for downloading files"));
    }
}