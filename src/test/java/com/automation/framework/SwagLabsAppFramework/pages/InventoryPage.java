package com.automation.framework.SwagLabsAppFramework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.automation.framework.Utils.WaitUtils;
import io.appium.java_client.AppiumBy;

public class InventoryPage {
	private WebDriver driver;

	public InventoryPage(WebDriver driver) {
		this.driver = driver;
	}

	private By pageTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")");
	private By productImage = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(6)");
	private By productName = AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Backpack\")");
	private By productPrice = AppiumBy.androidUIAutomator("new UiSelector().text(\"$29.99\")");

	// Sorting locators
	private By filterClick = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(5)");
	private By sortAtoZ = AppiumBy.androidUIAutomator("new UiSelector().text(\"Name (A to Z)\")");
	private By sortZtoA = AppiumBy.androidUIAutomator("new UiSelector().text(\"Name (Z to A)\")");
	private By sortLowtoHigh = AppiumBy.androidUIAutomator("new UiSelector().text(\"Price (low to high)\")");
	private By sortHightoLow = AppiumBy.androidUIAutomator("new UiSelector().text(\"Price (high to low)\")");
	private By cancelBtn = AppiumBy.androidUIAutomator("new UiSelector().text(\"Cancel\")");

	// Inventory CHeck Methods
	public String pageTitleChk() {
		return WaitUtils.waitForElementToBeVisible(driver, pageTitle).getText();
	}

	public boolean productImageChk() {
		WebElement img = WaitUtils.waitForElementToBeVisible(driver, productImage);
		return img.isDisplayed();
	}

	public String productNameChk() {
		return WaitUtils.waitForElementToBeVisible(driver, productName).getText();
	}

	public String productPriceChk() {
		return WaitUtils.waitForElementToBeVisible(driver, productPrice).getText();
	}

	public void productAddCartBtnChk() {
		try {
			// Try clicking "ADD TO CART" if visible
			WebElement addBtn = driver.findElement(By.xpath(
					"(//android.view.ViewGroup[@content-desc=\"test-Item\"]) [1]//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"]"));
			if (addBtn.isDisplayed()) {
				addBtn.click();
				return;
			}
		} catch (Exception e) {
			// If ADD TO CART is not found, item might already be added (REMOVE state)
			try {
				WebElement removeBtn = driver.findElement(By.xpath(
						"(//android.view.ViewGroup[@content-desc=\"test-Item\"]) [1]//android.view.ViewGroup[@content-desc=\"test-REMOVE\"]"));
				if (removeBtn.isDisplayed()) {
					// Item is already added, which satisfies the requirement for cart/checkout
					// tests
					return;
				}
			} catch (Exception ignored) {
			}
		}
	}

	public String productRemoveCartBtnTextChk() {
		try {
			// Check if the REMOVE button is displayed for the first item
			WebElement removeBtn = WaitUtils.waitForElementToBeVisible(driver, By.xpath(
					"(//android.view.ViewGroup[@content-desc=\"test-Item\"]) [1]//android.view.ViewGroup[@content-desc=\"test-REMOVE\"]"));
			return removeBtn.isDisplayed() ? "REMOVE" : "";
		} catch (Exception e) {
			return "";
		}
	}

	// Sorting Methods
	public void clickAtoZ() {
		WaitUtils.waitForElementToBeClickable(driver, filterClick).click();
		WaitUtils.waitForElementToBeClickable(driver, sortAtoZ).click();
	}

	public void clickZtoA() {
		WaitUtils.waitForElementToBeClickable(driver, filterClick).click();
		WaitUtils.waitForElementToBeClickable(driver, sortZtoA).click();
	}

	public void clickLowtoHigh() {
		WaitUtils.waitForElementToBeClickable(driver, filterClick).click();
		WaitUtils.waitForElementToBeClickable(driver, sortLowtoHigh).click();
	}

	public void clickHightoLow() {
		WaitUtils.waitForElementToBeClickable(driver, filterClick).click();
		WaitUtils.waitForElementToBeClickable(driver, sortHightoLow).click();
	}

	public void clickOnCancel() {
		WaitUtils.waitForElementToBeClickable(driver, filterClick).click();
		WaitUtils.waitForElementToBeClickable(driver, cancelBtn).click();
	}
}