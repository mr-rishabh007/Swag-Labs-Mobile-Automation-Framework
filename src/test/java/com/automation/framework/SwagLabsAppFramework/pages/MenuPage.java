package com.automation.framework.SwagLabsAppFramework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.appium.java_client.AppiumBy;

public class MenuPage {
	private WebDriver driver;
	private WebDriverWait wait;

	public MenuPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// --- LOCATORS FOR SIDE MENU & SPECIAL FEATURES ---
	private By menuDrawerButton = AppiumBy.accessibilityId("test-Menu");
	private By allItemsOption = By.xpath("//android.widget.TextView[@text='ALL ITEMS']");
	private By webViewOption = By.xpath("//android.widget.TextView[@text='WEBVIEW']");
	private By qrCodeOption = By.xpath("//android.widget.TextView[@text='QR CODE SCANNER']");
	private By geoCoordOption = By.xpath("//android.widget.TextView[@text='GEO LOCATION']");
	private By drawingOption = By.xpath("//android.widget.TextView[@text='DRAWING']");
	private By aboutOption = By.xpath("//android.widget.TextView[@text='ABOUT']");
	private By logoutOption = By.xpath("//android.widget.TextView[@text='LOGOUT']");
	// Change from 'RESET APPS STATE' to 'RESET APP STATE' (or use partial text
	// matching)
	private By resetAppStateOption = By.xpath("//android.widget.TextView[contains(@text, 'RESET APP STATE')]");
	private By closeMenuButton = AppiumBy.accessibilityId("test-Close");

	// --- PAGE ACTIONS & VERIFICATIONS ---

	public void openMenu() {
		wait.until(ExpectedConditions.elementToBeClickable(menuDrawerButton)).click();
	}

	public void closeMenu() {
		wait.until(ExpectedConditions.elementToBeClickable(closeMenuButton)).click();
	}

	public boolean areAllMenuOptionsDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(allItemsOption)).isDisplayed()
					&& driver.findElement(webViewOption).isDisplayed() && driver.findElement(qrCodeOption).isDisplayed()
					&& driver.findElement(geoCoordOption).isDisplayed()
					&& driver.findElement(drawingOption).isDisplayed() && driver.findElement(aboutOption).isDisplayed()
					&& driver.findElement(logoutOption).isDisplayed()
					&& driver.findElement(resetAppStateOption).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickResetAppState() {
		wait.until(ExpectedConditions.elementToBeClickable(resetAppStateOption)).click();
	}

	public void navigateToWebView() {
		wait.until(ExpectedConditions.elementToBeClickable(webViewOption)).click();
	}

	public void navigateToDrawing() {
		wait.until(ExpectedConditions.elementToBeClickable(drawingOption)).click();
	}

	public void navigateToGeoLocation() {
		wait.until(ExpectedConditions.elementToBeClickable(geoCoordOption)).click();
	}

	public void clickLogout() {
		wait.until(ExpectedConditions.elementToBeClickable(logoutOption)).click();
	}

	public void dismissLocationPopupIfPresent() {
		try {
			By noThanksBtn = By.xpath("//android.widget.Button[@text='No thanks']");
			if (driver.findElements(noThanksBtn).size() > 0) {
				driver.findElement(noThanksBtn).click();
			}
		} catch (Exception ignored) {
		}
	}
}