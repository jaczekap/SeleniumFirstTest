package test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Parameterized.class)
public class BmiCalculatorSimpleTest {

	private WebDriver chromeDriver;
	private WebDriverWait wait;
	private String weight;
	private String height;
	private String bmi;
	private String bmiCategory;

	@Parameters
	public static List<String[]> testData() {
		return Arrays.asList(new String[][] { 
			{ "50", "150", "22.22", "wagę prawidłową" },
			{ "40", "160", "15.63", "wygłodzenie" }, 
			{ "90", "170", "31.14", "I stopień otyłości" } 
		});
	}

	public BmiCalculatorSimpleTest(String weight, String height, String bmi, String bmiCategory) {
		this.weight = weight;
		this.height = height;
		this.bmi = bmi;
		this.bmiCategory = bmiCategory;
	}

	@Before
	public void Before() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		chromeDriver = new ChromeDriver();
		wait = new WebDriverWait(chromeDriver, 3);
		chromeDriver.manage().window().maximize();
		chromeDriver.get("http://bmi-online.pl/");
		dismissRodoWindow();
	}

	@Test
	public void testResultIsCorrect() {
		sendTestData();
		chromeDriver.findElement(By.xpath("/html/body/section/div/div[2]/div[1]/div/div/form/div[4]/div/button/span")).click();
		 assertTrue(chromeDriver.findElement(By.className("result-v1__title")).getText().contains(bmi));
		assertTrue(chromeDriver.findElement(By.className("result-v1__title-des")).getText().contains(bmiCategory));
	}

	@After
	public void TearDown() {
		chromeDriver.close();
	}

	private void dismissRodoWindow() {
		WebElement startPopupWindow = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div[3]/div/a/span")));
		if (startPopupWindow != null)
			startPopupWindow.click();
	}

	private void sendTestData() {
		List<WebElement> radioButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("bmi_gender")));
		for (WebElement button : radioButtons) {
			if (button.getAttribute("value").equals("male")) {
				JavascriptExecutor js = (JavascriptExecutor) chromeDriver;
				js.executeScript("arguments[0].click();", button);
			}
		}
		chromeDriver.findElement(By.name("weight")).sendKeys(weight);
		chromeDriver.findElement(By.name("height")).sendKeys(height);
	}
}
