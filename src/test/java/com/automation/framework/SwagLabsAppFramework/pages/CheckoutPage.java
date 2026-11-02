package com.automation.framework.SwagLabsAppFramework.pages;

import com.automation.framework.Utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.time.Duration;
import java.util.Arrays;

public class CheckoutPage {
	private WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}

	private By firstNameField = AppiumBy.accessibilityId("test-First Name");
	private By lastNameField = AppiumBy.accessibilityId("test-Last Name");
	private By postalCodeField = AppiumBy.accessibilityId("test-Zip/Postal Code");
	private By continueButton = AppiumBy.accessibilityId("test-CONTINUE");
	private By cancelButton = AppiumBy.accessibilityId("test-CANCEL");
	private By finishButton = AppiumBy.accessibilityId("test-FINISH");
	private By checkoutCompleteContainer = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(14)");
	private By backToHome = AppiumBy.accessibilityId("test-BACK HOME");

	// ── Page Methods ─────────────────────────────────────────────

	public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
		try {
			// Ensure the form fields are scrolled into view and visible before typing
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().accessibilityId(\"test-First Name\"));"));
		} catch (Exception ignored) {}

		WaitUtils.waitForElementToBeVisible(driver, firstNameField).sendKeys(firstName);
		driver.findElement(lastNameField).sendKeys(lastName);
		driver.findElement(postalCodeField).sendKeys(postalCode);
	}

	public void clickContinue() {
		WaitUtils.waitForElementToBeClickable(driver, continueButton).click();
	}

	public void clickCancel() {
		WaitUtils.waitForElementToBeClickable(driver, cancelButton).click();
	}

	public void clickFinish() {
		// Loop up to 3 times to ensure the finish button is scrolled into view
		for (int i = 0; i < 3; i++) {
			try {
				if (driver.findElement(finishButton).isDisplayed()) {
					break;
				}
			} catch (Exception e) {
				try {
					driver.findElement(AppiumBy.androidUIAutomator(
							"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().accessibilityId(\"test-FINISH\"));"));
				} catch (Exception ex) {
					try {
						int startX = driver.manage().window().getSize().width / 2;
						int startY = (int) (driver.manage().window().getSize().height * 0.75);
						int endY = (int) (driver.manage().window().getSize().height * 0.25);

						PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
						Sequence swipe = new Sequence(finger, 1);
						swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
						swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
						swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
						swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
						
						((io.appium.java_client.AppiumDriver) driver).perform(Arrays.asList(swipe));
					} catch (Exception ignored) {}
				}
			}
		}

		WaitUtils.waitForElementToBeClickable(driver, finishButton).click();
	}

	public boolean isCheckoutCompleteDisplayed() {
		try {
			return WaitUtils.waitForElementToBeVisible(driver, checkoutCompleteContainer).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	public void clickOnBackToHomeBtn() {
		WaitUtils.waitForElementToBeClickable(driver, backToHome).click();
	}
}