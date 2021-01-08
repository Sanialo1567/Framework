package test;

import org.hamcrest.Matchers;
import org.hamcrest.core.Every;
import org.testng.annotations.Test;
import page.TnfFilterPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TnfFilterTest extends TestBase {

    @Test
    public void filterByPriceTest(){
        int maxPrice = 10000;
        int minPrice = 0;
        List<Integer> prices = new TnfFilterPage()
                .openPage()
                .filterByPrice(TnfFilterPage.PRICE_RANGE_0_100)
                .getAllProducts()
                .stream()
                .map(i -> i.getPrice())
                .collect(Collectors.toList());

        assertThat(prices, Every.everyItem(lessThanOrEqualTo(maxPrice)));
        assertThat(prices, Every.everyItem(greaterThanOrEqualTo(minPrice)));
    }

    @Test
    public void sortByPriceCheapToExpensiveTest(){
        int maxPrice = 10000;
        int minPrice = 0;
        List<Integer> prices = new TnfFilterPage()
                .openPage()
                .filterByPrice(TnfFilterPage.PRICE_RANGE_0_100)
                .sortByPriceCheapToExpensive()
                .getAllProducts()
                .stream()
                .map(i -> i.getPrice())
                .collect(Collectors.toList());

        List<Integer> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);

        assertThat(prices, contains(sortedPrices.toArray()));
    }


    @Test
    public void sortByPriceExpensiveToÑðóôçTest(){
        int maxPrice = 10000;
        int minPrice = 0;
        List<Integer> prices = new TnfFilterPage()
                .openPage()
                .filterByPrice(TnfFilterPage.PRICE_RANGE_0_100)
                .sortByPriceExpensiveToCheap()
                .getAllProducts()
                .stream()
                .map(i -> i.getPrice())
                .collect(Collectors.toList());

        List<Integer> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices, Collections.reverseOrder());

        assertThat(prices, contains(sortedPrices.toArray()));
    }
}
