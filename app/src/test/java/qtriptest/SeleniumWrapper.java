package qtriptest;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {
    private  RemoteWebDriver driver;


public static Boolean click(WebElement elementToClick, RemoteWebDriver driver){
    if(elementToClick.isDisplayed()){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", elementToClick);
        elementToClick.click();
        return true;
    }
    else
    return false;
}




public static Boolean sendKeys(WebElement inputBox, String KeysToSend){
    inputBox.clear();
    inputBox.sendKeys(KeysToSend);
    return true;

}

public Boolean navigate(RemoteWebDriver driver, String url){
    if(!driver.getCurrentUrl().equals(url)){
        driver.get(url);
        return true;
    } else
     return false;
}

public static WebElement findElementWithRetry(RemoteWebDriver driver, By by, int retryCount){
    
   WebDriverWait wait = new WebDriverWait(driver, 10);
   for(int i=0; i<retryCount;i++){
     try{
        WebElement element = driver.findElement(by);
        if(element.isDisplayed()){
            return element;
        }
     }catch(NoSuchElementException e){
        System.out.println("Element not found");
     }
     wait.until(ExpectedConditions.visibilityOfElementLocated(by));
   }
     return null;

}




public WebElement findElementWithRetry(RemoteWebDriver driver2, By xpath) {
    return null;
}


}
