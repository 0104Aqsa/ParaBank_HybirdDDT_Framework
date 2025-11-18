package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {

    private static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static ExtentReports extent = ExtentManager.getInstance();

    // ============================
    //   START TEST CASE (TC)
    // ============================
    public static synchronized ExtentTest startTC(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    // ============================
    //   GET CURRENT TEST
    // ============================
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    // ============================
    //     END & FLUSH REPORT
    // ============================
    public static synchronized void endTest() {
        extent.flush();
    }
}
