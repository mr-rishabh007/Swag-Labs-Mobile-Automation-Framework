package com.automation.framework.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// If you need to import your base class to fetch the driver instance:
import com.automation.framework.SwagLabsAppFramework.BaseTestSwagLabsApp; 

public class ExtentTestNGListener implements ITestListener {
    private static ExtentReports extent = ExtentReportManager.getInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // Test suite started
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        ExtentReportManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());

        // Capture and attach screenshot on failure
        try {
            // Fetch the active driver instance from your BaseTest class
            WebDriver driver = BaseTestSwagLabsApp.getDriver(); 
            
            if (driver != null) {
                String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
                extentTest.get().fail("<b>Failure Screenshot:</b>", 
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else {
                extentTest.get().warning("Driver was null. Could not capture screenshot.");
            }
        } catch (Exception e) {
            extentTest.get().warning("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip(result.getThrowable());
    }

    // Helper method to take screenshot and save it locally
    public String captureScreenshot(WebDriver driver, String testName) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String targetDir = System.getProperty("user.dir") + "/reports/screenshots/";
        
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destinationPath = targetDir + testName + "_" + timeStamp + ".png";
        
        try {
            FileUtils.copyFile(srcFile, new File(destinationPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Return relative path so the Extent Report HTML can render it correctly even if moved
        return "screenshots/" + testName + "_" + timeStamp + ".png";
    }
}