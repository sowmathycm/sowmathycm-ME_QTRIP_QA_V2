package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventureDetailsPage {
  private RemoteWebDriver driver;
  private SeleniumWrapper seleniumWrapper;
  private String url;

  @FindBy(xpath = "//input[@class='form-control' and @name='name']")
  private WebElement name;

  @FindBy(xpath = "//input[@class='form-control' and @name='date']")
  private WebElement date;

  @FindBy(xpath = "//input[@class='form-control' and @name='person']")
  private WebElement Count;

  @FindBy(xpath = "//button[@class='reserve-button']")
  private WebElement reserve;

  @FindBy(xpath = "//div[@class='alert alert-success']")
  private WebElement success;

  @FindBy(xpath = "//a[strong[text()='here']] ")
  private WebElement history;

  public AdventureDetailsPage(RemoteWebDriver driver) {
    this.driver = driver;
    this.seleniumWrapper = new SeleniumWrapper();
    // AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
    PageFactory.initElements(driver, this);
    driver.manage().window().maximize();
  }

  public void setURL() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.urlContains("/pages/adventures/detail/"));
    url = driver.getCurrentUrl();

  }

  public boolean reservation(String GuestName, String Date, String count){

    System.out.println("Reserving the adventure");
    seleniumWrapper.sendKeys(name, GuestName);
    seleniumWrapper.sendKeys(date, Date);
    seleniumWrapper.sendKeys(Count, count);
    seleniumWrapper.click(reserve, driver);
    return true;

    
   }

  public boolean verifybooking() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    try{
      WebElement successmessage = wait.until(ExpectedConditions.visibilityOf(success));
      if (successmessage.isDisplayed()) {
      System.out.println("Adventure booking was successfull");
      return true;

    }
    
      } catch(TimeoutException e){
       System.out.println("Adventure booking was not successfull");
     
    }
       return false;

  }

  public boolean historyClick() {

    System.out.println("Clicking on history link");
    seleniumWrapper.click(history,driver);
    return true;
 }

   
}
