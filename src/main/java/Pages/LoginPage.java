package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.ExcelUtils;
import utils.Log;

import java.io.IOException;
import java.time.Duration;

public class LoginPage extends BaseTest {
    private WebDriver driver;

    // Locators for register page
    By Register = By.linkText("Register");
    By FirstName = By.name("customer.firstName");
    By LastName = By.name("customer.lastName");
    By Address = By.name("customer.address.street");
    By City = By.name("customer.address.city");
    By State = By.name("customer.address.state");
    By Zipcode = By.name("customer.address.zipCode");
    By SSN = By.name("customer.ssn");
    By UsernameRegister = By.name("customer.username");
    By PasswordRegister = By.name("customer.password");
    By confirmPassword = By.name("repeatedPassword");
    By RegisterButton = By.xpath("//input[@value='Register']");
    By successregsiterMessage = By.xpath("//p[text()='Your account was created successfully. You are now logged in.']");

    // Locators for login page
    By UserName = By.name("username");
    By Password = By.name("password");
    By LoginButton = By.xpath("//input[@type='submit']");
    By ErrorinLogin = By.xpath("//p[@class='error']");
    By AfterSuccesslogin = By.linkText("Accounts Overview");

    // for logout
    By LogoutLink = By.linkText("Log Out");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized");
        }
    }

    public void RegisterUserMethod(int rowNum) throws IOException {
        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "LoginData");

        String FName = ExcelUtils.getCellData(rowNum, 1);
        String LName = ExcelUtils.getCellData(rowNum, 2);
        String Add = ExcelUtils.getCellData(rowNum, 3);
        String Cityy = ExcelUtils.getCellData(rowNum, 4);
        String Stat = ExcelUtils.getCellData(rowNum, 5);
        String Zip = ExcelUtils.getCellData(rowNum, 6);
        String SSNum = ExcelUtils.getCellData(rowNum, 7);
        String UserNam = ExcelUtils.getCellData(rowNum, 8);
        String Pass = ExcelUtils.getCellData(rowNum, 9);
        String ConfirmPass = Pass;

        driver.findElement(Register).click();
        driver.findElement(FirstName).sendKeys(FName);
        driver.findElement(LastName).sendKeys(LName);
        driver.findElement(Address).sendKeys(Add);
        driver.findElement(City).sendKeys(Cityy);
        driver.findElement(State).sendKeys(Stat);
        driver.findElement(Zipcode).sendKeys(Zip);
        driver.findElement(SSN).sendKeys(SSNum);
        driver.findElement(UsernameRegister).sendKeys(UserNam);
        driver.findElement(PasswordRegister).sendKeys(Pass);
        driver.findElement(confirmPassword).sendKeys(ConfirmPass);
        driver.findElement(RegisterButton).click();
    }

    public boolean SuccessRegsiter() {
        try {
            return driver.findElement(successregsiterMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void LoginMethod(int rowNum) throws IOException, InterruptedException {
        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "LoginData");

        String TypeUsername = ExcelUtils.getCellData(rowNum, 8);
        String TypePassword = ExcelUtils.getCellData(rowNum, 9);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(UserName));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(Password));

        usernameField.clear();
        usernameField.sendKeys(TypeUsername);

        passwordField.clear();
        passwordField.sendKeys(TypePassword);

        driver.findElement(LoginButton).click();
    }

    public boolean LoginSuccessMethod() {
        try {
            return driver.findElement(AfterSuccesslogin).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String loginFaileMethod() {
        try {
            return driver.findElement(ErrorinLogin).getText();
        } catch (Exception e) {
            return "";
        }
    }
    public boolean isLoggedIn() {
        try {
            return driver.findElement(By.linkText("Log Out")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public void logoutIfLoggedIn() {
        try {
            if (driver.findElement(LogoutLink).isDisplayed()) {
                driver.findElement(LogoutLink).click();
                Log.info("Logged out successfully.");
            }
        } catch (Exception e) {
            Log.info("Logout not required or element not found.");
        }
    }

}