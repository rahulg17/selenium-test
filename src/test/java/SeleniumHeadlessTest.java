import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumHeadlessTest {
	
	protected final String BROWSER = "phantomjs";
	protected final String APPLICATION_URL = "http://40.76.12.174:8080/CreditCardApp/";
	
	String phantomjspath = "/opt/selenium/phantomjs/bin/phantomjs";
	//String phantomjspath = "C:/Users/HSBC/Downloads/phantomjs/bin/phantomjs.exe";
	
	WebDriver driver;
	//RemoteWebDriver driver;	
	DesiredCapabilities capabilities;
	WebDriverWait wait;
	
	// Application specific test data
	
	String userName 		= "ashukla";
	String mobileNumber 	= "99530";
	String city 			= "Lucknow";
	// String creditCardType 	= "silver";
	String expectedPage 	= "Credit Card Application Details";
	
	// Application specific elements
	
	By userNameInput = By.name("name");
	By numberInput = By.name("number");
	By cityInput = By.name("city");
	// By creditTypeList = By.name("creditCard");
	By submitButton = By.xpath("//input[@value='SUBMIT']");
	By confirmText = By.xpath("//*[contains(text(),'WELCOME')]");
	
	// Trying to make a test with hub and node in the same system that is localhost
	
	@BeforeTest
	public void setUp() throws MalformedURLException {
		//capabilities.setCapability("takesScreenshot", false);
		//capabilities.setCapability("phantomjs.binary.path", phantomjspath);
		System.setProperty("phantomjs.binary.path", phantomjspath);
		driver = new PhantomJSDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 20);
		driver.get(APPLICATION_URL);
		driver.manage().window().maximize();
	}
	
	@Test
	public void endToendTest(){
		String title = "Credit Card Application";
		Assert.assertEquals(title, driver.getTitle());
		
		// Deal with userName
		driver.findElement(userNameInput).click();
		driver.findElement(userNameInput).clear();
		driver.findElement(userNameInput).sendKeys(userName);
		
		// deal with number
		driver.findElement(numberInput).click();
		driver.findElement(numberInput).clear();
		driver.findElement(numberInput).sendKeys(mobileNumber);
		
		// deal with city
		driver.findElement(cityInput).click();
		driver.findElement(cityInput).clear();
		driver.findElement(cityInput).sendKeys(city);
		
		//deal with credit card type
		/*WebElement creditType = driver.findElement(creditTypeList);
		Select select = new Select(creditType);
		select.selectByValue(creditCardType);*/
		
		driver.findElement(submitButton).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(confirmText));
		// Validating that the next page is up
		Assert.assertTrue(driver.getPageSource().contains(expectedPage));
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
