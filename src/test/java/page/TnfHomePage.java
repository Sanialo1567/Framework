package page;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TnfHomePage extends AbstractPage {
    private static final String HOMEPAGE_URL = "https://www.thenorthface.com/";

    public TnfHomePage openPage(){
        driver.get(HOMEPAGE_URL);
        waitUntilAjaxCompleted();

        log.info("driver has changed an active page to a homepage");

        return this;
    }
}
