package messenger.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class MainApp {
	AndroidDriver driver;
	Dimension size;

	@BeforeTest
	public void connection() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "030834eed0218526");
		capabilities.setCapability("appPackage", "com.facebook.orca");
		capabilities.setCapability("appActivity", "com.facebook.orca.auth.StartScreenActivity");
		capabilities.setCapability("noReset", "true");
		capabilities.setCapability("fullReset", false);

		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}
	
	public void defaultHome() throws Exception{
		//driver.findElement(By.id("android.widget.TextView[@resource-id='com.facebook.orca:id/recents_tab']")).click();
		driver.findElementById("com.facebook.orca:id/recents_tab").click();
	}

	// @Test
	public void verifyInbox() throws Exception {
		driver.findElementByAccessibilityId("Selected, Home, Tab 1 of 4").click();
		verticalScrolling(15);

	}

	// @Test
	public void openMessages() throws Exception {
		Thread.sleep(2000);
		driver.findElementByAccessibilityId("Selected, Home, Tab 1 of 4").click();

		WebElement parentElement = driver.findElementById("com.facebook.orca:id/thread_list_recycler_view");
		List<WebElement> childElements = parentElement.findElements(By.className("android.view.View"));
		int childElementsSize = childElements.size();

		for (int i = 0; i < childElementsSize; i++) {
			System.out.println("i " + i);
			if (i == 1) {
				clickMessage(i, childElements);
				System.out.println("in");
			}

		}
		verticalScrolling(3);

	}

	public void verticalScrolling(int n) throws Exception {
		try {
			size = driver.manage().window().getSize();
			int start_y = (int) (size.getHeight() * 0.4);
			int end_y = (int) (size.getHeight() * 0.2);
			int x = (int) (size.getWidth() * 0.2);
			for (int i = 0; i < n; i++)
				driver.swipe(x, start_y, x, end_y, 800);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void keepScrolling() throws Exception{
		
	}

	public void clickMessage(int i, List<WebElement> childElements) throws Exception {

		childElements.get(i).click();
		Thread.sleep(2000);
		driver.findElement(By.id("com.facebook.orca:id/up")).click();

	}

	// @Test
	public void tabActive() throws Exception {
		Thread.sleep(5000);
		driver.findElementByAccessibilityId("Active, Tab 2 of 4").click();
		Thread.sleep(1000);
		List<WebElement> parentElement = driver.findElements(By.className("android.view.View"));
		// List<WebElement> childElements =
		// parentElement.findElements(By.className("android.support.v7.widget.RecyclerView"));
		int childElementsSize = parentElement.size();
		System.out.println("size " + parentElement.size());
		// System.out.println(childElements.get(0).getText());
		driver.findElementById("com.facebook.orca:id/empty_contacts_invite_friends_upsell_layout").getTagName();
		driver.findElementById("com.facebook.orca:id/empty_contacts_invite_friends_upsell_layout").getText();

		if (driver.findElementById("com.facebook.orca:id/empty_contacts_invite_friends_upsell_layout").isDisplayed()) {
			System.out.println("no");
			System.out.println("no contacts");
		} else {
			System.out.println("in");
			// driver.findElement(By.xpath("//android.view.View[@text='Jemmy
			// Johnson']")).click();

		}

		Thread.sleep(2000);
		// .findElement(By.id("com.facebook.orca:id/up")).click();

	}

	@Test
	public void tabGroups() throws Exception {
		defaultHome();
		Thread.sleep(3000);
		driver.findElementById("com.facebook.orca:id/groups_tab").click();
		Thread.sleep(1000);

		List<WebElement> groupsList = driver.findElements(By.className("android.widget.RelativeLayout"));
		for (WebElement webElement : groupsList) {

			webElement.click();
			Thread.sleep(1000);
			driver.findElementById("com.facebook.orca:id/up").click();
		verticalScrolling(3);

		}

	}

	//@Test
	public void tabCalls() throws Exception {
		Thread.sleep(2000);
		driver.findElementByAccessibilityId("Calls, Tab 4 of 4").click();
		Thread.sleep(1000);
		verticalScrolling(3);

	}

	// @Test
	public void composeMessage() throws Exception {
		Thread.sleep(5000);
		driver.findElementByAccessibilityId("Write Message").click();
		driver.findElement(By
				.xpath("//android.widget.EditText[@resource-id='com.facebook.orca:id/contact_picker_autocomplete_input']"))
				.sendKeys("jentest");
		driver.findElement(By
				.xpath("//android.widget.RelativeLayout[@resource-id='com.facebook.orca:id/contact_picker_list_item']"))
				.click();
		driver.findElement(By.xpath("//android.widget.EditText[@resource-id='com.facebook.orca:id/text_input_bar']"))
				.click();
		driver.findElement(By.xpath("//android.widget.EditText[@resource-id='com.facebook.orca:id/text_input_bar']"))
				.sendKeys("Auto");
		driver.findElement(
				By.xpath("//android.widget.ImageView[@resource-id='com.facebook.orca:id/composer_send_action_button']"))
				.click();
		driver.findElement(By.id("com.facebook.orca:id/up")).click();
		// driver.findElementByAccessibilityId("Navigate Up").click();

	}
	
	public void peopleTab() throws Exception{
		
	}
	
    public void gamesTab() throws Exception{
		
	}

    public void discoverTab() throws Exception{
	
   }

	@AfterTest
	public void end() {
		driver.quit();
	}

}
