package page;

import model.User;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractPageHeader {

    @FindBy(xpath = "//div[contains(@class,'topnav-search-input-container')]/input[contains(@name,'searchTerm')]")
    protected WebElement searchField;

    @FindBy(xpath = "//a[contains(@class,'topnav-utility-item-link topnav-utility-item-link-icon-account')]")
    protected WebElement myAccountButton;

    @FindBy(xpath = "//a[contains(@class,'topnav-utility-item-link topnav-utility-item-link-icon-wishlist')]")
    protected WebElement loginButton;

    @FindBy(id = "signin-email")
    protected WebElement emailField;

    @FindBy(id = "signin-password")
    protected WebElement passwordField;

    @FindBy(id = "buttonsignin")
    protected WebElement signinButton;
}
