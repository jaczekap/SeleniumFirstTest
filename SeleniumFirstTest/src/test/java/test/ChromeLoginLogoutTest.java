package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ChromeLoginLogoutTest {
	
	private WebDriver chromeDriver;
	private WebDriverWait wait;
	private String loginIdToTest;
	private String passwordToTest;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		chromeDriver = new ChromeDriver();
		wait = new WebDriverWait(chromeDriver, 3);
		chromeDriver.manage().window().maximize();
		chromeDriver.get("http://www.google.com");
	}
	
	@Test
	public void loginTest() {
		login();
		String TitleWithLogedMail = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"gbw\"]/div/div/div[2]/div[3]/div[1]/a"))).getAttribute("title");
		assertTrue(TitleWithLogedMail.contains(loginIdToTest));
	}
	
	@Test
	public void logoutTest() {
		login();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"gbw\"]/div/div/div[2]/div[3]/div[1]/a"))).click();
		chromeDriver.findElement(By.id("gb_71")).click();
		
		assertEquals("Zaloguj siê", chromeDriver.findElement(By.id("gb_70")).getText());
	}

	@After
	public void TearDown() {
		chromeDriver.close();
	}
	
	private void login() {
		loginIdToTest = "";
		passwordToTest = "";
		chromeDriver.findElement(By.id("gb_70")).click();
		WebElement loginId = chromeDriver.findElement(By.id("identifierId"));
		loginId.clear();
		loginId.sendKeys(loginIdToTest);
		chromeDriver.findElement(By.id("identifierNext")).click();
		WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
		password.clear();
		password.sendKeys(passwordToTest);
		chromeDriver.findElement(By.id("passwordNext")).click();
	}
}
