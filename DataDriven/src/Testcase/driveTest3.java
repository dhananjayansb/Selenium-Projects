package Testcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class driveTest3 
{
	static WebDriver driver;
	public static File file;
	public static FileInputStream fis;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow cellr;
	public static XSSFCell cellc;
	static int rowsize,colsize,row,col;

	public static void main(String[] args) throws IOException
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
		
		//Management-button
		WebElement topMenu = driver.findElement(By.id("top-menu"));
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Management')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Create Single User')]")));
		logger.info("Logged in Management");
		
		//Create-User-button
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//span[contains(text(),'Create Single User')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='save']")));
		logger.info("Logged in Create User");
		
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
			for(int i = 0; i < headlist.size(); i++) 
			{
	            String check = headlist.get(i);
	            if(check.equals("First Name"))
	            {
	            	ELPage.setFirstName(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Name");
	            }
	            else if(check.equals("Initials"))
	            {
	            	ELPage.setInital(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Initial");
	            }
	            else if(check.equals("Last Name"))
	            {
	            	ELPage.setLastName(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered LastName");
	            }
	            else if(check.equals("Logon Name"))
	            {
	            	ELPage.setLogonName(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered LogonName");
	            }
	            else if(check.equals("SAM Account Name"))
	            {
	            	ELPage.setPrewindows(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Prewindows");
	            }
	            else if(check.equals("Full Name"))
	            {
	            	ELPage.setFullName(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered FullName");
	            }
	            else if(check.equals("Display Name"))
	            {
	            	ELPage.setDisplayName(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered DisplayName");
	            }
	            else if(check.equals("Employee ID"))
	            {
	            	ELPage.setEmployeeId(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered EmployeeId");
	            }
	            else if(check.equals("Description"))
	            {
	            	ELPage.setDescription(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Description");
	            }
	            else if(check.equals("Office"))
	            {
	            	ELPage.setOffice(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Office");
	            }
	            else if(check.equals("Telephone Number"))
	            {
	            	ELPage.setPhone(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Phone");
	            }
	            else if(check.equals("Email Address"))
	            {
	            	ELPage.setEmail(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Email");
	            }
	            else if(check.equals("Webpage"))
	            {
	            	ELPage.setWebpage(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Web Page");
	            }
	            else if(check.equals("Container Name"))
	            {
	            	ELPage.setContainer(String.valueOf(valuelist.get(i)));
	            	logger.info("Entered Container value");
	            } 
 
	        }
			
			driver.findElement(By.name("save")).sendKeys(Keys.PAGE_DOWN);
			driver.findElement(By.name("save")).click();
			String result = driver.findElement(By.xpath("//span[text()[normalize-space()='Error in creating user, The server is unwilling to process the request.']]")).getText();
			logger.error(result);
			System.out.println("complete");
		}
		catch(Exception e) {e.printStackTrace();}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total Page Load Time: " + totalTime + "milliseconds");
	}
}