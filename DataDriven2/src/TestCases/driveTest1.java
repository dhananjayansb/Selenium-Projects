package TestCases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.By;
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

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class driveTest1 
{
	static WebDriver driver;
	private static final String CSV = "D:\\\\git\\\\Selenium-Projects\\\\DataDriven2\\\\Data.csv";
		
	public static void pleasewait()
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
					      return driver.findElement(By.cssSelector("div.loading.fixed-loading"));
					   }
					  }); 	
				} catch (Exception e) {}
				
				if (waitElement != null) 
				{
					  WebDriverWait wait = new WebDriverWait(driver, 60);
				      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading.fixed-loading")));
				}
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
					      return driver.findElement(By.cssSelector("div.progress-box"));
					   }
					  }); 
				} catch (Exception e) {}
				
				if (waitElement != null) 
				{
					  WebDriverWait wait = new WebDriverWait(driver, 60);
				      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.progress-box")));
				}
	}
	
	public static void output(File file,String message) throws IOException
	{
		FileWriter output = new FileWriter(file,true);
		BufferedWriter buffer = new BufferedWriter(output);
		buffer.write(message+"\n");
		buffer.close();
	}
		public static void main(String[] args) throws IOException, InterruptedException, CsvValidationException 
		{
			//Logger logger = Logger.getLogger("driveTest3");
			//PropertyConfigurator.configure("Log4j.properties");
			
			
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
			//logger.info("Browser opened and maximized");
			//output("Browser opened and maximized");
			
			driver.get("https://demo.admanagerplus.com/");
			//logger.info("URL opened");
			//output("URL opened");
			
			//Administrator-button
			driver.findElement(By.partialLinkText("Administrat")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='TABS_AREA']")));
			
			//Management-button
			WebElement topMenu = driver.findElement(By.id("top-menu"));
			action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Management')]"))).click().build().perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Create Single User')]")));
			//logger.info("Logged in Management");
			
			CSVReader reader = null;
			
			reader = new CSVReader(new FileReader(CSV));
			String[] header = reader.readNext();
			String[] csv = null;
			ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
				
			while((csv = reader.readNext()) != null)
			{
				HashMap<String, String> mp = new HashMap<>();
				for(int i=0;i<header.length;i++)
				{
					mp.put(header[i], csv[i]);
				}
				array.add(mp);
			}  
			int count = 1;
			for (HashMap<String, String> hm : array)
			{
				System.out.print(count);
				String filePath = "D:\\git\\Selenium-Projects\\DataDriven2";
				String fileName = "result"+count+".txt";
				File file = new File(filePath+"\\"+fileName);
				//Create-User-button
				try
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Create Single User')]")));
					action.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'Create Single User')]"))).click().build().perform();
					pleasewait();
					//logger.info("Logged in Create User");
					output(file,"Logged in Create User");
				}
				catch(Exception e) {e.printStackTrace();}
				long startTime = System.currentTimeMillis();
				
				List<String> headlist = new ArrayList<String>(hm.keySet());
				List<String> valuelist = new ArrayList<String>(hm.values());
				 
				for (int i = 0; i < headlist.size(); i++)
				{
					String key = headlist.get(i);
					if(key.equals("First Name"))
		            {
		            	ELPage.setFirstName(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered FirstName");
		            	output(file,"Entered FirstName");
		            }
					else if(key.equals("Initials"))
		            {
		            	ELPage.setInital(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered Initial");
		            	output(file,"Entered Inital");
		            }
		            else if(key.equals("Last Name"))
		            {
		            	ELPage.setLastName(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered LastName");
		            	output(file,"Entered LastName");
		            }
		            else if(key.equals("Logon Name"))
		            {
		            	ELPage.setLogonName(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered LogonName");
		            	output(file,"Entered LogonName");
		            }
		            else if(key.equals("SAM Account Name"))
		            {
		            	ELPage.setPrewindows(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered Prewindows");
		            	output(file,"Entered Prewindows");
		            }
		            else if(key.equals("Full Name"))
		            {
		            	ELPage.setFullName(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered FullName");
		            	output(file,"Entered FullName");
		            }
		            else if(key.equals("Display Name"))
		            {
		            	ELPage.setDisplayName(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered DisplayName");
		            	output(file,"Entered DisplayName");
		            }
		            else if(key.equals("Employee ID"))
		            {
		            	ELPage.setEmployeeId(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered EmployeeId");
		            	output(file,"Entered EmployeeID");
		            }
		            else if(key.equals("Description"))
		            {
		            	ELPage.setDescription(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered Description");
		            	output(file,"Entered Description");
		            }
		            else if(key.equals("Office"))
		            {
		            	ELPage.setOffice(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered Office");
		            	output(file,"Entered Office");
		            }
		            else if(key.equals("Telephone Number"))
		            {
		            	ELPage.setPhone(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered Phone");
		            	output(file,"Entered Phone");
		            }
		            else if(key.equals("Email Address"))
		            {
		            	ELPage.setEmail(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered Email");
		            	output(file,"Entered Email");
		            }
		            else if(key.equals("Webpage"))
		            {
		            	ELPage.setWebpage(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered Web Page");
		            	output(file,"Entered WebPage");
		            }
		            else if(key.equals("Container Name"))
		            {
		            	ELPage.setContainer(String.valueOf(valuelist.get(i)));
		            	//logger.info("Entered Container value");
		            	output(file,"Entered Container Value");
		            }
				}
				
				driver.findElement(By.name("save")).sendKeys(Keys.PAGE_DOWN);
				driver.findElement(By.name("save")).click();
				String result = driver.findElement(By.id("statusTable")).getText();
				//logger.error(result);
				output(file,result);
				System.out.println("complete");
			
				long endTime = System.currentTimeMillis();
				float totalTime = (endTime - startTime)/1000F;
				//logger.info("-----------COMPLETED TEST CASE SUCCESSFULLY---------");
				output(file,"-----------COMPLETED TEST CASE SUCCESSFULLY---------");
				//logger.info("Total Page Load Time: " + totalTime + "Seconds");
				output(file,"Total Page Load Time: " + totalTime + "Seconds");
				
				//Next-user-entry-creation
				driver.findElement(By.name("cancel")).sendKeys(Keys.PAGE_DOWN);
				driver.findElement(By.name("cancel")).click();
				Thread.sleep(1000);
				driver.findElement(By.cssSelector("#alert_ok")).click();
				Thread.sleep(2000);
				count++;
			}
	}
}
