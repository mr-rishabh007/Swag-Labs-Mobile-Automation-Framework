package com.automation.framework.SwagLabsAppFramework;

import java.io.File;
import java.time.Duration;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTestSwagLabsApp {
	public static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public AppiumDriverLocalService service;

	@BeforeSuite
	public void configureAppium() throws Exception {
		// Conditionally start local Appium service only if running in CI mode or if
		// needed,
		// preventing port binding conflicts when running locally via Eclipse/Terminal.
		String executionEnv = System.getProperty("env", "local");

		if ("ci".equalsIgnoreCase(executionEnv)) {
			try {
				service = new AppiumServiceBuilder()
						.withAppiumJS(new File(
								"C:\\Users\\Rishabh\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
						.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
						.withIPAddress("127.0.0.1").usingPort(4723).build();
				service.start();
			} catch (Exception e) {
				// Fallback if service is already running externally
			}
		}

		// Configure Android & Appium options
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("Rishabh Pixel 9a");
		options.setPlatformName("Android");
		options.setAutomationName("UIAutomator2");
		options.setChromedriverExecutable("F:\\Eclipse Application\\chromedriver-win64\\chromedriver.exe");
		options.setApp(
				"F:\\Eclipse Application\\eclipse-workspace\\SwagLabsAppFramework\\src\\test\\java\\com\\automation"
						+ "\\framework\\Utils\\SauceLabs_Mobile_Sample_app.apk");
		options.setAppPackage("com.swaglabsmobileapp");
		options.setAppActivity("com.swaglabsmobileapp.SplashActivity");
		options.setAppWaitActivity("*");
		options.setCapability("autoGrantPermissions", true);
		options.setCapability("appium:enforceAppInstall", false);
		options.setCapability("appium:orientation", "PORTRAIT");
		options.setCapability("appium:autoGrantPermissions", true);
		options.setCapability("appium:autoGrantPermissions", true);

		// Initialize AndroidDriver
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	public void scrollIntoViewByText(String visibleText) {
		try {
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""
							+ visibleText + "\"));"));
		} catch (Exception e) {
			scrollDownSwipe();
		}
	}

	@BeforeMethod
	public void setupMethod() {
		try {
			if (driver.findElements(io.appium.java_client.AppiumBy.accessibilityId("test-Username")).size() > 0) {
				driver.findElement(io.appium.java_client.AppiumBy.accessibilityId("test-Username"))
						.sendKeys("standard_user");
				driver.findElement(io.appium.java_client.AppiumBy.accessibilityId("test-Password"))
						.sendKeys("secret_sauce");
				driver.findElement(io.appium.java_client.AppiumBy.accessibilityId("test-LOGIN")).click();
			}
		} catch (Exception ignored) {
		}
	}

	public void scrollIntoViewByAccessibilityId(String accessibilityId) {
		try {
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().accessibilityId(\""
							+ accessibilityId + "\"));"));
		} catch (Exception e) {
			scrollDownSwipe();
		}
	}

	public static void scrollDownSwipe() {
		try {
			int startX = driver.manage().window().getSize().width / 2;
			int startY = (int) (driver.manage().window().getSize().height * 0.75);
			int endY = (int) (driver.manage().window().getSize().height * 0.25);

			org.openqa.selenium.interactions.PointerInput finger = new org.openqa.selenium.interactions.PointerInput(
					org.openqa.selenium.interactions.PointerInput.Kind.TOUCH, "finger");

			org.openqa.selenium.interactions.Sequence swipe = new org.openqa.selenium.interactions.Sequence(finger, 1);
			swipe.addAction(finger.createPointerMove(Duration.ZERO,
					org.openqa.selenium.interactions.PointerInput.Origin.viewport(), startX, startY));
			swipe.addAction(
					finger.createPointerDown(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
			swipe.addAction(finger.createPointerMove(Duration.ofMillis(500),
					org.openqa.selenium.interactions.PointerInput.Origin.viewport(), startX, endY));
			swipe.addAction(
					finger.createPointerUp(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));

			((io.appium.java_client.AppiumDriver) driver).perform(java.util.Arrays.asList(swipe));
		} catch (Exception ignored) {
		}
	}

	@AfterSuite
	public void TearDown() {
		if (driver != null) {
			driver.quit();
		}
		if (service != null && service.isRunning()) {
			service.stop();
		}
	}
}