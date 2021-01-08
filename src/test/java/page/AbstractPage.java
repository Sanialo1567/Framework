package page;

import driver.DriverSingleton;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import model.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public abstract class AbstractPage extends AbstractPageHeader{
    protected final int WAIT_TIMEOUT_SECONDS = 300;

    protected WebDriver driver;

    protected AbstractPage(){
        this.driver = DriverSingleton.getInstance();
        PageFactory.initElements(driver, this);
    }

    public TnfPersonalPage login(User user){
        waitUntilAjaxCompleted();
        waitUntilElementIsClickable(loginButton).click();
        waitUntilVisibilityOf(emailField).sendKeys(user.getEmail());
        waitUntilVisibilityOf(passwordField).sendKeys(user.getPassword());
        waitUntilElementIsClickable(signinButton).click();
        waitUntilAjaxCompleted();
        waitUntilVisibilityOf(myAccountButton).click();
        waitUntilAjaxCompleted();

        log.info("Sign-in is in progress...");

        return new TnfPersonalPage();
    }

    public TnfProductPage search(String request){
        waitUntilVisibilityOf(searchField).sendKeys(request + Keys.ENTER);
        waitUntilAjaxCompleted();

        log.info("search for {}", request);

        return new TnfProductPage();
    }

    public WebElement waitUntilPresenceOfElement(By location){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(location));
    }

    public WebElement waitUntilVisibilityOf(WebElement element){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitUntilElementIsClickable(By location){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(location));
    }

    public void waitUntilElementIsClickableAndClickAvoidModalWindow(By location){
        try {
            new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.elementToBeClickable(location)).click();
        } catch (ElementClickInterceptedException exception){
            driver.navigate().refresh();
            new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.elementToBeClickable(location)).click();
        }
    }

    public WebElement waitUntilElementIsClickable(WebElement element){
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilElementIsClickableAndClickAvoidModalWindow(WebElement element){
        try {
            new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException exception){
            driver.navigate().refresh();
            new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.elementToBeClickable(element)).click();
        }
    }

    public void waitUntilFieldIsChanged(WebElement element, String startValue){
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, startValue)));
    }

    public void waitUntilAjaxCompleted(){
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(jQueryAJAXCompleted());
    }

    protected static ExpectedCondition<Boolean> jQueryAJAXCompleted(){
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return (Boolean) ((JavascriptExecutor)
                        driver).executeScript("return (window.jQuery != null) && (jQuery.active == 0);");
            }
        };
    }
}
