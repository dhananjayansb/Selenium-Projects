package TestCases;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
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
		
		//Delegation-button
		WebElement topMenu = driver.findElement(By.id("top-menu"));
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Reports')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-original-title='View all the users']")));
		
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
		pleasewait();
		WebElement update = driver.findElement(By.cssSelector("#updateButton"));
		WebElement preview = driver.findElement(By.cssSelector("#previewButton"));
		WebElement Description = driver.findElement(By.xpath("(//span[text()='Description']/following::input)[1]"));
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
		System.out.println(result);
		driver.switchTo().window(parentWindow);
	}
}
