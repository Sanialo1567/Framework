package service;

import lombok.extern.slf4j.Slf4j;
import model.Product;

import static util.Resolver.resolveTemplate;

@Slf4j
public class ProductCreator {
    public static final String ITEM_NAME_TEMPLATE = "test.data.%s.name";
    public static final String ITEM_SIZE_TEMPLATE = "test.data.%s.size";
    public static final String ITEM_PRICE_TEMPLATE = "test.data.%s.price";
    public static final String ITEM_AMOUNT_TEMPLATE = "test.data.%s.amount";

    public static Product withCredentialsFromProperty(String orderNumber){
        orderNumber = orderNumber.toLowerCase();
        System.out.println("ASDasdasdasdasdasd");
        log.info("SFADASDASDASDSADASDASDDSDSADASDSD");

        String itemName = resolveTemplate(ITEM_NAME_TEMPLATE, orderNumber);
        String itemSize = resolveTemplate(ITEM_SIZE_TEMPLATE, orderNumber);
        String itemPrice = resolveTemplate(ITEM_PRICE_TEMPLATE, orderNumber);
        String itemAmount = resolveTemplate(ITEM_AMOUNT_TEMPLATE, orderNumber);

        return new Product(TestDataReader.getTestData(itemName),
                Integer.parseInt(TestDataReader.getTestData(itemPrice)),
                TestDataReader.getTestData(itemSize),
                Integer.parseInt(TestDataReader.getTestData(itemAmount)));
    }
}
