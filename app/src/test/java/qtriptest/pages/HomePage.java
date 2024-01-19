package qtriptest.pages;

import java.rmi.Remote;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.Alert;
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
    private String url = "https://qtripdynamic-qa-frontend.vercel.app/";



    @FindBy(xpath = "//a[(text()='Register')]")
    
   private  WebElement registerButton;

    @FindBy(xpath="//a[text()='Login Here']")
    
    private WebElement loginButton;

    @FindBy(xpath="//div[@class='nav-link login register' and contains(text(), 'Logout')]")
    
    private WebElement logoutButton;

    @FindBy(xpath="//a[text()='Home']")
    
    private  WebElement homepageButton;



    public HomePage(RemoteWebDriver driver){
        this.driver = driver;
         AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
        driver.manage().window().maximize();

    }

    public boolean checkNavigation() {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
            return true;
        }
        return false;
          
    }

    private boolean isElementDisplayed(WebElement element) throws TimeoutException{
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(element));
        return true;
    }
   
    public  void HomePageOptions(String option)throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver, 30);
        
        if(option.equalsIgnoreCase("register") && registerButton!=null){
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
        }
        else if(option.equalsIgnoreCase("login") && loginButton!=null){
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        }
        else if(option.equalsIgnoreCase("logout") && logoutButton!=null){
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
        }
        else if(homepageButton!=null){
        wait.until(ExpectedConditions.elementToBeClickable(homepageButton)).click();

        }
    
    }
    


     public boolean checkuserloggedin()throws InterruptedException{
        return !driver.getCurrentUrl().contains("/login") && logoutButton!=null && logoutButton.isDisplayed();
     }
    }
