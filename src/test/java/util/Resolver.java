package util;

public class Resolver {
    public static int resolvePrice(String price){
        return Integer.parseInt(String.join("", price.trim().split("[.$]")));
    }

    public static String resolveSize(String size){
        return size.replace("-","");
    }

    public static int resolveInt(String string){
        return Integer.parseInt(string.trim());
    }

    public static String removeAllSpecialChars(String string){
        return string.replaceAll("[^a-zA-Z0-9- ]","");
    }

    public static String resolveTemplate(String template, String data){
        return String.format(template, data);
    }

    public static String resolveTemplate(String template, int data){
        return String.format(template, data);
    }

    public static int resolveDiscount(int cost, int discount){
        return cost - (int)(cost * (discount / 100.));
    }
}
