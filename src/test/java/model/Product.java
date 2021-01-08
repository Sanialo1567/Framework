package model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import util.Resolver;

import java.nio.charset.StandardCharsets;

@Getter
@ToString
public class Product {
    private final String name;
    private final String size;
    private final int price;
    private final int amount;

    public Product(String name, int price, String size, int amount){
        this.name = Resolver.removeAllSpecialChars(name);
        this.price = price;
        this.size = size;
        this.amount = amount;
    }

    public int calculateTotalProductPrice() {
        return price * amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        if (obj == this) return true;
        Product product = (Product) obj;
        return Resolver.removeAllSpecialChars(name).equals(Resolver.removeAllSpecialChars(product.name))
                && price == product.price
                && size.equals(product.size)
                && amount == product.amount;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        int prime = 31;
        hash = prime * hash + price;
        hash = prime * hash + amount;
        hash = prime * hash + (size == null ? 0 : Resolver.removeAllSpecialChars(size).hashCode());
        hash = prime * hash + (name == null ? 0 : Resolver.removeAllSpecialChars(name).hashCode());
        return hash;
    }
}
