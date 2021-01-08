package test;

import driver.DriverSingleton;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import util.TestListener;

@Listeners(TestListener.class)
public class TestBase {
    @AfterMethod(alwaysRun = true)
    public void close(){
        DriverSingleton.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void dispose(){
        DriverSingleton.closeDriver();
    }
}
