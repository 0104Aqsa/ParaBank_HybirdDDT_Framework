package TestCases;

import Pages.Home_OpenAccount;
import Pages.LoginPage;
import com.aventstack.extentreports.ExtentTest;
import utils.ExcelUtils;
import utils.Log;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class UserFlow {

    private WebDriver driver;
    private LoginPage loginPage;
    private Home_OpenAccount homeAccount;
    private ExtentTest test; // optional for logging

    public UserFlow(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        this.loginPage = new LoginPage(driver);
        this.homeAccount = new Home_OpenAccount(driver);
    }

    // Reusable method for login/register + ensure account exists
    public void loginAndCreateAccount(int rowNum) throws Exception {

        // 1️⃣ Attempt login
        loginPage.logoutIfLoggedIn();
        Thread.sleep(2000);
        loginPage.LoginMethod(rowNum);

        if (!loginPage.LoginSuccessMethod()) {
            String error = loginPage.loginFaileMethod();
            Log.error("Login failed: " + error);
            if (test != null) test.warning("⚠️ Login failed: " + error);

            // Register user if login failed
            loginPage.RegisterUserMethod(rowNum);
            Thread.sleep(2000);
            Assert.assertTrue(loginPage.SuccessRegsiter(), "Registration failed");
            if (test != null) test.pass("✨ Registration successful (auto-logged in)");
        } else {
            if (test != null) test.pass("✅ Login successful");
            Log.info("Login successful.");
        }

        // 2️⃣ Ensure account exists
        homeAccount.OpenAccountMethod(rowNum);
        Thread.sleep(2000);
        Assert.assertTrue(homeAccount.SuccesscreatedAccMethod(), "Account creation failed");
        if (test != null) test.pass("🏦 Account created successfully");
    }

    // Reusable logout
    public void logout() {
        loginPage.logoutIfLoggedIn();
        if (test != null) test.info("🚪 Logged out");
    }
}
