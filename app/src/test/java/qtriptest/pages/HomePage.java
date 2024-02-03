package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.rmi.Remote;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private  RemoteWebDriver driver;
    private SeleniumWrapper seleniumWrapper;
    private String url = "https://qtripdynamic-qa-frontend.vercel.app/";



    @FindBy(xpath = "//a[(text()='Register')]")
    
   private  WebElement registerButton;

    @FindBy(xpath="//a[text()='Login Here']")
    
    private WebElement loginButton;

    @FindBy(xpath="//div[@class='nav-link login register' and contains(text(), 'Logout')]")
    
    private WebElement logoutButton;

    @FindBy(xpath="//a[text()='Home']")
    
    private  WebElement homepageButton;

    @FindBy(id = "autocomplete")
    private WebElement searchbox;

    

    public HomePage(RemoteWebDriver driver){
        this.driver = driver;
        this.seleniumWrapper = new SeleniumWrapper();
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
        driver.manage().window().maximize();

    }

    public boolean checkNavigation() {
        return seleniumWrapper.navigate(driver,url);
    }

  
   
    public  void HomePageOptions(String option)throws InterruptedException{
       if(option.equalsIgnoreCase("register") && registerButton!=null){
        seleniumWrapper.click(registerButton, driver);
        }
        else if(option.equalsIgnoreCase("login") && loginButton!=null){
        seleniumWrapper.click(loginButton,driver);
        }
        else if(option.equalsIgnoreCase("logout") && logoutButton!=null){
        seleniumWrapper.click(logoutButton,driver);
        }
        else if(homepageButton!=null){
        seleniumWrapper.click(homepageButton,driver);

        }
    
    }
    


     public boolean checkuserloggedin()throws InterruptedException{
        return !driver.getCurrentUrl().contains("/login") && logoutButton!=null && logoutButton.isDisplayed();
     }
    
     

     public WebElement searchCity(String CityName){

      try{
        enterCity(CityName);
        return isAutoCompleteDisplayed(CityName);

      }catch(Exception e){
        System.out.println("Error during city search" +e.getMessage());
        return null;
      }
     }


     public boolean selectCity(String CityName) throws InterruptedException{

          Thread.sleep(2000);
           try{
         
            WebElement autoComplete = SeleniumWrapper.findElementWithRetry(driver, By.xpath(String.format("//ul[@id='results']//a[@id='%s']//li", CityName.toLowerCase())), 30);
           
            seleniumWrapper.click(autoComplete,driver);
            // System.out.println("City selected from autocomplete" +CityName);
            return true;
            
       }catch(Exception e){
        System.out.println("Error selecting city from autocomplete" + e.getMessage());
        return false;
       }

 }
     public void enterCity(String CityName) throws InterruptedException{
        seleniumWrapper.sendKeys(searchbox, CityName);
        Thread.sleep(3000);
        
     }

     public WebElement isNoRecordsDisplayed() throws TimeoutException {
        
       return seleniumWrapper.findElementWithRetry(driver, By.xpath("//ul[@id='results']//h5[text()='No City found']"), 30);

        
 } 

    public WebElement isAutoCompleteDisplayed(String CityName) throws TimeoutException, InterruptedException{
        try{
            return SeleniumWrapper.findElementWithRetry(driver, By.xpath(String.format("//ul[@id='results']//a[@id='%s']//li", CityName.toLowerCase())), 30);
        }catch(Exception e){
            System.out.println("Error checking the autocomplete display:" +e.getMessage());
            return null;
        }
 } 
}

     


