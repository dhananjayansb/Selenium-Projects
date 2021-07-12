package Testcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import org.testng.annotations.Test;

 
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
	
	public static void enter(String element,String data) throws IOException
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try 
		{
			WebElement name = driver.findElement(By.xpath(element));
			name.click();
			name.clear();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element))).clear();
			name.click();
			name.sendKeys(data);
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public static void dropdown(String element,String data) throws IOException, InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try
		{
			driver.findElement(By.xpath(element)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@orgval='"+data+"']")));
			driver.findElement(By.xpath("//label[@orgval='"+data+"']")).click();
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public static void frame(String frame,String data) throws IOException, InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try
		{
			driver.findElement(By.xpath(frame)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal-header']/following-sibling::div[1]")));
			driver.switchTo().frame("adObjects");
			driver.findElement(By.xpath("//li[@id='OU="+data+",DC=admanagerplus,DC=com']//a[1]")).click();
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//input[@id='popupButtonVal']")).click();
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	@Test
	public void test3() throws IOException
	{
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
		
		driver.get("https://demo.admanagerplus.com/");
		
		//Administrator-button
		driver.findElement(By.partialLinkText("Administrat")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='TABS_AREA']")));
				
		//Management-button
		WebElement topMenu = driver.findElement(By.id("top-menu"));
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Management')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Create Single User')]")));
				
		//Create-User-button
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//span[contains(text(),'Create Single User')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='save']")));

		file = new File("D:\\git\\Selenium-Projects\\DataDriven\\Data2.xlsx");
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
			for(int i = 0; i < valuelist.size(); i++) {
	            System.out.println(valuelist.get(i));
	        }
			
			for(int i = 0; i < headlist.size(); i++) 
			{
	            String check = headlist.get(i);
	            if(check.equals("Name"))
	            {
	            	enter("(//span[text()='First name']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Initial"))
	            {
	            	enter("(//span[text()='Initials']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("LastName"))
	            {
	            	enter("(//span[text()='Last name']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Logonname"))
	            {
	            	enter("(//span[text()='Logon Name']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Prewindows"))
	            {
	            	enter("(//li[@class='field-input-li']//input)[2]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Fullname"))
	            {
	            	enter("(//span[text()='Full name']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Displayname"))
	            {
	            	enter("(//span[text()='Display name']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Employeeid"))
	            {
	            	enter("(//span[text()='Employee ID']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Description"))
	            {
	            	enter("(//span[text()='Description']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Office"))
	            {
	            	dropdown("(//div[@title='-- Select/specify a value --'])[2]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Phone"))
	            {
	            	enter("(//span[text()='Telephone number']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Email"))
	            {
	            	enter("(//span[text()='E-mail']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Webpage"))
	            {
	            	enter("(//span[text()='Web page']/following::input)[1]",String.valueOf(valuelist.get(i)));
	            }
	            else if(check.equals("Container"))
	            {
	            	frame("(//div[@class='input-group pull-left']//span)[1]",String.valueOf(valuelist.get(i)));
	            } 
 
	        }
			for(row=1;row<rowsize;row++)
			{
				sheet.getRow(row).createCell(14).setCellValue("Success");
				FileOutputStream fos = new FileOutputStream(file);
				workbook.write(fos);
			}
			driver.findElement(By.name("save")).sendKeys(Keys.PAGE_DOWN);
			driver.findElement(By.name("save")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-dialog modal-size-12']//div)[1]")));
			driver.findElement(By.xpath("(//button[text()='OK'])[2]")).click();
			System.out.print("complete");
		}
		catch(Exception e) {e.printStackTrace();}
	}
}