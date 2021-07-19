package TestCases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class driveTest4 
{
	static WebDriver driver;
	static JavascriptExecutor js = (JavascriptExecutor) driver;
	
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
	
	public static void pleasewait()
	{
		
		WebElement waitElement = null;
		Wait<WebDriver> fwait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(100)).pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class);
		try 
		{
			 waitElement = fwait.until(new Function<WebDriver, WebElement>() 
			 {
				 public WebElement apply(WebDriver driver) 
				 {
					 return driver.findElement(By.cssSelector("div.loading.fixed-loading"));
				 }
				 }); 	
		} catch (Exception e) {}
				
		if (waitElement != null)
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading.fixed-loading")));					
	    }
	}
	
	public static void main(String[] args) throws Exception
	{
		Instant start = Instant.now();
		String filePath = "D:\\git\\Selenium-Projects\\DataDriven2\\logs\\User Report";
		String fileName = "result.txt";
		File file = new File(filePath+"\\"+fileName);
		
		System.setProperty("webdriver.chrome.driver","D:\\software\\Selenium\\Drivers\\chromedriver.exe");
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
		output(file,"Adminstrator logged");
		report("Adminstrator logged",".png");
		
		//Report-button
		WebElement topMenu = driver.findElement(By.id("top-menu"));
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Reports')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-original-title='View all the users']")));
		output(file,"Report logged");
		report("Report logged",".png");
		
		//AllUser Report
		action.moveToElement(driver.findElement(By.cssSelector("span[data-original-title='View all the users']"))).click().build().perform();
		WebElement details = driver.findElement(By.xpath("//tbody/tr[5]/td[7]/div[1]")); 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", details);
		js.executeScript("arguments[0].click()", details);
		action.moveToElement(details).click().build().perform();
		String parentWindow = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		for(String window : windowHandles)
		{
			driver.switchTo().window(window);
		}
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		pleasewait();
		WebElement update = driver.findElement(By.cssSelector("#updateButton"));
		WebElement preview = driver.findElement(By.cssSelector("#previewButton"));
		WebElement Description = driver.findElement(By.xpath("(//span[text()='Description']/following::input)[1]"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Description']/following::input)[1]")));
		output(file,"Swtiched to all user report");
		report("Switched to all user report",".png");
		Description.clear();
		Description.sendKeys("TESTER");
		preview.sendKeys(Keys.PAGE_DOWN);
		preview.click();
		pleasewait();
		driver.findElement(By.cssSelector("a.backButton.pull-right")).click();
		preview.sendKeys(Keys.PAGE_DOWN);
		if(update.isEnabled())
		{
			update.click();
		}
		else
		{
			//System.out.println("disabled");
			//Boolean disabled = (Boolean) js.executeScript("return arguments[0].hasAttribute(\"disabled\");", update);
			//System.out.println(disabled);
			js.executeScript("arguments[0].removeAttribute(\"disabled\")", update);
			wait.until(ExpectedConditions.elementToBeClickable(update));
			update.click();
		}
		WebElement status = driver.findElement(By.cssSelector("#statusTable"));
		String result = status.getText();
		output(file,result);
		report("result message",".png");
		driver.switchTo().window(parentWindow);
		Instant end = Instant.now();
		long hours = ChronoUnit.HOURS.between(start, end);
        long minutes = ChronoUnit.MINUTES.between(start, end) % 60;
        long seconds = ChronoUnit.SECONDS.between(start, end) % 60;
        output(file,"Time Taken - " + hours + " hours " + minutes+ " minutes " + seconds + " seconds.");
	}
}
