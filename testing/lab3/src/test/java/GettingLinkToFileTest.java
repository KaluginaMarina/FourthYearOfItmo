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

public class GettingLinkToFileTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void gettingLinkToFile() {
        driver.get("https://dfiles.eu/");
        driver.manage().window().setSize(new Dimension(1920, 1053));
        driver.findElement(By.xpath("//div[@id=\'main\']/div/div/a")).click();
        driver.findElement(By.xpath("//input[@name=\'login\']")).sendKeys("gardemarrina");
        driver.findElement(By.xpath("//form[@id=\'login_frm\']/table/tbody/tr[5]/td")).click();
        driver.findElement(By.xpath("//input[@name=\'password\']")).sendKeys("123456");
        driver.findElement(By.xpath("//form[@id=\'login_frm\']/table/tbody/tr[5]/td")).click();
        driver.findElement(By.xpath("//input[@id=\'login_btn\']")).click();
        driver.findElement(By.xpath("//div[@id=\'main\']/div/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//table[@id=\'tbl_filelist\']/tbody/tr/td[2]/div/span")).click();
        driver.findElement(By.xpath("//div[@id=\'df_share\']/div[2]/div/a/span")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'depositbox\']/div/div[2]/div/textarea"));
            assert (elements.size() > 0);
        }
        driver.findElement(By.xpath("//a[contains(text(),\'Расширенный список\')]")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'depositbox\']/div/div[2]/div/textarea"));
            assert (elements.size() > 0);
        }
        driver.findElement(By.xpath("//a[contains(text(),\'Форум\')]")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'depositbox\']/div/div[2]/div/textarea"));
            assert (elements.size() > 0);
        }
        driver.findElement(By.xpath("//a[contains(text(),\'Блог\')]")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'depositbox\']/div/div[2]/div/textarea"));
            assert (elements.size() > 0);
        }
        driver.findElement(By.xpath("//div[@id=\'depositbox\']/div/div/a")).click();
        driver.findElement(By.xpath("//div[@id=\'main\']/div/div[2]/a[2]")).click();
        driver.findElement(By.xpath("//a[contains(text(),\'Выход\')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id=\'main\']/div/div/a/strong")));
        }
        driver.close();
    }
}