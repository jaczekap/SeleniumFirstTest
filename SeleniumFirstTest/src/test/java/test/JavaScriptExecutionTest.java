package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class JavaScriptExecutionTest {
	
	private WebDriver driverChrome;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driverChrome = new ChromeDriver();
		driverChrome.manage().window().maximize();
		driverChrome.get("http://www.google.com");
	}
	
	
	@Test
	public void testWindowTitleIsCorrect() {
		JavascriptExecutor js = (JavascriptExecutor) driverChrome;
		var title = js.executeScript("return document.title");
		assertEquals("Google", title);
	}
	
	@After
	public void TearDown() {
		driverChrome.close();
	}

}
