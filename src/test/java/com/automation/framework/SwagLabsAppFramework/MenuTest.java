package com.automation.framework.SwagLabsAppFramework;

import com.automation.framework.SwagLabsAppFramework.pages.AuthenticationPage;
import com.automation.framework.SwagLabsAppFramework.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MenuTest extends BaseTestSwagLabsApp {
	private MenuPage menuPage;
	private AuthenticationPage authPage;

	@BeforeMethod
	public void setupTestModule() {
		authPage = new AuthenticationPage(driver);
		menuPage = new MenuPage(driver);

		// Ensure user is logged in before each test if currently on the login screen
		try {
			if (driver.findElements(io.appium.java_client.AppiumBy.accessibilityId("test-Username")).size() > 0) {
				authPage.enterUserName();
				authPage.enterPassword("secret_sauce");
				authPage.clickOnLoginBtn();
			}
		} catch (Exception ignored) {
		}
	}

	@Test(priority = 1, description = "TC_MENU_01: Verify side navigation menu open and close actions")
	public void TC_01_testOpenAndCloseMenu() {
		menuPage.openMenu();
		boolean areOptionsVisible = menuPage.areAllMenuOptionsDisplayed();
		Assert.assertTrue(areOptionsVisible, "Side menu options failed to display correctly.");
		menuPage.closeMenu();
	}

	@Test(priority = 2, description = "TC_MENU_02: Verify Reset App State functionality from the side menu")
	public void TC_02_testResetAppState() {
		menuPage.openMenu();
		menuPage.clickResetAppState();
	}

	@Test(priority = 3, description = "TC_MENU_03: Verify WebView feature navigation from the menu")
	public void TC_03_testWebViewNavigation() {
		menuPage.openMenu();
		menuPage.navigateToWebView();
	}

	@Test(priority = 4, description = "TC_MENU_04: Verify Drawing canvas feature")
	public void TC_04_testDrawingCanvasFeature() {
		menuPage.openMenu();
		menuPage.navigateToDrawing();
	}

	@Test(priority = 5, description = "TC_MENU_05: Verify Geo Location / Maps feature")
	public void TC_05_testGeoLocationFeature() {
		menuPage.openMenu();
		menuPage.navigateToGeoLocation();
		menuPage.dismissLocationPopupIfPresent(); // Dismisses the system accuracy popup if it pops up
	}

	@Test(priority = 6, description = "Verify Logout flow from the side menu returns user to login screen")
	public void TC_06_testLogoutFromMenu() {
		menuPage.openMenu();
		menuPage.clickLogout();

		// Verify return to login screen
		boolean isLoginDisplayed = driver.findElements(io.appium.java_client.AppiumBy.accessibilityId("test-LOGIN"))
				.size() > 0;
		Assert.assertTrue(isLoginDisplayed, "Logout failed to return user to the authentication screen.");
	}
}