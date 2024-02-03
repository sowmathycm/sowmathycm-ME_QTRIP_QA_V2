package qtriptest.tests;

import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeoutException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;



public class testCase_03 {

  private static RemoteWebDriver driver;
  private static ExtentReports extentReports;
  private static ExtentTest extentTest;
  private static SoftAssert softAssert;

  public static void logStatus(String type, String message, String status) {
    System.out.println(String.format("%s |  %s  |  %s | %s",
    String.valueOf(java.time.LocalDateTime.now()), type, message, status));
  }

  @BeforeSuite(alwaysRun = true)
  public static void Driversetup() throws MalformedURLException {
    driver = DriverSingleton.getDriver();
    extentReports = ReportSingleton.getInstance();
    softAssert = new SoftAssert();
    logStatus("driver", "Initializing driver", "Started");
  }

  @Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify the booking and cancellation flow", priority=3, groups={"Booking and Cancellation Flow"})
  public void TestCase03(String username, String password, String CityName, String AdventureName,
      String GuestName, String Date, String count) throws InterruptedException, TimeoutException, IOException {
    
    extentTest = extentReports.startTest("Booking and Cancellation Flow Test");
    softAssert = new SoftAssert();
    HomePage homePage = new HomePage(driver);
    softAssert.assertTrue(homePage.checkNavigation(), "Navigation of Home page is failed");
    homePage.HomePageOptions("register");
    RegisterPage registerPage = new RegisterPage(driver);
    softAssert.assertTrue(registerPage.checkRegisterPageNavigation(),
        "Navigation of Register page is failed");
    registerPage.registernewuser(username, password, password, true);
    Thread.sleep(10000);
    LoginPage loginPage = new LoginPage(driver);

    loginPage.performNewLoginUser(username, password);
    WebDriverWait wait = new WebDriverWait(driver, 20);
    wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/"));
    softAssert.assertTrue(homePage.checkuserloggedin(), "User is unable to login");
     
     
    Thread.sleep(5000);
    WebElement autoCompleteElement = homePage.searchCity(CityName);
    softAssert.assertNotNull(autoCompleteElement, "City search failed");
    if (autoCompleteElement!=null) {
        softAssert.assertTrue(autoCompleteElement.isDisplayed(),"Autocomplete not displayed for city:" + CityName);
    }
    String CityNamelower = CityName.toLowerCase();

    softAssert.assertTrue(homePage.selectCity(CityName),
        "Failed to select city from the autocomplete");

    String expectedURL =
        "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city=" + CityNamelower;
    // System.out.println("Expected URL:" + expectedURL);
    wait.until(ExpectedConditions.urlContains(expectedURL));
    String actualURL = driver.getCurrentUrl();
    // System.out.println("Actual URL:" + actualURL);
    softAssert.assertTrue(actualURL.toLowerCase().contains(expectedURL.toLowerCase()),
        "Navigation to the adventure page failed");

    Thread.sleep(2000);
    AdventurePage adventurePage = new AdventurePage(driver, CityName);
    Thread.sleep(2000);
    softAssert.assertTrue(adventurePage.adventureSearch(AdventureName),	"Searching the adventure failed");
    Thread.sleep(2000);
    softAssert.assertTrue(adventurePage.adventureClick(AdventureName), "Clicking the adventure failed");
   
    AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
    adventureDetailsPage.setURL();
    Thread.sleep(5000);
    boolean isVerified = adventureDetailsPage.verifybooking();
    System.out.println("Booking verified" +isVerified);
     if(!isVerified){
       softAssert.assertTrue(adventureDetailsPage.reservation(GuestName, Date, count), "Reservation failed");
       Thread.sleep(5000);
       softAssert.assertTrue(adventureDetailsPage.verifybooking(), "Success message not displayed");
     }else{
      System.out.println("User already reserved for the adventure");
      extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(capture(driver)) + "Success message not displayed");
      
     }
    
    softAssert.assertTrue(adventureDetailsPage.historyClick(), "Success message not displayed");
    Thread.sleep(5000);
    HistoryPage historyPage = new HistoryPage(driver);
    historyPage.setURL();
    softAssert.assertTrue(historyPage.getTransactionID(), "Transaction ID not displayed");
    Thread.sleep(2000);
    softAssert.assertTrue(historyPage.cancelButton(),"Cancel button clicking failed");
    Thread.sleep(2000);
    softAssert.assertTrue(historyPage.transactionIDremoved(), "Transaction ID not removed");
    homePage.HomePageOptions("logout");
    wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/"));
    softAssert.assertTrue(homePage.checkNavigation(), "User is unable to logged out");
    extentTest.log(LogStatus.PASS, "Booking and Cancellation Test Passed");

  }

  public static String capture(RemoteWebDriver driver) throws IOException{
    //TODO: Add all the components here
  
  
     File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
  
     File Dest = new File(System.getProperty("user.dir")+"/QKARTImages/" + System.currentTimeMillis()+ ".png");
  
      String errflpath = Dest.getAbsolutePath();
      FileUtils.copyFile(scrFile, Dest);
      return errflpath;
    }


  @AfterSuite(enabled = true)

  public static void quitDriver() throws MalformedURLException {
    DriverSingleton.quitDriver();
    ReportSingleton.endTest(extentTest);
    ReportSingleton.flush();
    logStatus("driver", "Quitting driver", "Success");
  }



}


