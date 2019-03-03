package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearchTest {

	private WebDriver driverMozilla;
	private WebDriver driverChrome;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
		driverMozilla = new FirefoxDriver();
		driverMozilla.manage().window().maximize();
		driverMozilla.get("http://www.google.com");
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driverChrome = new ChromeDriver();
		driverChrome.manage().window().maximize();
		driverChrome.get("http://www.google.com");
	}
	
	@Test
	public void testGoogleSearchInFirefox() {
		WebElement element = driverMozilla.findElement(By.name("q"));
		element.clear();
		element.sendKeys("something interesting");
		element.submit();
		new WebDriverWait(driverMozilla, 10).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("something interesting");
			}
		});
		assertEquals("something interesting - Szukaj w Google", driverMozilla.getTitle());
	}
	
	@Test
	public void testGoogleSearchInChrome() {
		WebElement element = driverChrome.findElement(By.name("q"));
		element.clear();
		element.sendKeys("something interesting");
		element.submit();
		new WebDriverWait(driverChrome, 10).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("something interesting");
			}
		});
		assertEquals("something interesting - Szukaj w Google", driverChrome.getTitle());
	}
	
	@After
	public void tearDown() throws Exception {
		driverMozilla.quit();
		driverChrome.quit();
	}
}
