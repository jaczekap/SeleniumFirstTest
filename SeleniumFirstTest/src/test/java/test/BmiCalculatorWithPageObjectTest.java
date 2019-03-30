package test;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pageObjects.BmiCalculatorPage;

@RunWith(Parameterized.class)
public class BmiCalculatorWithPageObjectTest {
	
	private BmiCalculatorPage page = new BmiCalculatorPage();
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
	
	public BmiCalculatorWithPageObjectTest(String weight, String height, String bmi, String bmiCategory) {
		this.weight = weight;
		this.height = height;
		this.bmi = bmi;
		this.bmiCategory = bmiCategory;
	}

	@Test
	public void testResultIsCorrect() {
		page.load();
		page.dismissRodoWindow();
		page.setGender("male");
		page.sendTestData(weight, height);
		assertTrue(page.getBmiResult().contains(bmi));
		assertTrue(page.getBmiCategory().contains(bmiCategory));
		page.close();
	}
}
