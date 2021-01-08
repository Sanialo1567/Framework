package test;

import model.User;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import page.TnfPersonalPage;
import page.TnfProductPage;
import service.UserCreator;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TnfPersonalPageTest extends TestBase {

    @Test
    @Ignore
    public void loginTest(){
        User user = UserCreator.withCredentialsFromProperty();

        TnfPersonalPage personalPage = new TnfProductPage()
                .openPage()
                .closePopUps()
                .login(user);

        String firstname = personalPage.getFirstname();
        String lastname = personalPage.getLastname();
        String email = personalPage.getEmail();


        assertThat(firstname, equalTo(user.getFirstname()));
        assertThat(lastname, equalTo(user.getLastname()));
        assertThat(email, equalTo(user.getEmail()));
    }
}
