package TestCases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


public class driveTest3 
{
	static WebDriver driver;
	static JavascriptExecutor js = (JavascriptExecutor) driver;
	static WebElement Loading;
	public static void enter(String element,String data) throws IOException
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try 
		{
			WebElement name = driver.findElement(By.cssSelector(element));
			name.click();
			name.clear();
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(element))).clear();
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(data)));
			driver.findElement(By.xpath(data)).click();
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public static boolean isFileDownloaded(String pdf, String fileName) throws Exception {
	    final int SLEEP_TIME_MILLIS = 1000;
	    File file = new File(pdf+"\\"+fileName);
	    final int timeout = 60* SLEEP_TIME_MILLIS;
	    int timeElapsed = 0;
	    while (timeElapsed<timeout){
	        if (file.exists()) {
	            System.out.println(fileName + " is present");
	            return true;
	        } else {
	            timeElapsed +=SLEEP_TIME_MILLIS;
	            Thread.sleep(SLEEP_TIME_MILLIS);
	        }
	    }
	    return false;
	}
	
	public static void loading()
	{
		//Loading-icon-wait
				WebElement waitElement = null;
				Wait<WebDriver> fwait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(100)).pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class);
				try 
				{
					  waitElement = fwait.until(new Function<WebDriver, WebElement>() 
					  {
					   public WebElement apply(WebDriver driver) 
					   {
					      return driver.findElement(By.cssSelector("div.loading"));
					   }
					  });
				} catch (Exception e) {}
				
				if (waitElement != null) 
				{
					  WebDriverWait wait = new WebDriverWait(driver, 60);
				      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading")));
				}
	}
	
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
	
	
	public static void main(String[] args) throws Exception
	{
		Instant start = Instant.now();
		String filePath = "D:\\git\\Selenium-Projects\\DataDriven2\\logs\\Sorting";
		String fileName = "result.txt";
		File file = new File(filePath+"\\"+fileName);
		
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh mm ss");
		String time = dateFormat.format(now);
		String pdf = "D:\\git\\Selenium-Projects\\DataDriven2\\download\\"+time+"\\";
		File filenew = new File(pdf);
		filenew.mkdir();
		
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", pdf);
		options.setExperimentalOption("prefs", prefs);
		
		System.setProperty("webdriver.chrome.driver","D:\\software\\Selenium\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		//implicit-wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Explicit-wait
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Actions action = new Actions(driver);
		output(file,"Browser opened and maximized");
		
		driver.get("https://demo.admanagerplus.com/");
		output(file,"URL opened");
		report("webpage",".png");
		
		//Administrator-button
		driver.findElement(By.partialLinkText("Administrat")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='TABS_AREA']")));
		output(file,"Logged in Adminstrator");	
		report("administrator",".png");
		
		//Delegation-button
		WebElement topMenu = driver.findElement(By.id("top-menu"));
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Delegation')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='module_7003']/a[1]")));
		output(file,"Logged in Delegation");
		report("Delegation",".png");
		
		//Audit-Report-Button
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//li[@id='module_7003']/a[1]"))).click().build().perform();
		loading();
		output(file,"Audit report opened");
		report("Audit report",".png");
		
		
		//Sorting
		loading();
		action.moveToElement(driver.findElement(By.name("daterange"))).click().perform();
	    action.moveToElement(driver.findElement(By.cssSelector("#mCSB_9_container > li:nth-child(6)"))).click().perform();
	    driver.findElement(By.id("generateReport")).click();
	    loading();
	    
	    //Remove-Attribute
	    Thread.sleep(10000);
	    action.moveToElement(driver.findElement(By.cssSelector("i.admp-icon.icn-addRemove"))).click().perform();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='OK'])[1]")));
	    WebElement status = driver.findElement(By.cssSelector("li[value='807']"));
	    WebElement actionname = driver.findElement(By.cssSelector("li[value='801']")); 
	    action.keyDown(Keys.CONTROL).moveToElement(status).click().moveToElement(actionname).click().keyUp(Keys.CONTROL).perform();
	    action.moveToElement(driver.findElement(By.cssSelector("i.admp-icon.icn-backward"))).click().perform();
	    action.moveToElement(driver.findElement(By.xpath("(//button[text()='OK'])[1]"))).click().perform();
	    loading();
	    output(file,"Removed Attribute");
	    report("Removed attribute",".png");
	    Thread.sleep(10000);
	    
	    //Add-Attribute
	    action.moveToElement(driver.findElement(By.cssSelector("i.admp-icon.icn-addRemove"))).click().perform();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='OK'])[1]")));	    
	    WebElement statusadd = driver.findElement(By.xpath("//ul[@id='availableCols_1']//li[1]"));
	    WebElement actionnameadd = driver.findElement(By.xpath("//ul[@id='availableCols_1']//li[2]"));
	    action.keyDown(Keys.CONTROL).moveToElement(statusadd).click().moveToElement(actionnameadd).click().keyUp(Keys.CONTROL).perform();
	    Thread.sleep(1000);
	    action.moveToElement(driver.findElement(By.cssSelector("i.admp-icon.icn-forward"))).click().perform();
	    action.moveToElement(driver.findElement(By.xpath("(//button[text()='OK'])[1]"))).click().perform();
	    loading();
	    output(file,"Added Attribute");
	    report("Added Attribute",".png");
	    
	    //Search and export as PDF
	    Thread.sleep(10000);
	    action.moveToElement(driver.findElement(By.cssSelector("i.admp-icon.icn-table-search"))).click().perform();
	    enter("input#searchValue_auditReport_TECHNICIAN_NAME","adminuser");
	    enter("input#searchValue_auditReport_OBJECT_DOMAIN","admanagerplus.com");
	    enter("input#searchValue_auditReport_OBJECT_NAME","Dhananjayan");

	    dropdown("//tr[@id='searchCol_auditReport']//td[2]//div//button[@title='--Select Attribute--']","//span[text()='Create Users']");
	    loading();
	    dropdown("//tr[@id='searchCol_auditReport']//td[3]//div//button[@title='--Select Attribute--']","//span[text()='AD Management']");
	    loading();
	    dropdown("//tr[@id='searchCol_auditReport']//td[7]//div//button[@title='--Select Attribute--']","//span[text()='Create Single User']");
	    loading();
	    action.moveToElement(driver.findElement(By.cssSelector("i[data-original-title='Export as']"))).click().perform();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("PDF")));
	    action.moveToElement(driver.findElement(By.linkText("PDF"))).click().perform();
	    Thread.sleep(5000);
	    isFileDownloaded(pdf,"AuditReport.pdf"); 
	    
	    String path = ("D:\\git\\Selenium-Projects\\DataDriven2\\download\\"+time+"\\AuditReport.pdf");
	    File outputFile = new File(path);
		FileInputStream fis = new FileInputStream(outputFile);
		PDFParser parser = new PDFParser(fis);
		parser.parse();

		COSDocument cosDoc = parser.getDocument();
		PDDocument pdDoc = new PDDocument(cosDoc);
		
		PDFTextStripper strip = new PDFTextStripper();
		
		String data = strip.getText(pdDoc);
		//System.out.println(data);
		
		if(data.contains("Dhananjayan"))
		{
			output(file,"DATA FOUND !!");
		}
		
		Instant end = Instant.now();
		long hours = ChronoUnit.HOURS.between(start, end);
        long minutes = ChronoUnit.MINUTES.between(start, end) % 60;
        long seconds = ChronoUnit.SECONDS.between(start, end) % 60;
        output(file,"Time Taken - " + hours + " hours " + minutes+ " minutes " + seconds + " seconds.");
	}
}
