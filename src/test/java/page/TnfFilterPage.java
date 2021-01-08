package page;

import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.TestDataReader;
import util.Resolver;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TnfFilterPage extends AbstractPage{
    private final static String PAGE_URL = TestDataReader.getTestData("test.data.page.filter");
    public final static String PRICE_RANGE_0_100 = "$0 - $100";
    public final static String PRICE_RANGE_100_200 = "$100 - $200";
    public final static String PRICE_RANGE_200_300 = "$200 - $300";

    @FindBy(xpath = "//div[contains(@data-filter-name,'$$$')]")
    private WebElement priceFilter;

    @FindBy(xpath = "//span[contains(@data-selected-text,'Apply Filters')]")
    private WebElement applyFilter;

    @FindBy(xpath = "//div[contains(@class,'filter-menu__sub-menu-header row filter-sort-menu show-for-large')]")
    private WebElement sortButton;

    @FindBy(xpath = "//li[contains(@data-label,'$ - $$$')]")
    private WebElement sortFromCheapToExpensive;

    @FindBy(xpath = "//li[contains(@data-label,'$$$ - $')]")
    private WebElement sortFromExpensiveToCheap;

    private final String priceRangesPath = "//span[contains(@class,'sub-menu-options__label-text  ')]";
    private final String productsPath = "//div[contains(@class,'product-block product-block-js ')]";

    private final String productNamePath = ".//span[contains(@class,'product-block-name-wrapper')]";
    private final String productPricePath = ".//span[contains(@class,'product-block-price product-block-offer-price offer-price product-price-amount-js')]";
    private final String alternativeProductPricePath = ".//span[contains(@class,'product-block-price product-block-original-price original-price')]";

    public TnfFilterPage openPage(){
        driver.get(PAGE_URL);
        waitUntilAjaxCompleted();

        log.info("driver has changed an active page to a filter page");

        return this;
    }

    public TnfFilterPage filterByPrice(String range){
        waitUntilElementIsClickable(priceFilter).click();
        driver.findElements(By.xpath(priceRangesPath))
                .stream()
                .filter(i -> i.getText().equals(range))
                .findFirst()
                .get().click();
        waitUntilAjaxCompleted();
        waitUntilVisibilityOf(applyFilter).click();
        waitUntilAjaxCompleted();

        log.info("filtered by price");

        return this;
    }

    public TnfFilterPage sortByPriceCheapToExpensive(){
        waitUntilElementIsClickable(sortButton).click();
        waitUntilElementIsClickable(sortFromCheapToExpensive).click();
        waitUntilAjaxCompleted();

        log.info("sorted by price from cheap to expensive");

        return this;
    }

    public TnfFilterPage sortByPriceExpensiveToCheap(){
        waitUntilElementIsClickable(sortButton).click();
        waitUntilElementIsClickable(sortFromExpensiveToCheap).click();
        waitUntilAjaxCompleted();

        log.info("sorted by price from expensive to cheap");

        return this;
    }

    public List<Product> getAllProducts(){
        return driver.findElements(By.xpath(productsPath)).stream()
                .map(this::getProductFrom)
                .collect(Collectors.toList());
    }

    private Product getProductFrom(WebElement element){
        String name = element.findElement(By.xpath(productNamePath)).getText();
        String size = "5";
        int amount = 1;
        int price;

        if(element.findElements(By.xpath(productPricePath)).size() == 0){
            price = Resolver.resolvePrice(element.findElement(By.xpath(alternativeProductPricePath)).getText());
        } else {
            price = Resolver.resolvePrice(element.findElement(By.xpath(productPricePath)).getText());
        }

        return new Product(name, price, size, amount);
    }
}
