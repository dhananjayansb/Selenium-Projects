package TestCases;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class driveTest1 
{
	static WebDriver driver;
	private static final String CSV = "D:\\\\git\\\\Selenium-Projects\\\\DataDriven2\\\\Data.csv";
		
		public static void main(String[] args) throws IOException, InterruptedException 
		{
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
			
			//Management-button
			WebElement topMenu = driver.findElement(By.id("top-menu"));
			action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Management')]"))).click().build().perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Create Single User')]")));
			logger.info("Logged in Management");
			
			//Create-User-button
			action.moveToElement(topMenu).moveToElement(driver.findElement(By.xpath("//span[contains(text(),'Create Single User')]"))).click().build().perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='save']")));
			logger.info("Logged in Create User");
			
			File file = new File(CSV);
			byte[] bytes = FileUtils.readFileToByteArray(file);
			String data = new String(bytes);
			
			data = StringUtils.replace(data,"\r","");
			String[] dataArray = data.split("\n");
		
			String keys = dataArray[0];
			List<String> keyFromFile = new ArrayList<>();
			String[] keyArr = keys.split(",");
			ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
			
			keyFromFile.addAll(Arrays.asList(keyArr));
			keyFromFile.remove(0);
			long startTime = 0;
			for(int d=1;d<dataArray.length;d++)
			{
				HashMap<String, String> mp = new HashMap<>();
				List<String> row = new ArrayList<>();
				
				String[] rowArr = dataArray[d].split(",");
				row.addAll(Arrays.asList(rowArr));
			
				String testId = row.get(0);
				row.remove(0);
				
				//captures single row
				for(int i=0;i<keyFromFile.size();i++)
				{
					mp.put(keyFromFile.get(i).trim(),row.get(i).trim());
				}
				array.add(mp);
			}
			
			for (HashMap<String, String> hm : array)
			{
				List<String> headlist = new ArrayList<String>(hm.keySet());
				List<String> valuelist = new ArrayList<String>(hm.values());
				startTime = System.currentTimeMillis();
				for (int i = 0; i < headlist.size(); i++)
				{
					String key = headlist.get(i);
					if(key.equals("First Name"))
		            {
		            	ELPage.setFirstName(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered FirstName");
		            }
					else if(key.equals("Initials"))
		            {
		            	ELPage.setInital(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered Initial");
		            }
		            else if(key.equals("Last Name"))
		            {
		            	ELPage.setLastName(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered LastName");
		            }
		            else if(key.equals("Logon Name"))
		            {
		            	ELPage.setLogonName(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered LogonName");
		            }
		            else if(key.equals("SAM Account Name"))
		            {
		            	ELPage.setPrewindows(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered Prewindows");
		            }
		            else if(key.equals("Full Name"))
		            {
		            	ELPage.setFullName(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered FullName");
		            }
		            else if(key.equals("Display Name"))
		            {
		            	ELPage.setDisplayName(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered DisplayName");
		            }
		            else if(key.equals("Employee ID"))
		            {
		            	ELPage.setEmployeeId(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered EmployeeId");
		            }
		            else if(key.equals("Description"))
		            {
		            	ELPage.setDescription(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered Description");
		            }
		            else if(key.equals("Office"))
		            {
		            	ELPage.setOffice(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered Office");
		            }
		            else if(key.equals("Telephone Number"))
		            {
		            	ELPage.setPhone(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered Phone");
		            }
		            else if(key.equals("Email Address"))
		            {
		            	ELPage.setEmail(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered Email");
		            }
		            else if(key.equals("Webpage"))
		            {
		            	ELPage.setWebpage(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered Web Page");
		            }
		            else if(key.equals("Container Name"))
		            {
		            	ELPage.setContainer(String.valueOf(valuelist.get(i)));
		            	logger.info("Entered Container value");
		            }
				}
				
				driver.findElement(By.name("save")).sendKeys(Keys.PAGE_DOWN);
				driver.findElement(By.name("save")).click();
				String result = driver.findElement(By.id("statusTable")).getText();
				logger.error(result);
				System.out.println("complete");
			
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				logger.info("-----------COMPLETED TEST CASE SUCCESSFULLY---------");
				logger.info("Total Page Load Time: " + totalTime + "milliseconds");
			}
	}
}
