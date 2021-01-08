package page;

import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import util.Resolver;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TnfCartPage extends AbstractPage {

    public static final String STANDARD_SHIP_MODE = "15154";
    public static final String EXPRESS_SHIP_MODE = "15153";
    public static final int EXPRESS_SHIP_MODE_PRICE = 1750;

    @FindBy(xpath = "//span[contains(@class,'checkout-itemNum-js')]")
    private WebElement numberOfChosenProducts;

    @FindBy(xpath = "//*[@id='amount-after-giftcards-apply-summary']")
    private WebElement orderTotalPrice;

    @FindBy(xpath = "//dd[contains(@class,'checkout-estimate-shipping-2 checkout-estimate-shipping-2-js')]")
    private WebElement orderShippingPrice;

    @FindBy(xpath = "//dd[contains(@class,'checkout-subtotal checkout-subtotal-js')]")
    private WebElement orderItemsSubtotalPrice;

    @FindBy(xpath = "//select[contains(@class,'checkout-ship-mode-id checkout-ship-mode-js wcs-select')]")
    private WebElement shipModeSelect;

    private final String totalProductPricePath = "//div[contains(@class,'item-detail-total item-detail-total-js small-6 medium-3 large-1 columns hide-for-small-only')]/p";
    private final String productAmountPath = "//select[contains(@class,'checkout-shopcart-quantity checkout-shopcart-quantity-js disable-onfocusout-validate-js wcs-select ')]";
    private final String productPricePath = "//div[contains(@class,'item-detail-price item-price-js small-6 medium-3 large-1 columns')]/p";
    private final String productNamePath = "//h3[contains(@class,'item-detail-info-name item-name-js')]";
    private final String productSizeTemplate = "//*[@id=\"body-container\"]/article/section/div[1]/section[2]/div[5]/aside[1]/div/div[%s]/div[2]/div[1]/dl/dd[2]";
    private final String productRemoveButtonsPath = "//a[contains(@class,'checkout-item-action-delete checkout-delete-item-js ')]";

    public String getProductName(int productNumber){
        return waitUntilVisibilityOf(driver.findElements(By.xpath(productNamePath)).get(productNumber))
                .getText();
    }

    public int getNumberOfProducts(){
        return Integer.parseInt(waitUntilVisibilityOf(numberOfChosenProducts).getText());
    }

    public TnfCartPage changeAmountOfProduct(int productNumber, int amount){
        Select select = new Select(driver.findElements(By.xpath(productAmountPath)).get(productNumber));
        select.selectByValue(Integer.toString(amount));
        waitUntilAjaxCompleted();

        log.info("amount of products has been changed");

        return this;
    }

    public int getProductTotalPrice(int productNumber){
        String price = waitUntilPresenceOfElement(By.xpath(
                Resolver.resolveTemplate(totalProductPricePath, Integer.toString(productNumber))))
                .getText();

        return Resolver.resolvePrice(price);
    }

    public int getProductPrice(int productNumber){
        String price = waitUntilVisibilityOf(driver.findElements(By.xpath(productPricePath)).get(productNumber))
                .getText();

        return Resolver.resolvePrice(price);
    }

    public int getProductAmount(int productNumber){
        String amount = new Select(waitUntilVisibilityOf(driver.findElements(By.xpath(productAmountPath)).get(productNumber)))
                .getAllSelectedOptions().get(0).getText();

        return Resolver.resolvePrice(amount);
    }

    public String getProductSize(int productNumber){
        int offset = 2;
        productNumber += offset;

        String size = waitUntilPresenceOfElement(By.xpath(Resolver.resolveTemplate(productSizeTemplate, productNumber)))
                .getText();

        return Resolver.resolveSize(size);
    }

    public Product getProduct(int productNumber){
        return new Product(getProductName(productNumber),
                getProductPrice(productNumber),
                getProductSize(productNumber),
                getProductAmount(productNumber));
    }

    public Product removeProduct(int productNumber){
        Product removedProduct = getAllProducts().get(productNumber);
        waitUntilVisibilityOf(driver.findElements(By.xpath(productRemoveButtonsPath)).get(productNumber)).click();
        waitUntilAjaxCompleted();

        return removedProduct;
    }

    public List<Product> getAllProducts(){
        int numberOfProducts = getNumberOfProducts();
        List<Product> products = new ArrayList<>();

        for(int i = 0; i < numberOfProducts; i++){
            products.add(getProduct(i));
        }

        return products;
    }

    public TnfCartPage changeShipMode(String value){
        new Select(waitUntilVisibilityOf(shipModeSelect)).selectByValue(value);
        waitUntilFieldIsChanged(orderShippingPrice, "0");
        waitUntilAjaxCompleted();

        log.info("ship mode has been changed");

        return this;
    }

    public int getTotalPrice(){
        return Resolver.resolvePrice(waitUntilVisibilityOf(orderTotalPrice).getText());
    }

    public int getShippingPrice(){
        return Resolver.resolvePrice(waitUntilVisibilityOf(orderShippingPrice).getText());
    }

    public int getProductsSubtotalPrice(){
        return Resolver.resolvePrice(waitUntilVisibilityOf(orderItemsSubtotalPrice).getText());
    }

}
