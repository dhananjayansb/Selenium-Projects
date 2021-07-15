package TestCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class elementLocators 
{
	static WebDriver driver;
	
	@FindBy(xpath=("(//span[text()='First name']/following::input)[1]"))
	WebElement FirstName;
	@FindBy(xpath=("(//span[text()='Initials']/following::input)[1]"))
	WebElement Initial;
	@FindBy(xpath=("(//span[text()='Last name']/following::input)[1]"))
	WebElement LastName;
	@FindBy(xpath=("(//span[text()='Logon Name']/following::input)[1]"))
	WebElement LogonName;
	@FindBy(xpath=("(//li[@class='field-input-li']//input)[2]"))
	WebElement Prewindows;
	@FindBy(xpath=("(//span[text()='Full name']/following::input)[1]"))
	WebElement FullName;
	@FindBy(xpath=("(//span[text()='Display name']/following::input)[1]"))
	WebElement DisplayName;
	@FindBy(xpath=("(//span[text()='Employee ID']/following::input)[1]"))
	WebElement EmployeeId;
	@FindBy(xpath=("(//span[text()='Description']/following::input)[1]"))
	WebElement Description;
	@FindBy(xpath=("(//div[@title='-- Select/specify a value --'])[2]"))
	WebElement Office;
	@FindBy(xpath=("(//span[text()='Telephone number']/following::input)[1]"))
	WebElement Phone;
	@FindBy(xpath=("(//span[text()='E-mail']/following::input)[1]"))
	WebElement Email;
	@FindBy(xpath=("(//span[text()='Web page']/following::input)[1]"))
	WebElement Webpage;
	@FindBy(xpath=("(//div[@class='input-group pull-left']//span)[1]"))
	WebElement Container;
	
	
	elementLocators(WebDriver d)
	{
		//assign driver
		driver = d;
		PageFactory.initElements(d, this);//PageFactory method
	}
	
	public void setFirstName(String firstName) throws IOException
	{
		enter(FirstName,firstName);
	}
	public void setInital(String initial) throws IOException
	{
		enter(Initial,initial);
	}
	public void setLastName(String lastName) throws IOException
	{
		enter(LastName,lastName);
	}
	public void setLogonName(String logonName) throws IOException
	{
		enter(LogonName,logonName);
	}
	public void setPrewindows(String prewindows) throws IOException
	{
		enter(Prewindows,prewindows);
	}
	public void setFullName(String fullName) throws IOException
	{
		enter(FullName,fullName);
	}
	public void setDisplayName(String displayName) throws IOException
	{
		enter(DisplayName,displayName);
	}
	public void setEmployeeId(String employeeid) throws IOException
	{
		enter(EmployeeId,employeeid);
	}
	public void setDescription(String description) throws IOException
	{
		enter(Description,description);
	}
	public void setOffice(String office) throws IOException, InterruptedException
	{
		dropdown(Office,office);
	}
	public void setPhone(String phone) throws IOException
	{
		enter(Phone,phone);
	}
	public void setEmail(String email) throws IOException
	{
		enter(Email,email);
	}
	public void setWebpage(String webpage) throws IOException
	{
		enter(Webpage,webpage);
	}
	public void setContainer(String container) throws IOException, InterruptedException
	{
		frame(Container,container);
	}
	
	public static void enter(WebElement element,String data) throws IOException
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try 
		{
			element.click();
			element.clear();
			wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
			element.click();
			element.sendKeys(data);
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public static void dropdown(WebElement office2,String data) throws IOException, InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try
		{
			office2.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@orgval='"+data+"']")));
			driver.findElement(By.xpath("//label[@orgval='"+data+"']")).click();
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public static void frame(WebElement frame,String data) throws IOException, InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try
		{
			frame.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='popupButtonVal']")));
			driver.switchTo().frame("adObjects");
			driver.findElement(By.xpath("//li[@id='OU="+data+",DC=admanagerplus,DC=com']//a[1]")).click();
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//input[@id='popupButtonVal']")).click();
		}
		catch(Exception e) {e.printStackTrace();}
	}

}

