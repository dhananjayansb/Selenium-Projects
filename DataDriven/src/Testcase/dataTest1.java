package Testcase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dataTest1 
{
	public static WebDriver driver;
	public static void enter(String element,String data) throws IOException
	{
		WebElement name = driver.findElement(By.xpath(element));
		name.click();
		name.clear();
		name.click();
		name.sendKeys(data);
	}
	
	public static void dropdown(String element) throws IOException, InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.findElement(By.xpath(element)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'ABR')]")));
		driver.findElement(By.xpath("//span[contains(text(),'ABR')]")).click();
	}
	
	public static void frame(String frame) throws IOException, InterruptedException
	{
		driver.findElement(By.xpath(frame)).click();
		driver.switchTo().frame("adObjects");
		driver.findElement(By.xpath("//div[@id='ADTree']/child::ul/descendant::li[5]/a/i[1]")).click();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//input[@id='popupButtonVal']")).click();
	}
	
	public static void report(String fileName,String extension) throws IOException
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("dd-MMM-yy  hh.mm aa").format(new Date());
		FileUtils.copyFile(scrFile, new File("./ScreenShot/test1/" + fileName + " "+ timestamp+extension));
	}
	
	@Test(dataProvider="ADmanager")
	public void dataDrivenTest(String name, String initial,String lastname,String logonname,String prewindows,String fullname,String displayname,String employeeid,String description,String drop,String phone,String email,String webpage,String frame,String button) throws IOException, InterruptedException
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
		
		try 
		{
			enter("//td[@id='4646formcontainer']/child::div[4]/descendant::input",name);
			report("name",".png");
			System.out.println("****Name****");
			
			enter("//td[@id='4646formcontainer']/child::div[5]/descendant::input",initial);
			report("initial",".png");
			System.out.println("****Initial****");
			
			enter("//td[@id='4646formcontainer']/child::div[6]/descendant::input",lastname);
			report("lastname",".png");
			System.out.println("****Lastname****");
			
			enter("//td[@id='4646formcontainer']/child::div[7]/descendant::input",logonname);
			report("logonname",".png");
			System.out.println("****logonname****");
			
			enter("//td[@id='4646formcontainer']/child::div[8]/descendant::input[2]",prewindows);
			report("logonname-prewindows",".png");
			System.out.println("****prewindows****");
			driver.findElement(By.name("save")).sendKeys(Keys.PAGE_DOWN);
			
			enter("//td[@id='4646formcontainer']/child::div[9]/descendant::input",fullname);
			report("fullname",".png");
			System.out.println("****fullname****");
			
			enter("//td[@id='4646formcontainer']/child::div[10]/descendant::input",displayname);
			report("displayname",".png");
			System.out.println("****displayname****");
			
			enter("//td[@id='4646formcontainer']/child::div[11]/descendant::input",employeeid);
			report("employeeid",".png");
			System.out.println("****employeeid****");
			
			enter("//td[@id='4646formcontainer']/child::div[12]/descendant::input",description);
			report("description",".png");
			System.out.println("****description****");
			
			dropdown(drop);
			report("office",".png");
			System.out.println("****office****");
			
			enter("//td[@id='4646formcontainer']/child::div[14]/descendant::input",phone);
			report("phone-number",".png");
			System.out.println("****phone-number****");
			
			enter("//td[@id='4646formcontainer']/child::div[15]/descendant::input[1]",email);
			report("email",".png");
			System.out.println("****email****");
			
			enter("//td[@id='4646formcontainer']/child::div[16]/descendant::input",webpage);
			report("webpage",".png");
			System.out.println("****webpage****");
			
			frame(frame);
			report("select-container",".png");
			System.out.println("****container****");
		}
		catch(ArrayIndexOutOfBoundsException e) {System.out.print(e);}
		
		driver.findElement(By.name(button)).click();
		report("save-profile",".png");
		System.out.println("****saved successfully !****");
	}
	
	@DataProvider(name="ADmanager")
	public Object[][] passData()
	{
		Object[][] data = new Object[1][15];
		
		data[0][1] = "Dhananjayan";
		data[0][2] = "SB";
		data[0][3] = "Basker";
		data[0][4] = "DhananjayanSB";
		data[0][5] = "DhananjayanSB";
		data[0][6] = "DhananjayanSB";
		data[0][7] = "Dhananjayan";
		data[0][8] = "zoho261098";
		data[0][9] = "Incubation-Period";
		data[0][10] = "//td[@id='4646formcontainer']/child::div[13]/descendant::div[2]/descendant::input";
		data[0][11] = "8056772048";
		data[0][12] = "dhananjayansb";
		data[0][13] = "https://www.github/dhananjayansb/";
		data[0][14] = "//td[@id='4646formcontainer']/child::div[17]/descendant::ul/descendant::li[4]/descendant::div/descendant::span";
		data[0][15] = "save";	
		return data;
	}
}
