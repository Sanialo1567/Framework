package test;

import model.Product;
import org.testng.annotations.Test;
import page.TnfCartPage;
import page.TnfProductPage;
import service.ProductCreator;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TnfCartTest extends TestBase {

    @Test
    public void addToCartTest(){
        Product expectedProduct = ProductCreator.withCredentialsFromProperty("first");

        Product product = new TnfProductPage()
                .openPage()
                .closePopUps()
                .setSize(expectedProduct.getSize())
                .addToCart()
                .openCart()
                .getProduct(0);

        assertThat(product, equalTo(expectedProduct));
    }

    @Test
    public void addManyProductsToCartTest(){
        Product expectedFirstProduct = ProductCreator.withCredentialsFromProperty("first");
        Product expectedSecondProduct = ProductCreator.withCredentialsFromProperty("second");

        List<Product> products = new TnfProductPage()
                .openPage()
                .closePopUps()
                .setSize(expectedFirstProduct.getSize())
                .addToCart()
                .search(expectedSecondProduct.getName())
                .setSize(expectedSecondProduct.getSize())
                .addToCart()
                .openCart()
                .getAllProducts();

        assertThat(products, containsInAnyOrder(expectedFirstProduct, expectedSecondProduct));
    }

    @Test
    public void removeProductFromCartTest(){
        Product expectedFirstProduct = ProductCreator.withCredentialsFromProperty("first");
        Product expectedSecondProduct = ProductCreator.withCredentialsFromProperty("second");

        TnfCartPage cart = new TnfProductPage()
                .openPage()
                .closePopUps()
                .setSize(expectedFirstProduct.getSize())
                .addToCart()
                .search(expectedSecondProduct.getName())
                .setSize(expectedSecondProduct.getSize())
                .addToCart()
                .openCart();

        Product removedProduct = cart.removeProduct(1);
        List<Product> products = cart.getAllProducts();

        assertThat(products, contains(expectedFirstProduct));
        assertThat(products, not(contains(expectedSecondProduct)));
        assertThat(removedProduct, equalTo(expectedSecondProduct));
    }

    @Test
    public void changeAmountOfProductTest(){
        int newAmount = 3;
        Product expectedProduct = ProductCreator.withCredentialsFromProperty("first");

        TnfCartPage cart = new TnfProductPage()
                .openPage()
                .closePopUps()
                .setSize(expectedProduct.getSize())
                .addToCart()
                .openCart()
                .changeAmountOfProduct(0, newAmount);

        Product product = cart.getProduct(0);
        int totalPrice = cart.getProductTotalPrice(0);

        assertThat(product.getPrice() * newAmount, equalTo(totalPrice));
    }

    @Test
    public void getProductsTotalPriceTest(){
        Product expectedFirstProduct = ProductCreator.withCredentialsFromProperty("first");
        Product expectedSecondProduct = ProductCreator.withCredentialsFromProperty("second");

        int totalProductsPrice = new TnfProductPage()
                .openPage()
                .closePopUps()
                .setSize(expectedFirstProduct.getSize())
                .addToCart()
                .search(expectedSecondProduct.getName())
                .setSize(expectedSecondProduct.getSize())
                .addToCart()
                .openCart()
                .getProductsSubtotalPrice();

        int calculatedPrice = expectedFirstProduct.calculateTotalProductPrice()
                + expectedSecondProduct.calculateTotalProductPrice();

        assertThat(totalProductsPrice, equalTo(calculatedPrice));
    }

    @Test
    public void changeShippingModePriceTest(){
        Product expectedFirstProduct = ProductCreator.withCredentialsFromProperty("first");
        Product expectedSecondProduct = ProductCreator.withCredentialsFromProperty("second");

        int totalPrice = new TnfProductPage()
                .openPage()
                .closePopUps()
                .setSize(expectedFirstProduct.getSize())
                .addToCart()
                .search(expectedSecondProduct.getName())
                .setSize(expectedSecondProduct.getSize())
                .addToCart()
                .openCart()
                .changeShipMode(TnfCartPage.EXPRESS_SHIP_MODE)
                .getTotalPrice();

        int calculatedPrice = expectedFirstProduct.calculateTotalProductPrice()
                + expectedSecondProduct.calculateTotalProductPrice();

        assertThat(calculatedPrice + TnfCartPage.EXPRESS_SHIP_MODE_PRICE, equalTo(totalPrice));
    }

    @Test
    public void addToFavoriteTest(){
        Product product = ProductCreator.withCredentialsFromProperty("first");

        boolean canAdd = new TnfProductPage()
                .openPage()
                .closePopUps()
                .setSize(product.getSize())
                .addToFavorite()
                .isFavorite();

        assertThat(canAdd, equalTo(true));
    }
}
