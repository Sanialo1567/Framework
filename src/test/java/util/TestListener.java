package util;

import driver.DriverSingleton;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TestListener implements ITestListener {

    public void onTestFailure(ITestResult iTestResult){
        saveScreenshot();
    }

    private void saveScreenshot() {
        File screenCapture = ((TakesScreenshot)DriverSingleton
                .getInstance())
                .getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenCapture,
                    new File(String.format(".//target/screenshots/%s%s",getCurrentTime(), ".png")));
        } catch (IOException ex){
            log.error("Failed to save screenshot: " + ex.getMessage());
        }
    }

    private String getCurrentTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss");
        return Instant.now().atZone(ZoneId.systemDefault()).format(formatter);
    }
}
