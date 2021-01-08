package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TnfPersonalPage extends AbstractPage{
    @FindBy(xpath = "//span[contains(@class,'given-name firstName-js myaccount-profile-overview-first-name')]")
    private WebElement firstnameField;

    @FindBy(xpath = "//span[contains(@class,'family-name lastName-js myaccount-profile-overview-last-name')]")
    private WebElement lastnameField;

    @FindBy(xpath = "//span[contains(@class,'email email1-js myaccount-profile-overview-email')]")
    private WebElement emailField;

    public String getFirstname(){
        return waitUntilVisibilityOf(firstnameField).getText();
    }

    public String getLastname(){
        return waitUntilVisibilityOf(lastnameField).getText();
    }

    public String getEmail(){
        return waitUntilVisibilityOf(emailField).getText();
    }
}
