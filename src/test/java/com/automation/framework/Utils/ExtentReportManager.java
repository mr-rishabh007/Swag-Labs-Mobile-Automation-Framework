package com.automation.framework.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static ExtentReports createInstance() {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String fileName = "Test-Report-" + timeStamp + ".html";

		// Reports will be saved in a 'reports' folder at the root of your project
		File reportDir = new File(System.getProperty("user.dir") + "/reports");
		if (!reportDir.exists()) {
			reportDir.mkdirs();
		}

		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportDir + "/" + fileName);
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setDocumentTitle("SwagLabs Automation Execution Report");
		sparkReporter.config().setReportName("Mobile App Test Results");
		sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("OS", "Windows 11");
		extent.setSystemInfo("App", "Swag Labs Mobile App");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester", "Rishabh");

		return extent;
	}

	public static ExtentReports getInstance() {
		if (extent == null) {
			createInstance();
		}
		return extent;
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static void setTest(ExtentTest extentTest) {
		test.set(extentTest);
	}
}