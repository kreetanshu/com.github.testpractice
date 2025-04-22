package com.crm.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import com.crm.qa.util.TestUtil;

public class BaseTest {
	public static Properties prop;
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static TestUtil testUtil;
	public static WebDriverListener webDriverListener;
	public static EventFiringDecorator edriver;
	/*
	 * Base test class will serve as a common parent to all page lib and test class so that the common components can be written at one place and used by inheritance
	 * */
	
	// Code to load the configuration property file
	@BeforeSuite()
	public void envSetUp() throws IOException{
		prop= new Properties();
		FileInputStream file = new FileInputStream(new File(".//src/main/java/com/crm/qa/config/config.properties"));
		prop.load(file);
	}
	
	// Method to perform the pre-requisires for test: to select the browser, launch it, imlicit timeout etc.
	public static void initialization() {
		String browser= prop.getProperty("browser");
		  
		 
	       /*	System.setProperty("webdriver.gecko.driver",".\\drivers\\geckodriver.exe" );  
			        
			          // Initialize Gecko Driver using Desired Capabilities Class  
			    DesiredCapabilities capabilities = DesiredCapabilities.firefox();  
			    capabilities.setCapability("marionette",true);  
			    WebDriver dr= new FirefoxDriver(capabilities);  
			*/
		if(browser.equalsIgnoreCase("chrome")) {   
		    //System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");	
			driver= new ChromeDriver();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-data-dir=/tmp/custom_profile"); // Use a clean profile
			options.addArguments("--incognito"); // Incognito mode to avoid stored credentials
	
		}
		else if(browser.equalsIgnoreCase("firefox")){
			//System.setProperty("webdriver.gecko.driver",".\\drivers\\geckodriver.exe" );  
			driver= new FirefoxDriver();
		}
		
		else if(browser.equalsIgnoreCase("ie")){
			//System.setProperty("webdriver.ie.driver",".\\drivers\\IEDriverServer.exe" );  
			driver= new InternetExplorerDriver();
		}   
			driver.get(prop.getProperty("url"));
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);	
	}
	
}
