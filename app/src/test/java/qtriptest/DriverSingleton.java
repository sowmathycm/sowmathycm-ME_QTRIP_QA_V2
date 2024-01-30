package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

public class DriverSingleton {

    private static RemoteWebDriver driver;



public static RemoteWebDriver getDriver() throws MalformedURLException{

    if(driver==null){
        createDriver();
    }
     return driver;
}

public static void logStatus(String type, String message, String status) {
    System.out.println(String.format("%s |  %s  |  %s | %s",
            String.valueOf(java.time.LocalDateTime.now()), type, message, status));
}


public static void createDriver() throws MalformedURLException {
    
    final DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setBrowserName(BrowserType.CHROME);
    driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
    logStatus("driver", "Initializing driver", "Success");
}

public static void quitDriver() throws MalformedURLException{
    if(driver!=null){
        driver.quit();
        driver = null;
    }

}


}