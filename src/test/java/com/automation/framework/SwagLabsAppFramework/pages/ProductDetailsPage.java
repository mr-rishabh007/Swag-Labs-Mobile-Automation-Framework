package com.automation.framework.SwagLabsAppFramework.pages;

import com.automation.framework.SwagLabsAppFramework.BaseTestSwagLabsApp;
import com.automation.framework.Utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage {
	private WebDriver driver;

	public ProductDetailsPage(WebDriver driver) {
		this.driver = driver;
	}

	private By productItem = AppiumBy.accessibilityId("test-Item");
	private By addToCartButton = AppiumBy.accessibilityId("test-ADD TO CART");
	private By backButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"BACK TO PRODUCTS\")");
	private By removeButton = AppiumBy.accessibilityId("test-REMOVE");
	private By cartBadge = By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']//android.widget.TextView");

	// ── Page Methods ─────────────────────────────────────────────
	public void viewProductDescription() {
		try {
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().accessibilityId(\"test-Item\"));"));
		} catch (Exception e) {}
		
		try {
			WaitUtils.waitForElementToBeClickable(driver, productItem).click();
		} catch (Exception e) {
			// Fallback click by class name if accessibility id container is missed
			driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)")).click();
		}
	}

	public void backToProducts() {
		try {
			WaitUtils.waitForElementToBeClickable(driver, backButton).click();
		} catch (Exception e) {
			driver.navigate().back();
		}
	}

	public void addToCartBtn() {
		try {
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().accessibilityId(\"test-ADD TO CART\"));"));
			WaitUtils.waitForElementToBeClickable(driver, addToCartButton).click();
		} catch (Exception e) {
			try {
				if (!isRemoveButtonDisplayed()) {
					BaseTestSwagLabsApp.scrollDownSwipe();
					WaitUtils.waitForElementToBeClickable(driver, addToCartButton).click();
				}
			} catch (Exception ignored) {}
		}
	}

	public void removeCartBtn() {
		try {
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().accessibilityId(\"test-REMOVE\"));"));
			WaitUtils.waitForElementToBeClickable(driver, removeButton).click();
		} catch (Exception e) {
			try {
				driver.findElement(removeButton).click();
			} catch (Exception ignored) {}
		}
	}

	public boolean isRemoveButtonDisplayed() {
		try {
			return driver.findElement(removeButton).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getCartBadgeText() {
		try {
			return WaitUtils.waitForElementToBeVisible(driver, cartBadge).getText();
		} catch (Exception e) {
			return "0"; 
		}
	}
}