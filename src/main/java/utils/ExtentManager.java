package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            spark.config().setDocumentTitle("Automation Test Results");
            spark.config().setReportName("Banking App Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Aqsa Shaikh");
        }
        return extent;
    }
}
