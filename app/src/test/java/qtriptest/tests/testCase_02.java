package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.AdventurePage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeoutException;
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



	@Test(dataProvider = "data-provider", dataProviderClass = DP.class, description = "Verify the search and filter flow", priority=2, groups={"Search and Filter flow"})
	public void TestCase02(String CityName, String Category_Filter, String DurationFilter,
		String ExpectedFilteredResults, String ExpectedUnFilteredResults)
			throws TimeoutException, InterruptedException {

		softAssert = new SoftAssert();
		HomePage homePage = new HomePage(driver);
		softAssert.assertTrue(homePage.checkNavigation(), "Navigation of Home page is failed");
		Thread.sleep(5000);
		
		softAssert.assertTrue(homePage.searchCity(CityName), "City search failed");
		if (!homePage.isAutoCompleteDisplayed(CityName)) {
			softAssert.fail("Autocomplete not displayed for city:" + CityName);
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
			

		}else{
			softAssert.fail("Autocomplete not displayed for city" + CityName);
		}
		}

		

	@AfterSuite(enabled = true)

	public static void quitDriver() throws MalformedURLException {
		DriverSingleton.quitDriver();
		logStatus("driver", "Quitting driver", "Success");
	}

}
