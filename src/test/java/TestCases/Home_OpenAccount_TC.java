package TestCases;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.Home_OpenAccount;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.ExcelUtils;
import utils.Log;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentTestManager;

public class Home_OpenAccount_TC extends BaseTest {

    @Test
    public void verifyMultiUserOpenAccountFlow() throws Exception {

        ExtentTest test = ExtentTestManager.startTC("verifyMultiUserOpenAccountFlow","Login + Open Account for multiple users");
        Log.startTest("verifyMultiUserOpenAccountFlow");

        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "LoginData");
        int totalRows = ExcelUtils.getRowCount();

        for (int rowNum = 1; rowNum < totalRows; rowNum++) {

            String username = ExcelUtils.getCellData(rowNum, 8);
            test.info("🔄 Starting flow for user row " + rowNum + " (username: " + username + ")");
            Log.info("Executing flow for user at row: " + rowNum);

            LoginPage loginPage = new LoginPage(driver);

            loginPage.logoutIfLoggedIn();
            Thread.sleep(2000);

            // STEP 1: Attempt Login
            loginPage.LoginMethod(rowNum);
            test.info("🔐 Attempted login: " + username);

            boolean loginSuccess = loginPage.LoginSuccessMethod();

            if (!loginSuccess) {
                String error = loginPage.loginFaileMethod();
                Log.error("Login failed: " + error);
                test.warning("⚠️ Login failed: " + error);

                // -------------------------
                // SCENARIO 2: Missing Username/Password
                // -------------------------
                if (error.contains("Please enter a username and password")) {

                    test.info("🔁 Retrying login due to missing credentials...");
                    loginPage.LoginMethod(rowNum);
                    Thread.sleep(2000);

                    if (loginPage.LoginSuccessMethod()) {
                        test.pass("✅ Login successful after retry");
                        Log.info("Login successful after retry");
                    } else {
                        // Retry failed → Register user
                        test.info("📝 Retry failed → Registering user...");
                        loginPage.RegisterUserMethod(rowNum);
                        Thread.sleep(2000);

                        Assert.assertTrue(loginPage.SuccessRegsiter(),
                                "Registration failed after retry login scenario");
                        test.pass("✨ Registration successful (auto-logged in)");
                    }
                }
                // -------------------------
                // SCENARIO 1: Wrong Credentials
                // -------------------------
                else if (error.contains("could not be verified")) {

                    test.info("📝 Registering new user due to invalid credentials...");
                    loginPage.RegisterUserMethod(rowNum);
                    Thread.sleep(2000);

                    Assert.assertTrue(loginPage.SuccessRegsiter(),
                            "Registration failed for invalid credentials scenario");
                    test.pass("✨ Registration successful (auto-logged in)");
                }
                // -------------------------
                // UNKNOWN ERROR
                // -------------------------
                else {
                    test.fail("❌ Unexpected login error: " + error);
                    Assert.fail("Unexpected login failure: " + error);
                }
            } else {
                // -------------------------
                // SCENARIO 3: Login Success
                // -------------------------
                test.pass("✅ Login successful");
                Log.info("Login succeeded.");
            }

            // NOW USER IS LOGGED IN (from login OR registration)
            Log.info("Proceeding to Open Account...");
            test.info("🏦 Proceeding to open account...");

            Home_OpenAccount homeAccount = new Home_OpenAccount(driver);
            Thread.sleep(2000);

            homeAccount.OpenAccountMethod(rowNum);
            Thread.sleep(2000);

            Assert.assertTrue(homeAccount.SuccesscreatedAccMethod(), "Account creation failed.");
            test.pass("🎉 Account created successfully");

            loginPage.logoutIfLoggedIn();
            test.info("🚪 Logged out after account creation");

            driver.manage().deleteAllCookies();
            Thread.sleep(2000);
        }

        Log.endTest("verifyMultiUserFlow");
        ExtentTestManager.endTest();
    }
}
