package service;

import model.Product;
import model.User;

import static util.Resolver.resolveTemplate;

public class UserCreator {
    public static final String USER_FIRSTNAME_TEMPLATE = "test.data.user.firstname";
    public static final String USER_LASTNAME_TEMPLATE = "test.data.user.lastname";
    public static final String USER_EMAIL_TEMPLATE = "test.data.user.email";
    public static final String USER_PASSWORD_TEMPLATE = "test.data.user.password";

    public static User withCredentialsFromProperty(){
        return new User(TestDataReader.getTestData(USER_FIRSTNAME_TEMPLATE),
                TestDataReader.getTestData(USER_LASTNAME_TEMPLATE),
                TestDataReader.getTestData(USER_PASSWORD_TEMPLATE),
                TestDataReader.getTestData(USER_EMAIL_TEMPLATE));
    }
}
