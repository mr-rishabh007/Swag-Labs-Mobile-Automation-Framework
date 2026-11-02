package com.automation.framework.SwagLabsAppFramework;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.automation.framework.SwagLabsAppFramework.pages.CartPage;
import com.automation.framework.SwagLabsAppFramework.pages.InventoryPage;
import com.automation.framework.SwagLabsAppFramework.pages.ProductDetailsPage;

public class CartTest extends BaseTestSwagLabsApp {
	CartPage cartPage;
	ProductDetailsPage homePage;
	InventoryPage inventoryPage;

	@BeforeMethod
	public void setupPages() {
		cartPage = new CartPage(driver);
		homePage = new ProductDetailsPage(driver);
		inventoryPage = new InventoryPage(driver);
	}

	@Test(priority = 1)
	public void TC_CART_01_verifyCartNavigationAndItemPresence() {
		try {
			homePage.addToCartBtn();
		} catch (Exception e) {
		}
		cartPage.clickOnCart();

		String title = cartPage.getCartPageTitle();
		Assert.assertEquals(title, "YOUR CART", "Cart page title mismatch!");

		boolean isItemPresent = cartPage.isCartItemDisplayed();
		Assert.assertTrue(isItemPresent, "Added product is missing from the cart!");
	}

	@Test(priority = 2)
	public void TC_CART_02_verifyContinueShoppingButton() {
		cartPage.clickContinueShopping();

		String productTitle = inventoryPage.pageTitleChk();
		Assert.assertEquals(productTitle, "PRODUCTS", "Did not return to Products inventory page!");
	}

	@Test(priority = 3)
	public void TC_CART_03_verifyItemRemovalFromCart() {
		try {
			inventoryPage.productAddCartBtnChk();
		} catch (Exception e) {
			try {
				homePage.addToCartBtn();
			} catch (Exception ignored) {
			}
		}
		cartPage.clickOnCart();

		cartPage.clickRemoveFromCart();

		boolean isRemoved = cartPage.waitForItemToBeRemoved();
		Assert.assertTrue(isRemoved, "Item was NOT successfully removed from the cart!");
	}

	@Test(priority = 4)
	public void TC_CART_04_verifyProceedToCheckout() {
		// Ensure we go back to inventory from cart if needed, or navigate fresh
		try {
			cartPage.clickContinueShopping();
		} catch (Exception e) {
			try {
				driver.navigate().back();
			} catch (Exception ignored) {
			}
		}

		// Add an item fresh to ensure cart has items for checkout
		try {
			inventoryPage.productAddCartBtnChk();
		} catch (Exception e) {
			try {
				homePage.addToCartBtn();
			} catch (Exception ignored) {
			}
		}

		cartPage.clickOnCart();
		cartPage.clickCheckout();
	}
}