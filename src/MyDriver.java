import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class MyDriver {
    protected static WebDriver driver;
    protected static int location;

    public static void setUp() {
        String driverPath = "C:/Ticketmaster/ChromeDriver.exe";
        System.setProperty("webdriver.chrome.driver",driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:/Users/Admin/AppData/Local/Google/Chrome/Selenium");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);}
}