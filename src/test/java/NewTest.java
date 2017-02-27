package com.hsbc.test;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NewTest {
	protected final String BROWSER = "chrome";
	protected final String APPLICATION_URL = "http://vm-dev.eastus.cloudapp.azure.com:8080/CreditCardApp/";	
	
	RemoteWebDriver driver;	
	DesiredCapabilities capabilities;
	WebDriverWait wait;
	
	// Application specific test data
	
	String userName 		= "ashukla";
	String mobileNumber 	= "99530";
	String city 			= "Lucknow";
	String creditCardType 	= "silver";
	String expectedPage 	= "CREDIT CARD APPLICATION DETAILS";
	
	// Application specific elements
	
	By userNameInput = By.name("name");
	By numberInput = By.name("number");
	By cityInput = By.name("city");
	By creditTypeList = By.name("creditCard");
	By submitButton = By.xpath("//input[@value='SUBMIT']");
	By confirmText = By.xpath("//*[contains(text(),'WELCOME')]");
	
	@BeforeTest
	public void setup() {
		String executable = System.getProperty("user.dir") + "/lib/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", executable);
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 20);
		driver.get(APPLICATION_URL);
		driver.manage().window().maximize();
	}
	
	@Test
	public void test(){
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
