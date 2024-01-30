package qtriptest.tests;

import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import qtriptest.DP;
import qtriptest.DriverSingleton;

public class testCase_01 {


    private static  RemoteWebDriver driver;
    private static SoftAssert softAssert;
 
    public static void logStatus(String type, String message, String status) {
		System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	}

    @BeforeSuite( alwaysRun = true)
	public static void Driversetup() throws MalformedURLException {
		driver = DriverSingleton.getDriver();
        softAssert = new SoftAssert();
		logStatus("driver", "Initializing driver", "Started");
	}


  
    @Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify the user login flow", priority = 1, groups ={"Login Flow"})
    public void TestCase01(String username, String password) throws InterruptedException, TimeoutException{
     
     softAssert = new SoftAssert();
     HomePage homePage = new HomePage(driver);
     softAssert.assertTrue(homePage.checkNavigation(), "Navigation of Home page is failed");
     homePage.HomePageOptions("register");
     RegisterPage registerPage = new RegisterPage(driver);
     softAssert.assertTrue(registerPage.checkRegisterPageNavigation(), "Navigation of Register page is failed");
     registerPage.registernewuser(username, password, password,true);
     Thread.sleep(10000);
     LoginPage loginPage = new LoginPage(driver);
      try{
     loginPage.performNewLoginUser(username, password);
     WebDriverWait wait = new WebDriverWait(driver, 20);
     wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/"));
     softAssert.assertTrue(homePage.checkuserloggedin(), "User is unable to login");
     homePage.HomePageOptions("logout");
     wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/"));
     softAssert.assertTrue(homePage.checkNavigation(), "User is unable to logged out");
    // softAssert.assertAll();
     }catch(UnhandledAlertException alertException){
         logStatus("exception", "Exception occurred" + alertException.getMessage(), "Failed");
         
     }

    }

    @AfterSuite(enabled = true)
	
	public static void quitDriver() throws MalformedURLException {
		DriverSingleton.quitDriver();
        logStatus("driver", "Quitting driver", "Success");
	}
}


