package test;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Parameterized.class)
public class BmiCalculatorTestWithExcelFile {
	
	private WebDriver chromeDriver;
	private WebDriverWait wait;
	private String weight;
	private String height;
	private String bmi;
	private String bmiCategory;

	@Parameters
	public static List<String[]> testData() throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		List<String> temp = new ArrayList<String>();
		DataFormatter formatter = new DataFormatter();
		
		InputStream spreadsheet = new FileInputStream("src/test/resources/Bmi_Data.xls");
		Workbook dataFile = new HSSFWorkbook(spreadsheet);
		Sheet dataSheet = dataFile.getSheetAt(0);
		dataFile.close();
		
		dataSheet.forEach(r -> {temp.clear(); 
		    r.forEach(c -> temp.add(formatter.formatCellValue(c)));
		    data.add(temp.toArray(new String[temp.size()]));
		    	});
		
		return data;
	}

	public BmiCalculatorTestWithExcelFile(String weight, String height, String bmi, String bmiCategory) {
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
