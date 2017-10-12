package messenger.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.WebDriverEventListener;
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
		 capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
		 "030834eed0218526");
		//capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ZX1G22GL9N");
		capabilities.setCapability("appPackage", "com.facebook.orca");
		capabilities.setCapability("appActivity", "com.facebook.orca.auth.StartScreenActivity");
		capabilities.setCapability("noReset", "true");
		capabilities.setCapability("fullReset", false);

		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}
	
	@Test
	public void allTests() throws Exception {
		//loginScreen();
		/*verifyInbox();
		openMessages();
		tabActive();
		tabGroups();
		tabCalls();
*/		//peopleTab();
		gamesTab();
		discoverTab();
		
	}

	public void defaultHome() throws Exception {

		driver.findElementById("com.facebook.orca:id/recents_tab").click();
	}

	public void homeTab() throws Exception {
		driver.findElementById("com.facebook.orca:id/home_nested_tab").click();
	}

	//@Test
	public void verifyInbox() throws Exception {
		System.out.println("VERIFYING INBOX");
		homeTab();
		verticalScrolling(15);

	}

	//@Test
	public void openMessages() throws Exception {
		Thread.sleep(2000);
		System.out.println("OPEN ANY MESSAGE");
		//driver.findElementByAccessibilityId("Selected, Home, Tab 1 of 4").click();
		homeTab();
		defaultHome();
		// WebElement parentElement =
		// driver.findElementById("com.facebook.orca:id/thread_list_recycler_view");
		// List<WebElement> childElements =
		// parentElement.findElements(By.className("android.view.View"));
		List<WebElement> childElements = driver.findElements(By.className("android.view.View"));
		int childElementsSize = childElements.size();
		System.out.println("childElementsSize " + childElementsSize);
		for (int i = 1; i < childElementsSize; i++) {

			if (i % 4 == 0) {
				clickMessage(i, childElements);

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

	public void clickMessage(int i, List<WebElement> childElements) throws Exception {
		System.out.println("first " + i + childElements.size());

		childElements.get(3).click();

		Boolean isBack = driver.findElements(By.id("com.facebook.orca:id/quicksilver_back_button")).size() > 0;
		if (isBack) {
			driver.findElement(By.id("com.facebook.orca:id/quicksilver_back_button")).click();
			// System.out.println(driver.findElement(By.id("com.facebook.orca:id/up")).getClass());
			Thread.sleep(2000);
		} else {
			driver.findElement(By.id("com.facebook.orca:id/up")).click();
		}
		/*
		 * if (driver.findElement(By.id("com.facebook.orca:id/up")).isDisplayed
		 * ()) {System.out.println("disp");
		 * driver.findElement(By.id("com.facebook.orca:id/up")).click(); } else
		 * driver.findElementById(
		 * "com.facebook.orca:id/quicksilver_back_button" ).click();
		 */

	}

	//@Test
	public void tabActive() throws Exception {
		homeTab();
		System.out.println("VERIFYING ACTIVE TAB");
		defaultHome();
		// Thread.sleep(5000);
		driver.findElementById("com.facebook.orca:id/active_now_tab").click();
		Thread.sleep(1000);
		List<WebElement> activeElementsList = driver.findElements(By.className("android.view.View"));
		// List<WebElement> childElements =
		// parentElement.findElements(By.className("android.support.v7.widget.RecyclerView"));
		// int activeElementsList = parentElement.size();
		System.out.println("size " + activeElementsList.size());
		// System.out.println(childElements.get(0).getText());
		// driver.findElementById("com.facebook.orca:id/empty_contacts_invite_friends_upsell_layout").getTagName();
		// driver.findElementById("com.facebook.orca:id/empty_contacts_invite_friends_upsell_layout").getText();

		Boolean isActiveList= driver.findElements(By.id("com.facebook.orca:id/empty_contacts_title_for_ig_upsell")).size()>0;
		//if (driver.findElementById("com.facebook.orca:id/empty_contacts_title_for_ig_upsell").isDisplayed()) {
		if (isActiveList){
			System.out.println("NO CONTACTS FOUND");
		} else {
			System.out.println("in");
			verticalScrolling(2);
			// driver.findElement(By.xpath("//android.view.View[@text='Jemmy
			// Johnson']")).click();

		}

		Thread.sleep(2000);
		// .findElement(By.id("com.facebook.orca:id/up")).click();

	}

	//@Test
	public void tabGroups() throws Exception {

		System.out.println("VERIFYING GROUP TABS");
		defaultHome();
		Thread.sleep(3000);
		driver.findElementById("com.facebook.orca:id/groups_tab").click();
		Thread.sleep(1000);

		List<WebElement> groupsList = driver.findElements(By.className("android.widget.RelativeLayout"));
		for (WebElement gList : groupsList) {

			gList.click();
			Thread.sleep(1000);
			driver.findElementById("com.facebook.orca:id/up").click();
			verticalScrolling(2);

		}

	}

	//@Test
	public void tabCalls() throws Exception {
		System.out.println("VERIFYING CALL TABS");
		homeTab();
		//defaultHome();
		
		Thread.sleep(2000);
		driver.findElementById("com.facebook.orca:id/call_tab").click();
		Thread.sleep(1000);
		verticalScrolling(3);
		homeTab();
	}

	//@Test
	public void composeMessage() throws Exception {
		Thread.sleep(5000);
		homeTab();
		driver.findElementByAccessibilityId("Write Message").click();
		driver.findElement(By
				.xpath("//android.widget.EditText[@resource-id='com.facebook.orca:id/contact_picker_autocomplete_input']"))
				.sendKeys("jentest mal");

		driver.findElement(By
				.xpath("//android.widget.RelativeLayout[@resource-id='com.facebook.orca:id/contact_picker_list_item']"))
				.click();
		// if
		// (driver.findElement(By.id("com.facebook.orca:id/message_requests_accept_button")).isDisplayed())
		Boolean isAccept = driver.findElements(By.id("com.facebook.orca:id/message_requests_accept_button")).size() > 0;
		if (isAccept) {
			driver.findElement(By.id("com.facebook.orca:id/message_requests_accept_button")).click();
		}
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

	// @Test
	public void peopleTab() throws Exception {

		defaultHome();
		Thread.sleep(2000);
		driver.findElementById("com.facebook.orca:id/people_tab").click();
		System.out.println("PEOPLE TAB");
		Thread.sleep(1000);

		List<WebElement> peopleList = driver.findElements(By.className("android.widget.ListView"));
		// ?? why does it click the element not in class RelativeLayout
		for (WebElement pList : peopleList) {
			pList.click();
			Thread.sleep(1000);
			driver.findElementById("com.facebook.orca:id/up").click();
			verticalScrolling(3);
		}
		homeTab();
	}

	// @Test
	public void gamesTab() throws Exception {
		defaultHome();
		Thread.sleep(2000);
		System.out.println("GAMES TAB");
		driver.findElementById("com.facebook.orca:id/games_tab").click();
		Thread.sleep(1000);

		List<WebElement> gameList = driver.findElements(By.className("android.widget.LinearLayout"));
		// ?? why does it click the element not in class RelativeLayout
		/*
		 * for (WebElement pList:gameList) {System.out.println("ho" +
		 * pList.getTagName()); verticalScrolling(2); pList.click();
		 * Thread.sleep(1000);
		 * driver.findElementById("com.facebook.orca:id/quicksilver_back_button"
		 * ).click();
		 * 
		 * }
		 */
		System.out.println("game list size " + gameList.size());
		for (int i = 1; i < gameList.size(); i++) {
			System.out.println("size " + gameList.size() + " i " + i);

			System.out.println("i inside loop");
			if (i % 3 == 0) {System.out.println("in");
				clickMessage(i, gameList);
			}
			Thread.sleep(3000);
			System.out.println("after click ");

		}
		verticalScrolling(3);
	}

	// @Test
	public void discoverTab() throws Exception {
		defaultHome();

		driver.findElement(By.xpath("//android.widget.ImageView[@index='4']")).click();
		// driver.findElement(By.xpath("//android.widget.ImageView[5]")).click();
		Thread.sleep(3000);

		List<WebElement> discoverLists = driver.findElements(By.xpath("//android.view.View"));
		verticalScrolling(3);
		Thread.sleep(2000);
		/*
		 * for (WebElement ldiscover : discoverLists) { //
		 * driver.findElement(By.xpath("//android.view.View[@index='5']")).click
		 * ();
		 * 
		 * driver.findElement(By.id(
		 * "com.facebook.orca:id/platform_landing_page_back_button")).click(); }
		 */
		System.out.println(" Size discover " + discoverLists.size());
		for (int i = 7; i < discoverLists.size(); i++) {

			if (i == 9) {

				// String str= discoverLists.get(i).getText();

				discoverLists.get(i).click();
				Thread.sleep(2000);
			}
		}

		/*
		 * for (int i = 0; i < discoverLists.size(); i++) {
		 * 
		 * //driver.findElement(By.xpath("//android.view.View")).click();
		 * //driver.findElement(By.xpath(
		 * "//android.support.v7.widget.RecyclerView[@index='0']/android.widget.Button[@index='5']"
		 * )).click(); }
		 */

	}

	//@Test
	public void loginScreen() throws Exception {
		// Thread.sleep(4000);
		Boolean isLogin = driver.findElements(By.id("com.facebook.orca:id/login")).size() > 0;
		Boolean isLoginGroup = driver.findElements(By.id("com.facebook.orca:id/login_group")).size() > 0;
		Boolean isRecentTab = driver.findElements(By.id("com.facebook.orca:id/login_group")).size() > 0;
		// if
		// (driver.findElement(By.id("com.facebook.orca:id/login")).isDisplayed())
		// {
		if (isLogin) {
			System.out.println("isLogin");
			driver.findElement(By.id("com.facebook.orca:id/login")).click();
			logIn();
		} else if (isLoginGroup) {
			System.out.println("isLoginGroup");
			driver.findElement(By.id("com.facebook.orca:id/login_group")).click();
			System.out.println("Please log in ");
			Thread.sleep(2000);
		} else {
			System.out.println("USER ALREADY LOGGED IN");
			// driver.findElementById("com.facebook.orca:id/recents_tab").isDisplayed()
			defaultHome();
		}
	}

	public void logIn() throws Exception {
		if (driver.findElement(By.id("com.facebook.orca:id/skip")).isDisplayed()) {
			driver.findElement(By.id("com.facebook.orca:id/skip")).click();
			driver.findElement(By.id("com.facebook.orca:id/skip_step")).click();
			driver.findElement(By.id("com.facebook.orca:id/button2")).click();
			defaultHome();
			Thread.sleep(2000);
		}

	}

	@AfterTest
	public void end() {
		driver.quit();
	}

}
