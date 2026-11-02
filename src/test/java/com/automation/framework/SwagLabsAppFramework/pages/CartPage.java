package com.automation.framework.SwagLabsAppFramework.pages;

import com.automation.framework.Utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.time.Duration;
import java.util.Arrays;

public class CartPage {
	private WebDriver driver;

	public CartPage(WebDriver driver) {
		this.driver = driver;
	}

	private By cartIconClick = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(13)");
	private By cartTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"YOUR CART\")");
	private By cartItemRow = AppiumBy.accessibilityId("test-Item");
	private By removeButton = AppiumBy.accessibilityId("test-REMOVE");
	private By continueShoppingButton = AppiumBy.accessibilityId("test-CONTINUE SHOPPING");
	private By checkoutButton = AppiumBy.accessibilityId("test-CHECKOUT");

	public void clickOnCart() {
		WaitUtils.waitForElementToBeClickable(driver, cartIconClick).click();
	}

	public String getCartPageTitle() {
		try {
			return WaitUtils.waitForElementToBeVisible(driver, cartTitle).getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isCartItemDisplayed() {
		try {
			return driver.findElement(cartItemRow).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitForItemToBeRemoved() {
		try {
			org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
					java.time.Duration.ofSeconds(5));
			return wait
					.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(cartItemRow));
		} catch (Exception e) {
			return false;
		}
	}

	public void clickRemoveFromCart() {
		// Loop iteratively to remove all items present in the cart even if multiple items are listed below the fold
		for (int i = 0; i < 3; i++) {
			try {
				driver.findElement(AppiumBy.androidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().accessibilityId(\"test-REMOVE\"));"));
			} catch (Exception e) {
				scrollDownSwipe();
			}
			
			try {
				WaitUtils.waitForElementToBeClickable(driver, removeButton).click();
				Thread.sleep(1000);
			} catch (Exception e) {
				break; // Exit loop when no more remove buttons exist
			}
		}
	}

	public void clickContinueShopping() {
		try {
			// Scroll down until the 'test-CONTINUE SHOPPING' accessibility ID is visible
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().accessibilityId(\"test-CONTINUE SHOPPING\"));"));
		} catch (Exception e) {
			scrollDownSwipe();
		}

		WaitUtils.waitForElementToBeClickable(driver, continueShoppingButton).click();
	}

	public void clickCheckout() {
	    try {
	        WaitUtils.waitForElementToBeClickable(driver, checkoutButton).click();
	    } catch (Exception e) {
	        // Scroll down if the button is out of viewport bounds
	        scrollDownSwipe();
	        WaitUtils.waitForElementToBeClickable(driver, checkoutButton).click();
	    }
	}

	// Helper method for touch swipe fallback using correct Selenium 4 pointer constructor signature
	private void scrollDownSwipe() {
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