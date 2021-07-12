package lt.rol.configuration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class SeleniumConfiguration {

    final private String URL = "https://www.norwegian.com/";
    final private String DRIVER = "webdriver.chrome.driver";
    final private String DRIVER_PATH = "Path to chrome driver";



    public ChromeDriver getDriver() {

        System.setProperty(DRIVER,DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3000,TimeUnit.MILLISECONDS);
        driver.manage().window().maximize();
        driver.get(URL);

        return driver;
    }

}

