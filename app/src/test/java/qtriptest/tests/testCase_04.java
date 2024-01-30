package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import java.net.MalformedURLException;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;


public class testCase_04 {
    private static RemoteWebDriver driver;
    private static SoftAssert softAssert;

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s |  %s  |  %s | %s",
        String.valueOf(java.time.LocalDateTime.now()), type, message, status));
      }


   @BeforeSuite(alwaysRun = true)
   public static void Driversetup() throws MalformedURLException {
    driver = DriverSingleton.getDriver();
    softAssert = new SoftAssert();
    logStatus("driver", "Initializing driver", "Started");
  }

   @Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify the reliability flow", priority = 4, groups={"Reliability Flow"})
   public void TestCase04(String username, String password, String dataset1, String dataset2, String dataset3) throws InterruptedException, TimeoutException {
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
    //For Dataset1
    String[] DS = dataset1.split(";");
    String CityName = DS[0];
    String AdventureName = DS[1];
    String GuestName = DS[2];
    String Date = DS[3];
    String count = DS[4];

    softAssert.assertTrue(homePage.searchCity(CityName), "City search failed");
    if (!homePage.isAutoCompleteDisplayed(CityName)) {
      softAssert.fail("Autocomplete not displayed for city:" + CityName);
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
     }
    
    
     //For Dataset2
    softAssert.assertTrue(homePage.checkNavigation(), "Navigation of Home page is failed");
    String[] DS1 = dataset2.split(";");
    String CityName1 = DS1[0];
    String AdventureName1 = DS1[1];
    String GuestName1 = DS1[2];
    String Date1 = DS1[3];
    String count1 = DS1[4];
    Thread.sleep(2000);
    softAssert.assertTrue(homePage.searchCity(CityName1), "City search failed");
    if (!homePage.isAutoCompleteDisplayed(CityName1)) {
      softAssert.fail("Autocomplete not displayed for city:" + CityName1);
    }

    String CityNamelower1= CityName1.toLowerCase();

    softAssert.assertTrue(homePage.selectCity(CityName1),
        "Failed to select city from the autocomplete");

    String expectedURL1 =
        "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city=" + CityNamelower1;
    // System.out.println("Expected URL:" + expectedURL);
    wait.until(ExpectedConditions.urlContains(expectedURL1));
    String actualURL1 = driver.getCurrentUrl();
    // System.out.println("Actual URL:" + actualURL);
    softAssert.assertTrue(actualURL1.toLowerCase().contains(expectedURL1.toLowerCase()),
        "Navigation to the adventure page failed");
    Thread.sleep(2000);
    softAssert.assertTrue(adventurePage.adventureSearch(AdventureName1),	"Searching the adventure failed");
    Thread.sleep(2000);
    softAssert.assertTrue(adventurePage.adventureClick(AdventureName1), "Clicking the adventure failed");
    adventureDetailsPage.setURL();
    Thread.sleep(5000);
    boolean isVerified1 = adventureDetailsPage.verifybooking();
    System.out.println("Booking verified" +isVerified1);
     if(!isVerified1){
       softAssert.assertTrue(adventureDetailsPage.reservation(GuestName1, Date1, count1), "Reservation failed");
       Thread.sleep(5000);
       softAssert.assertTrue(adventureDetailsPage.verifybooking(), "Success message not displayed");
     }else{
      System.out.println("User already reserved for the adventure");
     }
   


     //For Dataset3

     softAssert.assertTrue(homePage.checkNavigation(), "Navigation of Home page is failed");
     String[] DS2 = dataset3.split(";");
     String CityName2 = DS2[0];
     String AdventureName2 = DS2[1];
     String GuestName2 = DS2[2];
     String Date2 = DS2[3];
     String count2 = DS2[4];
     Thread.sleep(2000);
     softAssert.assertTrue(homePage.searchCity(CityName2), "City search failed");
     if (!homePage.isAutoCompleteDisplayed(CityName2)) {
       softAssert.fail("Autocomplete not displayed for city:" + CityName2);
     }
 
     String CityNamelower2= CityName2.toLowerCase();
 
     softAssert.assertTrue(homePage.selectCity(CityName2),
         "Failed to select city from the autocomplete");
 
     String expectedURL2 =
         "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city=" + CityNamelower2;
     // System.out.println("Expected URL:" + expectedURL);
     wait.until(ExpectedConditions.urlContains(expectedURL2));
     String actualURL2 = driver.getCurrentUrl();
     // System.out.println("Actual URL:" + actualURL);
     softAssert.assertTrue(actualURL1.toLowerCase().contains(expectedURL1.toLowerCase()),
         "Navigation to the adventure page failed");
     Thread.sleep(2000);
     softAssert.assertTrue(adventurePage.adventureSearch(AdventureName2),	"Searching the adventure failed");
     Thread.sleep(2000);
     softAssert.assertTrue(adventurePage.adventureClick(AdventureName2), "Clicking the adventure failed");
     adventureDetailsPage.setURL();
     Thread.sleep(5000);
     boolean isVerified2 = adventureDetailsPage.verifybooking();
     System.out.println("Booking verified" +isVerified2);
      if(!isVerified2){
        softAssert.assertTrue(adventureDetailsPage.reservation(GuestName2, Date2, count2), "Reservation failed");
        Thread.sleep(5000);
        softAssert.assertTrue(adventureDetailsPage.verifybooking(), "Success message not displayed");
      }else{
       System.out.println("User already reserved for the adventure");
      }
 
      
    softAssert.assertTrue(adventureDetailsPage.historyClick(), "History page not clicked");
    Thread.sleep(5000);
    HistoryPage historyPage = new HistoryPage(driver);
    historyPage.setURL();
    softAssert.assertTrue(historyPage.allBookingDisplayed(), "All bookings are not displayed");
    Thread.sleep(2000);
    homePage.HomePageOptions("logout");
    wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/"));
    softAssert.assertTrue(homePage.checkNavigation(), "User is unable to logged out");
    
    

  }

  @AfterSuite(enabled = true)
	
	public static void quitDriver() throws MalformedURLException {
		DriverSingleton.quitDriver();
        logStatus("driver", "Quitting driver", "Success");
	}  


}
