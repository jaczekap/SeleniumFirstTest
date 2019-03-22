package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DroDownMenuTest {
	
	private WebDriver driverChrome;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driverChrome = new ChromeDriver();
		driverChrome.manage().window().maximize();
		driverChrome.get("https://magento.com/");
	}
	
	@Test
	public void testNumberOfAvailableLanguages() {
		List<WebElement> elements = getLanguages();
		
		assertEquals(6, elements.size());
	}

	@Test
	public void testAllLanguagesArePresent() {
		List<String> expectedLanguages = new ArrayList<String>( Arrays.asList("", "Français (FR)", "Deutsch (DE)", "Português (BR)", "Español (LATAM)", "日本の (JP)"));
		List<WebElement> elements = getLanguages();
		List<String> actualLanguages = elements.stream().map(WebElement::getText).collect(Collectors.toList());
		
		assertEquals(expectedLanguages, actualLanguages);
	}
	
	
	@After
	public void TearDown() {
		driverChrome.close();
	}
	
	private List<WebElement> getLanguages() {
		driverChrome.findElement(By.xpath("//*[@id=\"block-footerlanguages\"]/div/div")).click();
		WebElement language = driverChrome.findElement(By.xpath("//*[@id=\"block-footerlanguages\"]/div/ul"));
		
		List<WebElement> elements = language.findElements(By.tagName("li"));
		return elements;
	}
}
