package com.sharelane.test;

import com.sharelane.page.RegistrationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTest extends RegistrationPage {

    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String zipCode;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        confirmPassword = password;
        zipCode = "12345";
        openRegistrationForm(zipCode);
        WebElement registerBtn = driver.findElement(By.xpath(REGISTER_BTN));
        Assert.assertTrue(registerBtn.isDisplayed());
    }

    @Test
    public void checkSuccessfulRegistrationWithAllValidFields() {
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifySuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithEmptyFirstName() {
        firstName = EMPTY_STRING;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithEmptyLastName() {
        lastName = EMPTY_STRING;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithEmptyEmail() {
        email = EMPTY_STRING;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithEmptyPassword() {
        password = EMPTY_STRING;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithEmptyConfirmPassword() {
        confirmPassword = EMPTY_STRING;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithSpaceBeforeTextInFirstName() {
        firstName = SPACE + firstName;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithSpaceBeforeTextInLastName() {
        lastName = SPACE + lastName;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithSpaceBeforeTextInEmail() {
        email = SPACE + email;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithSpaceBeforeTextInPassword() {
        password = SPACE + password;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithSpaceBeforeTextInConfirmPassword() {
        confirmPassword = SPACE + confirmPassword;
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithEmailWithoutATAndDomain() {
        fillRegistrationForm(firstName, lastName, getEmailWithoutATAndDomain(), password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithSpecSymbolsInEmail() {
        fillRegistrationForm(firstName, lastName, getEmailWithSpecialCharacters(), password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWithMismatchPasswordAndConfirmPassword() {
        confirmPassword = faker.internet().password();
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWhenPasswordEqualsEmail() {
        fillRegistrationForm(firstName, lastName, email, email, email);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @Test
    public void checkUnsuccessfulRegistrationWhenUserIsNotUnique() {
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        driver.navigate().back();
        clearAllRegistrationFields();
        fillRegistrationForm(firstName, lastName, email, password, confirmPassword);
        clickRegister();
        verifyUnsuccessfulRegistrationMessage();
    }

    @AfterTest()
    public void tearDown() {
        driver.quit();
    }

    private String getEmailWithoutATAndDomain() {
        return email.replaceAll("@.*", "");
    }

    private String getEmailWithSpecialCharacters() {
        return "?/*" + email;
    }

    private void verifySuccessfulRegistrationMessage() {
        String actualMsg = getRegistrationStatusMessage();
        Assert.assertEquals(actualMsg, SUCCESSFUL_REGISTRATION_MSG, "Registration should be done");
    }

    private void verifyUnsuccessfulRegistrationMessage() {
        String actualMsg = getRegistrationStatusMessage();
        Assert.assertEquals(actualMsg, UNSUCCESSFUL_REGISTRATION_MSG, "Registration shouldn't be done");
    }
}
