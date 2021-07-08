package Testcases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class componentTest 
{
	static WebDriver driver;
    
	public static void report(String fileName,String extension) throws IOException
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("dd-MMM-yy  hh.mm aa").format(new Date());
		FileUtils.copyFile(scrFile, new File("E:/zoho/Incubation/Reports/" + fileName+" "+timestamp+extension));
	}
	@Test
	public void compTest() throws InterruptedException, IOException
	{
		//Set property
		System.setProperty("webdriver.chrome.driver","D:\\software\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","D:\\software\\Selenium\\geckodriver.exe");
		
		//Launch browser 
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		//implicit-wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		System.out.println("*********** Successfully Browser Launched ***********");
		
		//File Browse
				driver.get("http://the-internet.herokuapp.com/upload");
				String filepath = "E:\\zoho\\Incubation\\welcome.png";
				WebElement uploadbutton = driver.findElement(By.xpath("//*[@id='file-upload']"));
				uploadbutton.sendKeys(filepath);
				WebElement upload = driver.findElement(By.xpath("//*[@id='file-submit']"));
				upload.click();
				report("file-browse",".png");
				System.out.println("*********** Successfully File Uploaded test ***********");
			
		//CHECKBOX automation
		try
		{
			driver.get("https://www.seleniumeasy.com/test/basic-checkbox-demo.html");
			Thread.sleep(3000);
			WebElement check = driver.findElement(By.xpath("//*[@id='isAgeSelected']"));
			if(!check.isSelected())
			{
				check.click();
				Thread.sleep(2000);
			}
			if(check.isSelected())
			{
				check.click();
				Thread.sleep(2000);
			}
			report("check-box",".png");
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed Checkbox test ***********");
		
		//RADIO BUTTON automation
		try
		{
			driver.get("https://www.seleniumeasy.com/test/basic-radiobutton-demo.html");
			Thread.sleep(3000);
			WebElement radiomale = driver.findElement(By.xpath("//*[text()='Male']"));
			radiomale.click();
			WebElement rcheck = driver.findElement(By.id("buttoncheck"));
			rcheck.click();
			Thread.sleep(2000);
			WebElement radiofemale = driver.findElement(By.xpath("//*[text()='Female']"));
			radiofemale.click();
			rcheck.click();
			Thread.sleep(2000);
			report("radio-button",".png");
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed Radiobutton test ***********");
		
		//DROPDOWN automation
		try
		{
			driver.get("https://www.seleniumeasy.com/test/basic-select-dropdown-demo.html");
			Thread.sleep(2000);
			Select selectdrop = new Select(driver.findElement(By.id("select-demo")));
			selectdrop.selectByIndex(4);
			report("dropdown-seelct",".png");
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed Dropdown test ***********");
		
		//TextBox and Keyboard actions
		try
		{
			driver.get("https://demoqa.com/text-box");
			Actions actions = new Actions(driver);
			WebElement Username = driver.findElement(By.id("userName"));
			Username.sendKeys("DhananjayanSB");
			WebElement Email = driver.findElement(By.id("userEmail"));
			Email.sendKeys("dhananjayansb@gmail.com");
			WebElement CurrentAddress = driver.findElement(By.id("currentAddress"));
			CurrentAddress.sendKeys("138/3 Sriramavilas Thiruvanaikovil Trichy-06");
			
			//select all and all address keyboard action ctrl+a and ctrl+c
			actions.keyDown(Keys.CONTROL);
			actions.sendKeys("a");
			actions.keyUp(Keys.CONTROL);
			actions.build().perform();
			
			actions.keyDown(Keys.CONTROL);
			actions.sendKeys("c");
			actions.keyUp(Keys.CONTROL);
			actions.build().perform();
			
			actions.sendKeys(Keys.TAB);
			actions.build().perform();
			
			actions.keyDown(Keys.CONTROL);
			actions.sendKeys("v");
			actions.keyUp(Keys.CONTROL);
			actions.build().perform();
			
			actions.sendKeys(Keys.TAB);
			actions.build().perform();
			
			//explicit-wait
			WebDriverWait wait = new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
			Thread.sleep(2000);
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement button = driver.findElement(By.id("submit"));
			button.click();
			report("keyboard-action",".png");
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed Keyboard actions & Textbox test ***********");
		
		//ALERT
		try 
		{
			driver.get("http://leafground.com/pages/Alert.html");
			Thread.sleep(2000);
			WebElement alertbutton = driver.findElement(By.xpath("//button[contains(text(),'Alert Box')]"));
			alertbutton.click();
			Alert alert = driver.switchTo().alert();
			String alertMsg = driver.switchTo().alert().getText();
			System.out.println(alertMsg);
			Thread.sleep(2000);
			alert.accept();
			report("alert-msg",".png");
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed Alert test ***********");
		
		//iFrame Automation
		try 
		{
			driver.get("http://rediff.com");
			driver.switchTo().frame(0);
			String nse = driver.findElement(By.id("nseindex")).getText();
			System.out.println(nse);
			report("iframe",".png");
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed iFrame test ***********");
		
		//Login Test using GITHUBS
		try
		{
			driver.get("https://www.github.com");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//a[@href='/login']")).click();	
			driver.findElement(By.id("login_field")).sendKeys("text123@gmail.com");
			Thread.sleep(1000);
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("password");
			Thread.sleep(1000);
			driver.findElement(By.name("commit")).click();
			Thread.sleep(3000);
			report("login-test",".png");
			//UI data comparison
			driver.get("https://www.github.com/dhananjayansb");
			Thread.sleep(3000);
			String actualName = driver.findElement(By.xpath("//span[contains(text(),'Dhananjayan SB')]")).getText();
			String expectedName = "Dhananjayan SB";
			if(actualName.equals(expectedName))
			{
				driver.quit();
			}
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed Login & UI comparison test ***********");
		
	}
}
