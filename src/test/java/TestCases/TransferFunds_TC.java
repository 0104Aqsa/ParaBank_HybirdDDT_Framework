package TestCases;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.TransferFundsPage;
import utils.ExcelUtils;
import utils.Log;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentTestManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransferFunds_TC extends BaseTest {

    @Test
    public void verifyTransferFundsMultipleUsers() throws Exception {

        ExtentTest test = ExtentTestManager.startTC(
                "verifyTransferFundsMultipleUsers",
                "Login/Register → Ensure Account → Transfer Funds for multiple users"
        );
        Log.startTest("verifyTransferFundsMultipleUsers");

        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "LoginData");
        int totalRows = ExcelUtils.getRowCount();

            for (int rowNum = 1; rowNum < totalRows; rowNum++) {

            String username = ExcelUtils.getCellData(rowNum, 8);
            test.info("🔄 Starting transfer flow for user row " + rowNum + " (username: " + username + ")");
            Log.info("Executing transfer flow for user at row: " + rowNum);

            // -------------------------
            // Step 1: Login or Register & Ensure Account Exists
            // -------------------------
            UserFlow userFlow = new UserFlow(driver, test);
            userFlow.loginAndCreateAccount(rowNum); // reusable login/register/account creation flow

            // -------------------------
            // Step 2: Perform Transfer Funds
            // -------------------------
            TransferFundsPage transfer = new TransferFundsPage(driver);

            try {
                transfer.TransferFundMethod(rowNum);
                test.pass("💸 Transfer successful for user: " + username);
                Log.info("Transfer successful for user: " + username);
            } catch (Exception e) {
                test.fail("❌ Transfer failed for user: " + username + " - " + e.getMessage());
                Log.error("Transfer failed: " + e.getMessage());
                Assert.fail("Transfer failed for user: " + username, e);
            }

            // -------------------------
            // Step 3: Logout & Cleanup
            // -------------------------
            userFlow.logout();
            test.info("🚪 Logged out after transfer for user: " + username);
            driver.manage().deleteAllCookies();
            Thread.sleep(2000);
        }

        Log.endTest("verifyTransferFundsMultipleUsers");
        ExtentTestManager.endTest();
    }
}
