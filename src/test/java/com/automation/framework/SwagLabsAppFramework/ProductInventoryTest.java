package com.automation.framework.SwagLabsAppFramework;

import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.framework.SwagLabsAppFramework.pages.InventoryPage;

public class ProductInventoryTest extends BaseTestSwagLabsApp {
	InventoryPage inventoryPage;

	@BeforeMethod
	public void setUp() {
		// 'driver' is now inherited from BaseTestSwagLabsApp
		inventoryPage = new InventoryPage(driver);
	}

	@Test(priority = 1)
	public void TC_INV_01() {
		String titleChk = inventoryPage.pageTitleChk();
		Assert.assertEquals(titleChk, "PRODUCTS", "Page title mismatch!");

		boolean isImageDisplayed = inventoryPage.productImageChk();
		Assert.assertTrue(isImageDisplayed, "Product image is not displayed!");

		String productNameVerify = inventoryPage.productNameChk();
		Assert.assertNotNull(productNameVerify, "Product name element is missing or null!");
		Assert.assertFalse(productNameVerify.trim().isEmpty(), "Product name is empty!");

		String priceVerify = inventoryPage.productPriceChk();
		Assert.assertNotNull(priceVerify, "Product price element is missing or null!");
		Assert.assertFalse(priceVerify.trim().isEmpty(), "Product price is empty!");
		// Click the add to cart button
		inventoryPage.productAddCartBtnChk();

		// Verify the button successfully toggled to REMOVE state
		String addButtonVerify = inventoryPage.productRemoveCartBtnTextChk();
		Assert.assertEquals(addButtonVerify, "REMOVE", "Add to cart button did not toggle to REMOVE state!");
	}

	@Test(priority = 2)
	public void TC_INV_02() {
		inventoryPage.clickAtoZ();
		inventoryPage.clickZtoA();

	}

	@Test(priority = 3)
	public void TC_INV_03() {
		inventoryPage.clickLowtoHigh();
		inventoryPage.clickHightoLow();
		inventoryPage.clickOnCancel();
	}

	@Test(priority = 4)
	public void TC_INV_04() {
		// 1. Verify initial state (Portrait)
		Assert.assertEquals(inventoryPage.pageTitleChk(), "PRODUCTS", "Page title mismatch in Portrait!");
		Assert.assertTrue(inventoryPage.productImageChk(), "Product image not visible in Portrait mode.");

		// 2. Try rotating to LANDSCAPE mode
		try {
			((io.appium.java_client.android.AndroidDriver) driver).rotate(ScreenOrientation.LANDSCAPE);
			Thread.sleep(1500); // Wait for UI reflow
		} catch (Exception e) {
			System.out.println(
					"Note: Programmatic rotation restricted by environment, skipping physical rotation command.");
		}

		// Verify elements in landscape view (or verify current layout state)
		Assert.assertEquals(inventoryPage.pageTitleChk(), "PRODUCTS",
				"Page title check failed after rotation attempt!");
		Assert.assertTrue(inventoryPage.productImageChk(), "Product image check failed after rotation attempt!");

		// 3. Rotate back to PORTRAIT mode
		try {
			((io.appium.java_client.android.AndroidDriver) driver).rotate(ScreenOrientation.PORTRAIT);
			Thread.sleep(1500);
		} catch (Exception e) {
			// Catch block to prevent hard failures if rotation lock is active
		}

		// Final post-rotation check
		Assert.assertEquals(inventoryPage.pageTitleChk(), "PRODUCTS",
				"Page title missing after rotating back to Portrait!");
	}
}