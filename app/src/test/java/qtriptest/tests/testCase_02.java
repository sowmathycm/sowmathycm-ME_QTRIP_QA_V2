package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.AdventurePage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeoutException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_02 {

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



	@Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify the search and filter flow", priority=2, groups={"Search and Filter flow"})
	public void TestCase02(String CityName, String Category_Filter, String DurationFilter,
		String ExpectedFilteredResults, String ExpectedUnFilteredResults)
			throws TimeoutException, InterruptedException, IOException {

		extentTest = extentReports.startTest("Search and Filter Flow Test");
		softAssert = new SoftAssert();
		HomePage homePage = new HomePage(driver);
		softAssert.assertTrue(homePage.checkNavigation(), "Navigation of Home page is failed");
		Thread.sleep(5000);
		
		WebElement autoCompleteElement = homePage.searchCity(CityName);
		softAssert.assertNotNull(autoCompleteElement, "City search failed");
		if (autoCompleteElement!=null) {
			softAssert.assertTrue(autoCompleteElement.isDisplayed(),"Autocomplete not displayed for city:" + CityName);
		}
        
		String CityNamelower = CityName.toLowerCase();
		
		softAssert.assertTrue(homePage.selectCity(CityName), "Failed to select city from the autocomplete");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String expectedURL = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city=" + CityNamelower;
		// System.out.println("Expected URL:" + expectedURL);
		wait.until(ExpectedConditions.urlContains(expectedURL));
		String actualURL = driver.getCurrentUrl();
		// System.out.println("Actual URL:" + actualURL);
		softAssert.assertTrue(actualURL.toLowerCase().contains(expectedURL.toLowerCase()), "Navigation to the adventure page failed");
		Thread.sleep(2000);
		 if (!ExpectedFilteredResults.isEmpty()) {
		
			AdventurePage adventurePage = new AdventurePage(driver, CityName);
			Thread.sleep(2000);
			
			adventurePage.selectCategory(Category_Filter);
			Thread.sleep(2000);
			
			softAssert.assertTrue(adventurePage.verifyFilterData(ExpectedFilteredResults),
					"Filtered data verification failed");

		 
			adventurePage.selectFilters(DurationFilter);
			softAssert.assertTrue(adventurePage.verifyFilterData(ExpectedFilteredResults),
							"Filtered data verification failed");

			Thread.sleep(2000);
			adventurePage.ClearFilters();
			softAssert.assertTrue(adventurePage.Alldatadisplayed(), "All records not displayed");
			extentTest.log(LogStatus.PASS, "Search and Filter Test Passed");
			

		}else{
			softAssert.fail("Autocomplete not displayed for city" + CityName);
            extentTest.log(LogStatus.FAIL, "Search and Filter Test Failed");
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(capture(driver)) + "Search and Filter Test Failed, reason:");

		}
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
