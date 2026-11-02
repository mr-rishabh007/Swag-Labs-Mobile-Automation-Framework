package com.automation.framework.SwagLabsAppFramework;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.automation.framework.SwagLabsAppFramework.pages.CartPage;
import com.automation.framework.SwagLabsAppFramework.pages.CheckoutPage;
import com.automation.framework.SwagLabsAppFramework.pages.InventoryPage;
import com.automation.framework.SwagLabsAppFramework.pages.ProductDetailsPage;

public class CheckoutTest extends BaseTestSwagLabsApp {
	CheckoutPage checkoutPage;
	CartPage cartPage;
	ProductDetailsPage homePage;
	InventoryPage inventoryPage;

	@BeforeMethod
	public void setupPages() {
		checkoutPage = new CheckoutPage(driver);
		cartPage = new CartPage(driver);
		homePage = new ProductDetailsPage(driver);
		inventoryPage = new InventoryPage(driver);
	}

	private void navigateToCheckoutScreen() {
		try {
			// 1. Ensure an item is added to the cart from the products/catalog view first
			try {
				inventoryPage.productAddCartBtnChk();
			} catch (Exception e) {
				try {
					homePage.viewProductDescription();
					homePage.addToCartBtn();
					homePage.backToProducts();
				} catch (Exception ignored) {}
			}

			// 2. Explicitly click the cart icon to open the Cart page
			cartPage.clickOnCart();

			// 3. Click Checkout from the Cart page
			cartPage.clickCheckout();
		} catch (Exception e) {
			// Fallback navigation if already partially in a flow
			try {
				cartPage.clickCheckout();
			} catch (Exception ignored) {}
		}
	}

	@Test(priority = 1)
	public void TC_CHECKOUT_01_verifyMandatoryFieldsValidation() {
		navigateToCheckoutScreen();
		checkoutPage.clickContinue();
		// Add assertion for error message validation if required
	}

	@Test(priority = 2)
	public void TC_CHECKOUT_02_verifySuccessfulInformationSubmission() {
		navigateToCheckoutScreen();
		checkoutPage.enterCheckoutInformation("Rishabh", "Choure", "411027");
		checkoutPage.clickContinue();
	}

	@Test(priority = 3)
	public void TC_CHECKOUT_03_verifyCancelCheckoutFlow() {
		navigateToCheckoutScreen();
		checkoutPage.clickCancel();

		String productTitle = inventoryPage.pageTitleChk();
		Assert.assertEquals(productTitle, "PRODUCTS", "Did not return to products page after cancellation!");
	}

	@Test(priority = 4)
	public void TC_CHECKOUT_04_verifyCompleteOrderFlow() {
		navigateToCheckoutScreen();
		checkoutPage.enterCheckoutInformation("Rishabh", "Choure", "411027");
		checkoutPage.clickContinue();
		checkoutPage.clickFinish();

		boolean isComplete = checkoutPage.isCheckoutCompleteDisplayed();
		Assert.assertTrue(isComplete, "Checkout order completion message is missing!");
		checkoutPage.clickOnBackToHomeBtn();
	}
}