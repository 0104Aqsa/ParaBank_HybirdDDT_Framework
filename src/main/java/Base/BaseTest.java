package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void BrowserSetup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        String url = ConfigReader.getProperty("baseURL");
        driver.get(url);
        System.out.println("The page is open ");
        System.out.println(driver.getTitle());
    }

    // Start Extent Test for every method
    @BeforeSuite
    public void startReport() {
        ExtentManager.getInstance();
    }

    @AfterSuite
    public void endReport() {
        ExtentTestManager.endTest();
    }

}
