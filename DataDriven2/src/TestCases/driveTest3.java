package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class driveTest3 
{
	static WebDriver driver;
	static JavascriptExecutor js = (JavascriptExecutor) driver;
	static String pdf = "D:\\git\\Selenium-Projects\\DataDriven2\\AuditReport.pdf";
	
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
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Logger logger = Logger.getLogger("driveTest4");
		PropertyConfigurator.configure("Log4j.properties");
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
		elementLocators ELPage = new elementLocators(driver);
		logger.info("Browser opened and maximized");
		
		driver.get("https://demo.admanagerplus.com/");
		logger.info("URL opened");
		
		//Administrator-button
		driver.findElement(By.partialLinkText("Administrat")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='TABS_AREA']")));
		logger.info("Logged in Adminstrator");	
		
		//Delegation-button
		WebElement topMenu = driver.findElement(By.id("top-menu"));
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Delegation')]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='module_7003']/a[1]")));
		logger.info("Logged in Delegation");
		
		//Audit-Report-Button
		action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//li[@id='module_7003']/a[1]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ResultData_auditReport")));
		logger.info("Audit report opened");
		
		//Sorting
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='admp-wid-auto']//a)[1]")));
		action.moveToElement(driver.findElement(By.name("daterange"))).click().perform();
	    action.moveToElement(driver.findElement(By.cssSelector("#mCSB_9_container > li:nth-child(6)"))).click().perform();
	    driver.findElement(By.id("generateReport")).click();
	    
	    //Remove-Attribute
	    Thread.sleep(10000);
	    action.moveToElement(driver.findElement(By.cssSelector("i.admp-icon.icn-addRemove"))).click().perform();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='OK'])[1]")));
	    WebElement status = driver.findElement(By.cssSelector("li[value='807']"));
	    WebElement actionname = driver.findElement(By.cssSelector("li[value='801']")); 
	    action.keyDown(Keys.CONTROL).moveToElement(status).click().moveToElement(actionname).click().keyUp(Keys.CONTROL).perform();
	    action.moveToElement(driver.findElement(By.cssSelector("i.admp-icon.icn-backward"))).click().perform();
	    action.moveToElement(driver.findElement(By.xpath("(//button[text()='OK'])[1]"))).click().perform();
	    logger.info("Removed Attribute");
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
	    logger.info("Added Attribute");
	    
	    //Search and export as PDF
	    Thread.sleep(10000);
	    action.moveToElement(driver.findElement(By.cssSelector("i.admp-icon.icn-table-search"))).click().perform();
	    enter("input#searchValue_auditReport_TECHNICIAN_NAME","adminuser");
	    enter("input#searchValue_auditReport_OBJECT_DOMAIN","admanagerplus.com");
	    enter("input#searchValue_auditReport_OBJECT_NAME","Dhananjayan");

	    dropdown("//tr[@id='searchCol_auditReport']//td[2]//div//button[@title='--Select Attribute--']","//span[text()='Create Users']");
	    Thread.sleep(5000);
	    dropdown("//tr[@id='searchCol_auditReport']//td[3]//div//button[@title='--Select Attribute--']","//span[text()='AD Management']");
	    Thread.sleep(5000);
	    dropdown("//tr[@id='searchCol_auditReport']//td[7]//div//button[@title='--Select Attribute--']","//span[text()='Create Single User']");
	    Thread.sleep(5000);
	    action.moveToElement(driver.findElement(By.cssSelector("i[data-original-title='Export as']"))).click().perform();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("PDF")));
	    action.moveToElement(driver.findElement(By.linkText("PDF"))).click().perform();
	    
	    
	    File file = new File(pdf);
		FileInputStream fis = new FileInputStream(file);
		PDFParser parser = new PDFParser(fis);
		parser.parse();

		COSDocument cosDoc = parser.getDocument();
		PDDocument pdDoc = new PDDocument(cosDoc);
		
		PDFTextStripper strip = new PDFTextStripper();
		
		String data = strip.getText(pdDoc);
		//System.out.println(data);
		
		if(data.contains("Dhananjayan"))
		{
			logger.info("DATA FOUND !!");
		}
	}
}
