package com.sharelane.page;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class RegistrationPage {

    private static final String REGISTRATION_URL = "https://www.sharelane.com/cgi-bin/register.py";
    private static final String ZIP_CODE_ELEMENT = "//input[@name='zip_code']";
    private static final String CONTINUE_BTN = "//input[@value='Continue']";
    private static final String UNSUCCESSFUL_REGISTRATION_MSG_ELEMENT = "//tr[@align='center']//span[@class]";
    private static final String FIRST_NAME_ELEMENT = "//input[@name='first_name']";
    private static final String LAST_NAME_ELEMENT = "//input[@name='last_name']";
    private static final String EMAIL_ELEMENT = "//input[@name='email']";
    private static final String PASSWORD_ELEMENT = "//input[@name='password1']";
    private static final String CONFIRM_PASSWORD_ELEMENT = "//input[@name='password2']";
    private static final String REGISTRATION_FORM_INPUT_ELEMENT = "//table[@class='yellow_bg']" +
            "//td/input[@type!='submit']";
    protected static final String REGISTER_BTN = "//input[@value='Register']";
    protected static final String SUCCESSFUL_REGISTRATION_MSG = "Account is created!";
    protected static final String UNSUCCESSFUL_REGISTRATION_MSG = "Oops, error on page." +
            " Some of your fields have invalid data or email was previously used";

    protected WebDriver driver;
    protected Faker faker;

    public RegistrationPage() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        faker = new Faker();
    }

    protected void openRegistrationForm(String zipCode) {
        driver.get(REGISTRATION_URL);
        driver.findElement(By.xpath(ZIP_CODE_ELEMENT)).sendKeys(zipCode);
        driver.findElement(By.xpath(CONTINUE_BTN)).click();
    }

    protected void fillRegistrationForm(String firstName, String lastName, String email, String password, String confirmPassword) {
        driver.findElement(By.xpath(FIRST_NAME_ELEMENT)).sendKeys(firstName);
        driver.findElement(By.xpath(LAST_NAME_ELEMENT)).sendKeys(lastName);
        driver.findElement(By.xpath(EMAIL_ELEMENT)).sendKeys(email);
        driver.findElement(By.xpath(PASSWORD_ELEMENT)).sendKeys(password);
        driver.findElement(By.xpath(CONFIRM_PASSWORD_ELEMENT)).sendKeys(confirmPassword);
    }

    protected void clickRegister() {
        driver.findElement(By.xpath(REGISTER_BTN)).click();
    }

    protected void clearAllRegistrationFields() {
        List<WebElement> registrationFields = driver.findElements(By.xpath(REGISTRATION_FORM_INPUT_ELEMENT));
        registrationFields.forEach(WebElement::clear);
    }

    protected String getRegistrationStatusMessage() {
        return driver.findElement(By.xpath(UNSUCCESSFUL_REGISTRATION_MSG_ELEMENT)).getText();
    }
}
