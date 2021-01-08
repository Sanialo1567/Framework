package test;

import model.Product;
import org.testng.annotations.Test;
import page.TnfHomePage;
import service.ProductCreator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TnfHomepageTest extends TestBase{

    @Test
    void searchTest(){
        Product product = ProductCreator.withCredentialsFromProperty("first");

        String name = new TnfHomePage()
                .openPage()
                .search(product.getName())
                .getProductName();

        assertThat(name, equalTo(product.getName()));
    }
}
