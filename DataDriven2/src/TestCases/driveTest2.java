package TestCases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class driveTest2 
{
	static WebDriver driver;
	static WebDriverWait wait;
		
	public static void report(String fileName,String extension) throws IOException
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("dd-MMM-yy  hh.mm aa").format(new Date());
		FileUtils.copyFile(scrFile, new File("./ScreenShot/userReport/" + fileName + " "+ timestamp+extension));
	}
	
	public static void output(File file,String message) throws IOException
	{
		FileWriter output = new FileWriter(file,true);
		BufferedWriter buffer = new BufferedWriter(output);
		buffer.write(message+"\n");
		buffer.close();
	}
	
	public static boolean windowCheck(String check) throws InterruptedException
		{
			Set<String> windowHandles = driver.getWindowHandles();
			List<String> windows = new ArrayList<String>(windowHandles); 
			
			if(check.equals("Buy Now"))
			{
				driver.switchTo().window(windows.get(1));
				String actualURL = driver.getCurrentUrl();
				String expectedURL = "https://store.manageengine.com/ad-manager/?src=admanagerplusproduct";
				String actualElement = "View Cart";
				String expectedElement = driver.findElement(By.xpath("//span[contains(text(),\"View Cart\")]")).getText();
				if((actualURL.equals(expectedURL)) || (actualElement.equals(expectedElement)))
				{
					Thread.sleep(3000);
					driver.switchTo().window(windows.get(0));
					return true;
				}
			}
			
			else if(check.equals("Get Quote"))
			{
				driver.switchTo().window(windows.get(2));
				String actualURL = driver.getCurrentUrl();
				String expectedURL = "https://www.manageengine.com/products/ad-manager/get-quote.html?src=admanagerplusproduct";
				String actualElement = "Get Quote";
				String expectedElement = driver.findElement(By.xpath("(//a[@title='Get Quote'])[1]")).getText();
				if((actualURL.equals(expectedURL)) || (actualElement.equals(expectedElement)))
				{
					Thread.sleep(3000);
					driver.switchTo().window(windows.get(0));
					return true;
				}
			}
			
			else if(check.equals("Pricing"))
			{
				driver.switchTo().window(windows.get(3));
				String actualURL = driver.getCurrentUrl();
				String expectedURL = "https://www.manageengine.com/products/ad-manager/pricing-details.html?src=admanagerplusproduct";
				String actualElement = "pricing-details";
				String expectedElement = driver.findElement(By.cssSelector("h2#pricing-details")).getText();
				if((actualURL.equals(expectedURL)) || (actualElement.equals(expectedElement)))
				{
					Thread.sleep(3000);
					driver.switchTo().window(windows.get(0));
					return true;
				}
			}
			return false;
		}
		public static void main(String[] args) throws IOException, InterruptedException 
		{
			Instant start = Instant.now();
			String filePath = "D:\\git\\Selenium-Projects\\DataDriven2\\logs\\URL";
			String fileName = "result.txt";
			File file = new File(filePath+"\\"+fileName);
		
			Logger logger = Logger.getLogger("driveTest3");
			PropertyConfigurator.configure("Log4j.properties");
			System.setProperty("webdriver.chrome.driver","D:\\software\\Selenium\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
			//implicit-wait
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//Explicit-wait
			wait = new WebDriverWait(driver, 30);
			Actions action = new Actions(driver);
			output(file,"Browser opened and maximized");
			
			//URL
			driver.get("https://demo.admanagerplus.com/");
			output(file,"URL opened");
			
			//Administrator-button
			driver.findElement(By.partialLinkText("Administrat")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='TABS_AREA']")));
			output(file,"Logged in Adminstrator");	
			
			//Support-button
			WebElement topMenu = driver.findElement(By.id("top-menu"));
			action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Support')]"))).click().build().perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='OnlineStore']")));
			output(file,"Logged in Support");
			
			//Buy-now
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='04'])[1]")));
			WebElement buynow = driver.findElement(By.id("BuyNow"));
			buynow.click();
			String buy = buynow.getText();
			output(file,"BuyNow : "+windowCheck(buy));
			
			//Get-Quote
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='04'])[1]")));
			WebElement getquote = driver.findElement(By.id("GetQuote"));
			getquote.click();
			String quote = getquote.getText();
			output(file,"GetQuote : "+ windowCheck(quote));
			
			//Pricing
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='04'])[1]")));
			WebElement pricing = driver.findElement(By.id("Pricing"));
			pricing.click();
			String price = pricing.getText();
			output(file,"Pricing : "+ windowCheck(price));
			
			Instant end = Instant.now();
			long hours = ChronoUnit.HOURS.between(start, end);
	        long minutes = ChronoUnit.MINUTES.between(start, end) % 60;
	        long seconds = ChronoUnit.SECONDS.between(start, end) % 60;
	        output(file,"Time Taken - " + hours + " hours " + minutes+ " minutes " + seconds + " seconds.");
		}
}
