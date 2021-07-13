package Testcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class driveTest4 
{
	static WebDriver driver;
	public static File file;
	public static FileInputStream fis;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow cellr;
	public static XSSFCell cellc;
	static int rowsize,colsize,row,col;
	
	public static boolean compare(String a, String b)
	{
		if(a.equals(b))
		{
        	return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Logger logger = Logger.getLogger("driveTest3");
		PropertyConfigurator.configure("Log4j.properties");
		System.setProperty("webdriver.chrome.driver","D:\\software\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		//implicit-wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Explicit-wait
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Actions action = new Actions(driver);
		elementLocators ELPage = new elementLocators(driver);
		logger.info("Browser opened and maximized");
		
		long startTime = System.currentTimeMillis();
		driver.get("https://demo.admanagerplus.com/");
		logger.info("URL opened");
		
		//Administrator-button
		driver.findElement(By.partialLinkText("Administrat")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='TABS_AREA']")));
		logger.info("Logged in Adminstrator");	
		
		//Delegation-button
		WebElement topMenu = driver.findElement(By.id("top-menu"));
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Delegation')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='module_7003']/a[1]")));
		logger.info("Logged in Delegation");
		
		//Audit-Report-Button
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//li[@id='module_7003']/a[1]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ResultData_auditReport")));
		logger.info("Audit report opened");

		//details-table
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ResultData_auditReport")));
		WebElement details = driver.findElement(By.xpath("//table[@id='ResultDataRows_auditReport']/tbody/tr[2]/td[9]/a[1]")); 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", details);
		js.executeScript("arguments[0].click()", details);
		logger.info("dialog opened");
		Thread.sleep(2000);
		WebElement table = driver.findElement(By.xpath("//div[@dir='ltr']//table[1]"));
		List<WebElement> allrows = table.findElements(By.xpath("//div[@dir='ltr']//table[1]//tbody//tr")); 
		int size = allrows.size();
		
		
		file = new File("D:\\git\\Selenium-Projects\\DataDriven\\Data3.xlsx");
		fis = new FileInputStream(file);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet("Sheet1");
		
		int rowsize = sheet.getPhysicalNumberOfRows();
		int colsize = sheet.getRow(0).getLastCellNum();
		int row,col;
		HashMap<String, String> data = new HashMap<String,String>();
		try
		{
			for(row=1;row<rowsize;row++)
			{
				cellr = sheet.getRow(row);
				for(col=0;col<colsize;col++)
				{
					String key = sheet.getRow(0).getCell(col).getStringCellValue();
					String value =null;
					cellc = cellr.getCell(col);
					if(cellc == null)
					{
						continue;
					}
					
					try {
							switch(cellc.getCellTypeEnum())
							{
								case STRING: value = cellc.getStringCellValue()+""; break;
								case NUMERIC: value = (long)cellc.getNumericCellValue()+""; break;
								case BOOLEAN: value =(boolean)cellc.getBooleanCellValue()+""; break;
								default: break;	
							}
						}
					catch(NullPointerException e) {e.printStackTrace();}
					
					data.put(key, value);
				}
			}
			
			List<String> headlist = new ArrayList<String>(data.keySet());
			List<String> valuelist = new ArrayList<String>(data.values());
			logger.info("Read and Stored Excel data");
			logger.info(size);
			boolean compared = false;
			try
			{
				for(int i = 0; i <=size; i++) 
				{
					for(int j=0;j<colsize;j++)
					{
			            String check = headlist.get(j);
			            //logger.info(check);
						List<WebElement> columns = allrows.get(i).findElements(By.tagName("td"));
						WebElement dataColumn = columns.get(1);
						WebElement headColumn = columns.get(0);
						String header = headColumn.getText();
						String value = dataColumn.getText();
							
						//logger.info(header);
						js.executeScript("arguments[0].scrollIntoView();",dataColumn );
						if(check.equals(header))
				        {
							if(header.equals("Email Address") || header.equals("Logon Name"))
							{
								value = value.split("@")[0];
							}
							if(header.equals("Container Name"))
							{
								String temp = value.split(",")[0];
								value = temp.split("=")[1];
							}
				            String local = String.valueOf(valuelist.get(j));
				            compared = compare(local,value);
				            if(compared == true)
				            {
				            	logger.info(check + " Present");
				            }
				            else
				            {
				            	logger.error(check+ " Absent");
				            }
				        }
					}
				}
			}
			catch(Exception e) {System.out.println(e);}
			System.out.println("complete");
		}
		catch(Exception e) {e.printStackTrace();}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total Page Load Time: " + totalTime + "milliseconds");
	}
}

