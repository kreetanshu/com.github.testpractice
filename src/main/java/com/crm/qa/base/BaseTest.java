package com.crm.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;

public class BaseTest {
	public static Properties prop;
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static TestUtil testUtil;
	public static WebEventListener webEventListner;
	public static EventFiringWebDriver edriver;
	
	@BeforeSuite()
	public void envSetUp() throws IOException{
		prop= new Properties();
		FileInputStream file = new FileInputStream(new File(".//src/main/java/com/crm/qa/config/config.properties"));
		prop.load(file);
	}
		
	public static void initialization() {
		String browser= prop.getProperty("browser");
		  
		 
	       /*	System.setProperty("webdriver.gecko.driver",".\\drivers\\geckodriver.exe" );  
			        
			          // Initialize Gecko Driver using Desired Capabilities Class  
			    DesiredCapabilities capabilities = DesiredCapabilities.firefox();  
			    capabilities.setCapability("marionette",true);  
			    WebDriver dr= new FirefoxDriver(capabilities);  
			*/
		if(browser.equalsIgnoreCase("chrome")) {   
		    System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");	
			driver= new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver",".\\drivers\\geckodriver.exe" );  
			driver= new FirefoxDriver();
		}
		
		else if(browser.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver",".\\drivers\\IEDriverServer.exe" );  
			driver= new InternetExplorerDriver();
		}   
		    
		    edriver = new EventFiringWebDriver(driver);
		    webEventListner = new WebEventListener();
		    edriver.register(webEventListner);
		    driver= edriver;
		    
		    //http://the-internet.herokuapp.com/
			driver.get(prop.getProperty("url"));
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
			
	}
	
}
