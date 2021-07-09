package Testcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class driveTest1 
{
	static WebDriver driver;
	
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
		WebDriverWait wait = new WebDriverWait(driver, 30);
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
	
	public static void report(String fileName,String extension) throws IOException
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("dd-MMM-yy  hh.mm aa").format(new Date());
		FileUtils.copyFile(scrFile, new File("./ScreenShot/test2/" + fileName + " "+ timestamp+extension));
	}
	
	public static void main(String[] args) throws IOException, InterruptedException
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
		
		//Providing EXCEL --> Input
		File file = new File("D:\\git\\Selenium-Projects\\DataDriven\\Data.xlsx");
		try
		{
			FileInputStream fin = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fin);
			XSSFSheet sheet =workbook.getSheet("Sheet1");
			
			//count: rows & columns
			int rowsize = sheet.getPhysicalNumberOfRows();
			int colsize = sheet.getRow(0).getLastCellNum();
			System.out.print(colsize);
			int row;
	
			//Iterating sheet
			for(row=1;row<rowsize;row++)
			{
				XSSFRow currentrow = sheet.getRow(row);
			
				String name = currentrow.getCell(0).getStringCellValue();
				String initial = currentrow.getCell(1).getStringCellValue();
				String lastname = currentrow.getCell(2).getStringCellValue();
				String logonname = currentrow.getCell(3).getStringCellValue();
				String prewindows = currentrow.getCell(4).getStringCellValue();
				String fullname = currentrow.getCell(5).getStringCellValue();
				String displayname = currentrow.getCell(6).getStringCellValue();
				String employeeid = currentrow.getCell(7).getStringCellValue();
				String description = currentrow.getCell(8).getStringCellValue();
				String office = currentrow.getCell(9).getStringCellValue();
				long phone = (long)currentrow.getCell(10).getNumericCellValue();
				String email = currentrow.getCell(11).getStringCellValue();
				String webpage = currentrow.getCell(12).getStringCellValue();
				String container = currentrow.getCell(13).getStringCellValue();
				
				enter("(//span[text()='First name']/following::input)[1]",String.valueOf(name));
				report("name",".png");
				enter("(//span[text()='Initials']/following::input)[1]",String.valueOf(initial));
				report("initial",".png");
				enter("(//span[text()='Last name']/following::input)[1]",String.valueOf(lastname));
				report("lastname",".png");
				enter("(//span[text()='Logon Name']/following::input)[1]",String.valueOf(logonname));
				report("logonname",".png");
				enter("//input[@value='ADMANAGERPLUS\\']/following-sibling::input[1]",String.valueOf(prewindows));
				report("prewindows",".png");
				enter("(//span[text()='Full name']/following::input)[1]",String.valueOf(fullname));
				report("fullname",".png");
				enter("(//span[text()='Display name']/following::input)[1]",String.valueOf(displayname));
				report("displayname",".png");
				enter("(//span[text()='Employee ID']/following::input)[1]",String.valueOf(employeeid));
				report("employeeid",".png");
				enter("(//span[text()='Description']/following::input)[1]",String.valueOf(description));
				report("description",".png");
				dropdown("(//span[text()='Office']/following::input)[2]",String.valueOf(office));
				report("office",".png");
				enter("(//span[text()='Telephone number']/following::input)[1]",String.valueOf(phone));
				report("phone",".png");
				enter("(//span[text()='E-mail']/following::input)[1]",String.valueOf(email));
				report("email",".png");
				enter("(//span[text()='Web page']/following::input)[1]",String.valueOf(webpage));
				report("webpage",".png");
				frame("//div[@class='input-group pull-left']//span[1]",String.valueOf(container));
				report("container",".png");
				
				sheet.getRow(row).createCell(14).setCellValue("Success");
				FileOutputStream fos = new FileOutputStream(file);
				workbook.write(fos);
			}
			driver.findElement(By.name("save")).sendKeys(Keys.PAGE_DOWN);
			driver.findElement(By.name("save")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-dialog modal-size-12']//div)[1]")));
			driver.findElement(By.xpath("(//button[text()='OK'])[2]")).click();
			report("final",".png");
			System.out.print("complete");
			}
			catch(Exception e) {e.printStackTrace();}
	}
}
