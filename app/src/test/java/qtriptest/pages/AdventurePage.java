package qtriptest.pages;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AdventurePage {

  
    private  RemoteWebDriver driver;
    private String url;

    // @FindBy(xpath = "//select[@id='duration-select']/option[text()=' + DurationFilter + ']")
    // private WebElement durationFilter;

    // @FindBy(xpath = "//select[@id='category-select']/option[text()=' + Category_Filter + ']")
    // private WebElement categoryFilter;

    @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    private WebElement durationClear;

    @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
    private WebElement categoryClear;

    @FindBy(xpath="//input[@id='search-adventures']")
    private WebElement adventureSearch;

    @FindBy(xpath="//a[contains(@href, '../adventures/detail/?adventure=')]")
    private WebElement adventureClick;


    public AdventurePage(RemoteWebDriver driver, String CityName){
        this.driver = driver;
        this.url = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city=" + CityName;
        //AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();

    }
 

    public boolean checkAdventurePageNavigation() {
       
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.urlContains(url));
        

    }

    private boolean isElementDisplayed(WebElement element) throws TimeoutException{
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(element));
        return true;
    }

    public void selectFilters(String DurationFilter){
        System.out.println("Selecting the filter:" + DurationFilter);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement duration = wait.until(ExpectedConditions.elementToBeClickable(By.id("duration-select")));
        Select durationselect = new Select(duration);
        durationselect.selectByVisibleText(DurationFilter);
     }

     public void selectCategory(String Category_Filter){
        System.out.println("Selecting the filter:" + Category_Filter);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement category = wait.until(ExpectedConditions.elementToBeClickable(By.id("category-select")));
        Select categoryselect = new Select(category);
        categoryselect.selectByVisibleText(Category_Filter);
     }
      

     public boolean adventureSearch(String AdventureName){

        System.out.println("Searching an adventure" + AdventureName);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(adventureSearch));
        adventureSearch.sendKeys(AdventureName);
        return false;
     }

     public boolean adventureClick(String AdventureName){
        System.out.println("Clicking on adventure" + AdventureName);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(adventureClick));
        adventureClick.click();
        return false;
     }

    public void ClearFilters() throws TimeoutException{
        if(isElementDisplayed(durationClear)){
            durationClear.click();
        }
          
        if(isElementDisplayed(categoryClear)){
            categoryClear.click();
        }


    }

    public boolean verifyFilterData(String ExpectedFilteredResults) throws TimeoutException{

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement filtereddata = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("data")));
        List<WebElement> filtereditems = filtereddata.findElements(By.xpath("//div[@class='col-6 col-lg-3 mb-4']"));
        int actualfiltercount = filtereditems.size();
        System.out.println("Actual filtered data:" + actualfiltercount);
        int expectedfiltercount = Integer.parseInt(ExpectedFilteredResults);
        return actualfiltercount==expectedfiltercount;

        }
        



    public boolean Alldatadisplayed(){
        List<WebElement> allRecords = driver.findElements(By.xpath("//div[@id='data']//div"));
        for(WebElement record : allRecords){
            if(!record.isDisplayed()){
                return false;
            }
        }
         return true;
    }
}



    
   
 
