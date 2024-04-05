package Configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Initialization {

    protected WebDriver driver;
    public Common com;

    @BeforeMethod(alwaysRun = true)
    public void Init() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        com = new Common(driver);
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) throws Exception {

        if (testResult.getStatus()==1) {
            com.log("PASS : " + testResult.getName() + "\n");
        }

        if (testResult.getStatus()==2) {
            com.log("Failed : " + testResult.getName() + "\n");
            }
        driver.quit();
    }




}