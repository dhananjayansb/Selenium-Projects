package Testcases;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class componentTest 
{
	@Test
	public void compTest() throws InterruptedException
	{
		//Set property
		System.setProperty("webdriver.chrome.driver","D:\\software\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","D:\\software\\Selenium\\geckodriver.exe");
		
		//Launch browser 
		WebDriver driver = new ChromeDriver();
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
				WebElement uploadbutton = driver.findElement(By.xpath("//*[@id=\"file-upload\"]"));
				uploadbutton.sendKeys(filepath);
				WebElement upload = driver.findElement(By.id("file-submit"));
				upload.click();
				System.out.println("*********** Successfully File Uploaded test ***********");
				
		//CHECKBOX automation
		try
		{
			driver.get("https://www.seleniumeasy.com/test/basic-checkbox-demo.html");
			Thread.sleep(3000);
			WebElement check = driver.findElement(By.xpath("//*[@id=\"isAgeSelected\"]"));
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
			
			//Group check-box
			List <WebElement> Allboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
			int size = Allboxes.size();
			for(int i=0;i<size;i++)
			{
				Allboxes.get(i).click();
			}
			//JavaScript to scroll down
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			driver.findElement(By.xpath("//*[@id=\"check1\"]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id=\"check1\"]")).click();
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed Checkbox test ***********");
		
		//RADIO BUTTON automation
		try
		{
			driver.get("https://www.seleniumeasy.com/test/basic-radiobutton-demo.html");
			Thread.sleep(3000);
			WebElement radiomale = driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[1]/div[2]/label[1]/input"));
			radiomale.click();
			WebElement rcheck = driver.findElement(By.id("buttoncheck"));
			rcheck.click();
			Thread.sleep(2000);
			WebElement radiofemale = driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[1]/div[2]/label[2]/input"));
			radiofemale.click();
			rcheck.click();
			Thread.sleep(2000);
			
			//Group radio button
			WebElement rsex = driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[2]/div[2]/div[1]/label[1]/input"));
			rsex.click();
			WebElement rage = driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[2]/div[2]/div[2]/label[1]/input"));
			rage.click();
			WebElement rcheckgroup = driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[2]/div[2]/button"));
			//JavaScript to scroll down
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			rcheckgroup.click();	
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
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed Keyboard actions & Textbox test ***********");
		
		//ALERT
		try 
		{
			driver.get("https://www.seleniumeasy.com/test/javascript-alert-box-demo.html");
			WebElement alertbutton = driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[1]/div[2]/button"));
			alertbutton.click();
			Alert alert = driver.switchTo().alert();
			String alertMsg = driver.switchTo().alert().getText();
			System.out.println(alertMsg);
			Thread.sleep(2000);
			alert.accept();
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
		}
		catch(Exception e) {System.out.print(e);}
		System.out.println("*********** Successfully Completed iFrame test ***********");
		
		//Login Test using GITHUBS
		try
		{
			driver.get("https://www.github.com");
			Thread.sleep(3000);
			driver.findElement(By.xpath("/html/body/div[1]/header/div/div[2]/div[2]/div[2]/a")).click();	
			driver.findElement(By.id("login_field")).sendKeys("username");
			Thread.sleep(1000);
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("password");
			Thread.sleep(1000);
			driver.findElement(By.name("commit")).click();
			Thread.sleep(3000);
			
			//UI data comparison
			driver.get("https://www.github.com/dhananjayansb");
			Thread.sleep(3000);
			String actualName = driver.findElement(By.xpath("//*[@id=\"js-pjax-container\"]/div[2]/div/div[1]/div/div[2]/div[2]/h1/span[1]")).getText();
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
