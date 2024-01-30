
package qtriptest.pages;

import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage {
    private  RemoteWebDriver driver;
    private String url;

    @FindBy(xpath = "//tbody[@id='reservation-table']/tr/th[@scope='row']")
    private WebElement transactionID;

    @FindBy(xpath ="//tbody[@id='reservation-table']//button[@class='cancel-button']")
    private WebElement cancel;
    
    @FindBy(xpath = "//div[@id='no-reservation-banner']")
    private WebElement message;

    public HistoryPage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
}

    public void setURL(){
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.urlContains("/pages/adventures/reservations/"));
    url = driver.getCurrentUrl();

   }

   public boolean getTransactionID(){
    return transactionID.isDisplayed();
   }

   public boolean cancelButton(){

    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement cancelbutton = wait.until(ExpectedConditions.visibilityOf(cancel));
    cancelbutton.click();
    return false;
   }


   public boolean transactionIDremoved(){
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement Message = wait.until(ExpectedConditions.visibilityOf(message));
    if(Message.isDisplayed()){
        System.out.println("Transaction ID got removed and message is displayed");
        return true;
    }
    else{
        System.out.println("Transaction ID not got removed and message is displayed");
        return false;
    }
    
}

    public boolean allBookingDisplayed(){
    List<WebElement> bookeditems = driver.findElements(By.xpath("//div[@id='reservation-table-parent']//tbody//tr"));
    if(bookeditems.size()>0){
      System.out.println("All bookings are displayed on the history page");
      return true;
    }else{
      System.out.println("All bookings are not displayed on the history page");
      return false;
    }
   }

}
