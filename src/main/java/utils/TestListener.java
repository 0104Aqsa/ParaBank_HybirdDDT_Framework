package utils;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("🔷 Test Suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🔶 Test Suite finished: " + context.getName());
        ExtentTestManager.endTest(); // flush report
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        ExtentTest test = ExtentTestManager.startTC(methodName, "Executing: " + methodName);
        test.log(Status.INFO, "🚀 Test started: " + methodName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "✅ Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.getTest().log(Status.FAIL, "❌ Test failed: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, "⏭️ Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optional: handle partial success
    }
}