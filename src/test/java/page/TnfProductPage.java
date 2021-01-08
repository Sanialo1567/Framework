package page;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.TestDataReader;
import util.Resolver;

@Slf4j
public class TnfProductPage extends AbstractPage {
    private static final String PRODUCT_URL = TestDataReader.getTestData("test.data.first.link");

    @FindBy(id = "buttonAddToBag")
    private WebElement addToCartButton;

    @FindBy(xpath = "//li[contains(@class,'topnav-utility-item topnav-cart show-for-medium')]//a")
    private WebElement openBagButton;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/div/div/div[1]/div[1]")
    private WebElement closeAdButton;

    @FindBy(xpath = "//a[contains(@class,'product-content-form-add-to-wishlist link-button add-wishlist add-to-wishlist-js')]")
    private WebElement addFavoriteButton;

    @FindBy(xpath = "//h1[contains(@class,'product-content-info-name product-info-js')]")
    private WebElement nameField;

    private String favoriteProduct = "//span[contains(@class,'icon-favorite-filled')]";
    private String selectSizeButtonTemplate = "//div[contains(@class,'product-content-form-attr-container attr-container attr-container-js swatches ')]/button[contains(@data-attribute-value,'%s')]";

    public TnfProductPage openPage() {
        driver.get(PRODUCT_URL);

        log.info("driver has changed an active page to a product page");

        return this;
    }

    public TnfProductPage closePopUps() {
        waitUntilElementIsClickable(closeAdButton).click();

        return this;
    }

    public TnfProductPage addToCart() {
        waitUntilElementIsClickable(addToCartButton).click();
        waitUntilAjaxCompleted();

        log.info("product has been added to a cart");

        return this;
    }

    public TnfCartPage openCart(){
        waitUntilAjaxCompleted();
        waitUntilElementIsClickable(openBagButton).click();

        log.info("driver is changing his active page to a cart page");

        return new TnfCartPage();
    }

    public TnfProductPage addToFavorite(){
        waitUntilVisibilityOf(addFavoriteButton).click();

        log.info("product have been added to favorite");

        return this;
    }

    public boolean isFavorite(){
        return driver.findElements(By.xpath(favoriteProduct)).size() == 0;
    }

    public TnfProductPage setSize(String size){
        waitUntilElementIsClickable(By.xpath(Resolver.resolveTemplate(selectSizeButtonTemplate, size)))
                .click();

        log.info("product size has been changed");

        return this;
    }

    public String getProductName(){
        return Resolver.removeAllSpecialChars(waitUntilVisibilityOf(nameField).getText());
    }
}
