package qtriptest.pages;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private  RemoteWebDriver driver;
    private String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";
    private final static String LOGIN_PAGE_IDENTIFIER = "/pages/login/";

  


    @FindBy(name= "email")
    
    private WebElement emailtextbox;

    @FindBy(name = "password")
    
     private WebElement passwordtextbox;

    @FindBy(xpath="//button[text()='Login to QTrip']")
    
     private WebElement loginButton;

    
    public LoginPage(RemoteWebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
        driver.manage().window().maximize();

    }
    
    public boolean checkLoginPageNavigation() {
       
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlContains(url));
        return driver!=null && emailtextbox!=null && passwordtextbox!=null && loginButton!=null;

    }
   public  void performNewLoginUser(String username, String password)throws InterruptedException{
     
    if(username ==null || username.isEmpty() || password == null || password.isEmpty()){
        username = RegisterPage.USER_EMAIL;
    }
    try{
    performExistingUserLogin(username, password);
   }catch(UnhandledAlertException e){
     driver.switchTo().alert().dismiss();
   }
}


   public  void performExistingUserLogin(String username, String password)throws InterruptedException{
     
    WebDriverWait wait = new WebDriverWait(driver, 30);
    WebElement Email = wait.until(ExpectedConditions.visibilityOf(emailtextbox));
    Email.clear();
    Email.sendKeys(username);
    passwordtextbox.sendKeys(password);
    loginButton.click();

   }

   public void performLogin(String username, String password, boolean isUserDynamic)throws InterruptedException{
    if(isUserDynamic){
        username = RegisterPage.USER_EMAIL;
        performExistingUserLogin(username, password);
    }
   }
}
