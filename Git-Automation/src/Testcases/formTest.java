package Testcases;

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
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class formTest 
{
	static WebDriver driver;
	public static void report(String fileName,String extension) throws IOException
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("dd-MMM-yy  hh.mm aa").format(new Date());
		FileUtils.copyFile(scrFile, new File("E:/zoho/Incubation/Reports/Task2" + fileName+" "+timestamp+extension));
	}
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
	@Test
	public void formFillTest() throws IOException, InterruptedException
	{
		//Web Driver
		System.setProperty("webdriver.chrome.driver","D:\\software\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","D:\\software\\Selenium\\geckodriver.exe");
		System.setProperty("webdriver.gecko.driver","D:\\software\\Selenium\\msedge.exe");
		
		
		driver = new ChromeDriver();
		//driver = new FirefoxDriver();
		//driver = new EdgeDriver();
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		//implicit-wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
		//Explicit-wait
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Actions action = new Actions(driver);
		//URL
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
		//input[starts-with(@id,'input')][1]
		//Entering details
		
		enter("//td[@id='4646formcontainer']/child::div[4]/descendant::input","Dhananjayan");
		report("name",".png");
		System.out.println("****Name****");
		
		enter("//td[@id='4646formcontainer']/child::div[5]/descendant::input","SB");
		report("initial",".png");
		System.out.println("****Initial****");
		
		enter("//td[@id='4646formcontainer']/child::div[6]/descendant::input","Basker");
		report("lastname",".png");
		System.out.println("****Lastname****");
		
		enter("//td[@id='4646formcontainer']/child::div[7]/descendant::input","DhananjayanSB");
		report("logonname",".png");
		System.out.println("****logonname****");
		
		enter("//td[@id='4646formcontainer']/child::div[8]/descendant::input[2]","DhananjayanSB");
		report("logonname-prewindows",".png");
		System.out.println("****prewindows****");
		driver.findElement(By.name("save")).sendKeys(Keys.PAGE_DOWN);
		
		enter("//td[@id='4646formcontainer']/child::div[9]/descendant::input","DhananjayanSB");
		report("fullname",".png");
		System.out.println("****fullname****");
		
		enter("//td[@id='4646formcontainer']/child::div[10]/descendant::input","Dhananjayan");
		report("displayname",".png");
		System.out.println("****displayname****");
		
		enter("//td[@id='4646formcontainer']/child::div[11]/descendant::input","zoho261098");
		report("employeeid",".png");
		System.out.println("****employeeid****");
		
		enter("//td[@id='4646formcontainer']/child::div[12]/descendant::input","Incubation-Period");
		report("description",".png");
		System.out.println("****description****");
		
		dropdown("//td[@id='4646formcontainer']/child::div[13]/descendant::div[2]/descendant::input");
		report("office",".png");
		System.out.println("****office****");
		
		enter("//td[@id='4646formcontainer']/child::div[14]/descendant::input","8056772048");
		report("phone-number",".png");
		System.out.println("****phone-number****");
		
		enter("//td[@id='4646formcontainer']/child::div[15]/descendant::input[1]","dhananjayansb");
		report("email",".png");
		System.out.println("****email****");
		
		enter("//td[@id='4646formcontainer']/child::div[16]/descendant::input","https://www.github/dhananjayansb/");
		report("webpage",".png");
		System.out.println("****webpage****");
		
		frame("//td[@id='4646formcontainer']/child::div[17]/descendant::ul/descendant::li[4]/descendant::div/descendant::span");
		report("select-container",".png");
		System.out.println("****container****");
		
		driver.findElement(By.name("save")).click();
		report("save-profile",".png");
		System.out.println("****saved successfully !****");
	}
}
