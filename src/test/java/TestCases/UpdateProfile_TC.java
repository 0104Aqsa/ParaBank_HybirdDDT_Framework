package TestCases;

import Base.BaseTest;
import Pages.UpdateProfile;
import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExcelUtils;
import utils.ExtentTestManager;
import utils.Log;

public class UpdateProfile_TC  extends BaseTest {

    @Test
    public void verifyUpdateProfileForMultipleUser()  throws Exception{
        ExtentTest test = ExtentTestManager.startTC(
                "verifyUpdateProfileForMultipleUser",
                "Login/Register →Update the profile"
        );
        Log.startTest("verifyUpdateProfileForMultipleUser");
        ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "LoginData");
        int totalRows=ExcelUtils.getRowCount();

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
            // Step 2: Initialize the updateprofile excel sheet and Updatethe profile
            // -------------------------
            ExcelUtils.setExcelFile("src/test/resources/TestData.xlsx", "UpdateProfile");
            int updateRows = ExcelUtils.getRowCount();

            for (int updateRow = 1; updateRow < updateRows; updateRow++) {
                UpdateProfile UpdatePro = new UpdateProfile(driver);

                try {
                    UpdatePro.UpdateProfileMethod(updateRow);

                    test.pass("💸 Update profile successful for user: " + username);
                    Log.info("Profile Updated successful for user: " + username);
                    UpdatePro.SuccessUpdateMethod();
                    Log.info("Sucessfully upate profile message is getting");

                } catch (Exception e) {
                    test.fail("❌ Update profile is  failed for user: " + username + " - " + e.getMessage());
                    Log.error("update profile is  failed: " + e.getMessage());
                    Assert.fail("profile updation is failed for user: " + username, e);
                }
            }

            // -------------------------
            // Step 3: Logout & Cleanup
            // -------------------------
            userFlow.logout();
            test.info("🚪 Logged out after updateprofile for user: " + username);
            driver.manage().deleteAllCookies();
            Thread.sleep(2000);

        }

    }

}
