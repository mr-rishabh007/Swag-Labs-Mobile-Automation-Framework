package com.automation.framework.SwagLabsAppFramework;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.framework.SwagLabsAppFramework.pages.AuthenticationPage;
import com.automation.framework.Utils.WaitUtils;
import io.appium.java_client.AppiumBy;

public class LoginTest extends BaseTestSwagLabsApp {
	
	@BeforeMethod
	public void verifyLoginScreen() {
	    try {
	        // Force restart the app package to ensure every test starts fresh on the login screen
	        ((io.appium.java_client.android.AndroidDriver) driver).terminateApp("com.swaglabsmobileapp");
	        ((io.appium.java_client.android.AndroidDriver) driver).activateApp("com.swaglabsmobileapp");
	    } catch (Exception ignored) {}
	}

	@Test(priority = 1)
	public void TC_LOGIN_01() {
		AuthenticationPage authorize = new AuthenticationPage(driver);
		authorize.clickOnLoginBtn();

		// Using explicit wait for the error message element
		By errorMessageLocator = AppiumBy
				.xpath("//android.view.ViewGroup[@content-desc='test-Error message']//android.widget.TextView");
		String errorMessage = WaitUtils.waitForElementToBeVisible(driver, errorMessageLocator).getText();

		Assert.assertEquals(errorMessage, "Username is required");
	}

	@Test(priority = 2)
	public void TC_LOGIN_02() {
		AuthenticationPage authorize = new AuthenticationPage(driver);
		authorize.enterUserName();
		authorize.enterPassword("secret_sauce");
		authorize.clickOnLoginBtn();
	}
}