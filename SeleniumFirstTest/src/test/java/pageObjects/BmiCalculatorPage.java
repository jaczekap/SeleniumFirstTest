package pageObjects;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BmiCalculatorPage {
	
	private WebDriver chromeDriver;
	private WebElement weight;
	private WebElement height;
	@FindBy(name = "bmi_gender")
	@CacheLookup
	private List<WebElement> bmi_gender;
	@FindBy(className = "result-v1__title")
	@CacheLookup
	private WebElement bmiResult;
	@FindBy(className = "result-v1__title-des")
	@CacheLookup
	private WebElement bmiCategory;
	@FindBy(xpath = "/html/body/div[3]/div[2]/div[3]/div/a/span")
	@CacheLookup
	private WebElement rodoPopUp;
	@FindBy(xpath = "/html/body/section/div/div[2]/div[1]/div/div/form/div[4]/div/button/span")
	@CacheLookup
	private WebElement calculateButton;
	private String pageUrl = "http://bmi-online.pl/";
	
	public BmiCalculatorPage() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		chromeDriver = new ChromeDriver();
		PageFactory.initElements(chromeDriver, this);
	}
	
	public void load() {
		chromeDriver.get(pageUrl);
	}
	
	public void close() {
		chromeDriver.close();
	}
	
	public void setWeight(String weight) {
		this.weight.sendKeys(weight);
	}
	
	public void setHeight(String height) {
		this.height.sendKeys(height);
	}
	
	public void sendTestData(String weight, String height) {
		this.weight.sendKeys(weight);
		this.height.sendKeys(height);
		calculateButton.click();
	}
	
	public void setGender(String gender) {
		for(WebElement genderButton : bmi_gender) {
			if (genderButton.getAttribute("value").equals(gender)) {
				JavascriptExecutor js = (JavascriptExecutor) chromeDriver;
				js.executeScript("arguments[0].click();", genderButton);
			}
		}
	}
	
	public String getBmiResult() {
		return bmiResult.getText();
	}
	
	
	public String getBmiCategory() {
		return bmiCategory.getText();
	}

	public void dismissRodoWindow() {
		if (rodoPopUp != null)
			rodoPopUp.click();
	}

}
