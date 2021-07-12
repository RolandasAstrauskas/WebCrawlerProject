package lt.rol;

import lt.rol.configuration.SeleniumConfiguration;
import lt.rol.flightData.Flight;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import java.util.ArrayList;


public class WebCrawler {

    private final SeleniumConfiguration seleniumConfiguration = new SeleniumConfiguration();
    private final ChromeDriver driver = seleniumConfiguration.getDriver();

    
    public void searchForFlights() throws InterruptedException {
        clickCountry();
        insertFlightFrom();
        insertFlightTo();
        insertFlightDateFrom();
        insertFlightDateTo();
        chooseDirection();
        chooseDirectOrNotFlight();
        checkBoxSubmitFareCalendar();
        searchButtonClick();
        readDate();
        driver.close();
    }

    private void readDate() throws InterruptedException {
        ArrayList<Flight> flightList = new ArrayList<>();

        for(int tr = 1; 5 > tr;tr++){
            for(int td = 2; 9 > td;td++){
                boolean calendar = existTicketPriceInCalendar(tr, td);
                boolean ticketPage = existTicketPage();
                if(!calendar & !ticketPage){
                    continue;
                }else if(ticketPage){
                    flightList.add(collectData());
                }else{
                    driver.findElement(By.xpath("/html/body/main/nas-fare-calendar/nas-calendar-combo" +
                            "/div/div/div/section/nas-calendar/div/div/table/tbody/tr[" + tr + "]/td[" + td + "]")).click();
                    driver.findElement(By.id("nas-continue-action-0")).click();
                    flightList.add(collectData());
                }
            }
        }
        System.out.println(flightList);
    }

    private Flight collectData() throws InterruptedException {
        String flightDateFrom = collectFlightDateFrom();
        String flightDateTo = collectFlightDateTo();
        String flightFrom = collectFlightFrom();
        String flightTo = collectFlightTo();
        String date = collectDate();
        choosePrice();

        double priceWithoutTaxes = Double.parseDouble(collectLowPrice().replace("£", ""));
        double taxes = Double.parseDouble(collectTax().replace("£", ""));
        buttonNextDay();

        return new Flight(date, flightDateFrom, flightDateTo, flightFrom,
                                 flightTo, priceWithoutTaxes, taxes);
    }

    private String collectDate() {
        String date = driver.findElement(By.xpath("//*[@id='avaday-outbound-result']/div/div/div[1]/table/tbody/tr/td[2]")).getText();
        return date;
    }

    private void buttonNextDay() {
        driver.findElement(By.xpath("//*[@id='avaday-outbound-result']/div/div/div[3]" +
                "/table/tbody/tr/td[2]/div/span[2]")).click();
    }

    private void clickCountry() throws InterruptedException {
        driver.findElement(By.xpath(("//div[text()='UK (English)']"))).click();
        driver.findElement(By.id(("nas-button-7"))).click();
        Thread.sleep(1000);
    }

    private void insertFlightFrom() throws InterruptedException {
        Thread.sleep(3000);
        WebElement inputFrom = driver.findElement(By.id("nas-airport-select-dropdown-input-0"));
        Thread.sleep(1000);
        inputFrom.clear();
        Thread.sleep(1000);
        inputFrom.sendKeys("OSL");
        driver.findElement(By.id("nas-airport-select-dropdown-airport-0-all-airports-OSL")).click();
    }

    private void insertFlightTo() throws InterruptedException {
        WebElement inputTo = driver.findElement(By.id("nas-airport-select-dropdown-input-1"));
        Thread.sleep(1000);
        inputTo.clear();
        Thread.sleep(1000);
        inputTo.sendKeys("FCO");
        driver.findElement(By.id("nas-airport-select-dropdown-airport-1-all-airports-FCO")).click();
    }

    private void insertFlightDateFrom() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.className("nas-datepicker-combo__date-picker")).click();
        clickNextMonthInCalendar();
        clickNextMonthInCalendar();
        clickNextMonthInCalendar();
        clickNextMonthInCalendar();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@aria-label='01']")).click();
    }

    private void clickNextMonthInCalendar() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@aria-label='Show next month']")).click();
    }

    private void insertFlightDateTo() throws InterruptedException {
        Thread.sleep(700);
        driver.findElement(By.xpath("//*[@aria-label='30']")).click();
    }

    private void chooseDirection() throws InterruptedException {
        driver.findElement(By.id("nas-dropdown-2")).click();
        Thread.sleep(800);
        driver.findElement(By.xpath("//*[text()='One-way']")).click();
    }

    private  void chooseDirectOrNotFlight() throws InterruptedException {
        driver.findElement(By.id("nas-dropdown-3")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[text()='Direct only']")).click();
    }

    private void searchButtonClick() {
        driver.findElement(By.id("nas-continue-action-2")).click();
    }

    private String collectTax() throws InterruptedException {
        WebElement price = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_ipcAvaDay_upnlResSelection']" +
                "/div[1]/div/table/tbody/tr[17]/td[2]"));
        return price.getText();
    }

    private String collectLowPrice() {
        WebElement price = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_ipcAvaDay_upnlResSelection']" +
                "/div[1]/div/table/tbody/tr[19]/td[2]"));
        return price.getText();
    }

    private String collectFlightTo() throws InterruptedException {
        WebElement flightTo = driver.findElement(By.xpath("//*[@id='avaday-outbound-result']/div/div/" +
                "div[2]/div/table/tbody/tr[2]/td[2]/div"));
        return flightTo.getText();
    }

    private String collectFlightFrom() throws InterruptedException {
        WebElement flightTo = driver.findElement(By.xpath("//*[@id='avaday-outbound-result']/div/div/div" +
                "[2]/div/table/tbody/tr[2]/td[1]/div"));
        return flightTo.getText();
    }

    private String collectFlightDateTo() throws InterruptedException {
        WebElement flightDate = driver.findElement(By.className("arrdest"));
        return flightDate.getText();
    }

    private String collectFlightDateFrom() throws InterruptedException {
        WebElement flightDate = driver.findElement(By.className("depdest"));
        return flightDate.getText();
    }

    private void choosePrice() throws InterruptedException {
        driver.findElement(By.id("FlightSelectOutboundStandardLowFare0")).click();
    }
    private void checkBoxSubmitFareCalendar() {
        driver.findElement(By.xpath("//*[@id='nas-tab-content-0-0']/div/nas-flight-search/form/div[2]" +
                "/nas-continue/footer/div/div/div/nas-checkbox/label/span")).click();
    }

    private boolean existTicketPage() {
        boolean present;
        try {
            driver.findElement(By.id("FlightSelectOutboundStandardLowFare0"));
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }
        return present;
    }

    private boolean existTicketPriceInCalendar(int tr, int td) {
        boolean present;
        try {
            driver.findElement(By.xpath("/html/body/main/nas-fare-calendar/nas-calendar-combo/" +
                    "div/div/div/section/nas-calendar/div/div/table" +
                    "/tbody/tr[" +tr+"]/td["+td+"]/button/strong"));
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }
        return present;
    }

}
