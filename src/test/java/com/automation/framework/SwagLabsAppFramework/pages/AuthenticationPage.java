package com.automation.framework.SwagLabsAppFramework.pages;
import com.automation.framework.SwagLabsAppFramework.BaseTestSwagLabsApp;
import com.automation.framework.Utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthenticationPage extends BaseTestSwagLabsApp {
	private WebDriver driver;

	// Constructor
	public AuthenticationPage(WebDriver driver) {
		this.driver = driver;
	}

	// ── Locators ───────────
	private By loginBtn = AppiumBy.accessibilityId("test-LOGIN");
	private By fieldUserName = AppiumBy.accessibilityId("test-Username");
	private By fieldPassword = AppiumBy.accessibilityId("test-Password");

	// ── Page Methods using Explicit Waits ─────────────────────────────
	public void clickOnLoginBtn() {
		WaitUtils.waitForElementToBeClickable(driver, loginBtn).click();
	}

	public void enterUserName() {
		WaitUtils.waitForElementToBeVisible(driver, fieldUserName).sendKeys("standard_user");
	}

	public void enterPassword(String password) {
	    try {
	        WaitUtils.waitForElementToBeVisible(driver, fieldPassword).sendKeys(password);
	    } catch (Exception e) {
	    	scrollDownSwipe();
	        WaitUtils.waitForElementToBeVisible(driver, fieldPassword).sendKeys(password);
	    }
	}
}