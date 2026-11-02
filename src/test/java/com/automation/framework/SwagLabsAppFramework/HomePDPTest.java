package com.automation.framework.SwagLabsAppFramework;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.automation.framework.SwagLabsAppFramework.pages.ProductDetailsPage;
import com.automation.framework.SwagLabsAppFramework.pages.CartPage;

public class HomePDPTest extends BaseTestSwagLabsApp {
	ProductDetailsPage homePage;
	CartPage cartPage;

	@BeforeMethod
	public void setupPages() {
		homePage = new ProductDetailsPage(driver);
		cartPage = new CartPage(driver);
	}

	@Test(priority = 1)
	public void TC_PDP_01() {
		homePage.viewProductDescription();
	}

	@Test(priority = 2)
	public void TC_PDP_02() {
		homePage.backToProducts();
	}

	@Test(priority = 3)
	public void TC_PDP_03() {
		homePage.viewProductDescription();

		// Ensure clean state: if item is already added (shows REMOVE), click it once to
		// toggle back to ADD TO CART
		if (homePage.isRemoveButtonDisplayed()) {
			homePage.removeCartBtn();
		}

		// 1. Click Add to Cart on the Product Details page
		homePage.addToCartBtn();

		// 2. Verify button text/state toggled instantly to "REMOVE"
		boolean isRemoveVisible = homePage.isRemoveButtonDisplayed();
		Assert.assertTrue(isRemoveVisible, "Button state did not change to 'REMOVE' after clicking Add to Cart!");

		// 3. Verify the cart badge counter is present and reads correctly
		String cartCount = homePage.getCartBadgeText();
		Assert.assertTrue(Integer.parseInt(cartCount) > 0, "Cart badge counter did not increment!");

		// Go back to products list for subsequent tests
		try {
			homePage.backToProducts();
		} catch (Exception e) {
		}
	}

	@Test(priority = 4)
	public void TC_PDP_04() throws InterruptedException {
		try {
			homePage.viewProductDescription();
		} catch (Exception e) {
			driver.navigate().back();
			homePage.viewProductDescription();
		}
		homePage.removeCartBtn();
		Thread.sleep(1000);
	}
}