package TestCases;

import Base.BaseTest;
import Pages.LoginPage;
import org.testng.annotations.Test;
import utils.ExcelUtils;
import utils.Log;

public class Login extends BaseTest {

    @Test(priority = 1, description = "Verify login and fallback to registration for multiple users")
    public void VerifyMultipleLogins() throws Exception {
        Log.startTest("VerifyMultipleLogins");

        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "LoginData");
        int totalRows = ExcelUtils.getRowCount();

        for (int rowNum = 1; rowNum < totalRows; rowNum++) {
            Log.info("Executing test for row: " + rowNum);

            LoginPage loginpage = new LoginPage(driver);

            // ✅ Ensure logout before starting next login
            loginpage.logoutIfLoggedIn();
            Thread.sleep(5000);

            loginpage.LoginMethod(rowNum);

            if (loginpage.LoginSuccessMethod()) {
                Log.info("Login successful for user: " + ExcelUtils.getCellData(rowNum, 8));
                loginpage.logoutIfLoggedIn(); // ✅ logout after success
                driver.manage().deleteAllCookies(); // ✅ clears session
                driver.navigate().refresh();        // ✅ reloads the page

                Thread.sleep(5000);

                driver.manage().deleteAllCookies();
            } else {
                String error = loginpage.loginFaileMethod();
                Log.error("Login failed for user: " + ExcelUtils.getCellData(rowNum, 8) + " with message: " + error);

                if (error.trim().equalsIgnoreCase("The username and password could not be verified.")) {
                    Log.info("Registering new user...");
                    loginpage.RegisterUserMethod(rowNum);
                    Thread.sleep(5000);
                    loginpage.LoginMethod(rowNum);

                    if (loginpage.LoginSuccessMethod()) {
                        Log.info("Login successful after registration for user: " + ExcelUtils.getCellData(rowNum, 8));
                        loginpage.logoutIfLoggedIn();
                        driver.manage().deleteAllCookies(); // ✅ clears session
                        driver.navigate().refresh();        // ✅ reloads the page
                    } else {
                        Log.error("Login still failed after registration for user: " + ExcelUtils.getCellData(rowNum, 8));
                    }
                }
            }
        }
    }
}