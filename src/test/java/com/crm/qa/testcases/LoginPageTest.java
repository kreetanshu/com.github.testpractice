package com.crm.qa.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.crm.qa.base.BaseTest;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;


public class LoginPageTest extends BaseTest {
	HomePage homePage;
	LoginPage loginPage;
	static String invalidUserName="shsjhhhskh";
	static String invalidPassword="hjshfs";
	static String loginErrorMessage="Incorrect username or password.";
	
	static Logger log = Logger.getLogger(LoginPageTest.class);
	

	@BeforeMethod
	public void setUp() throws IOException {
		initialization();
		loginPage = new LoginPage();
		log.info("Browser Open");
	}
	
	// Negative test for github login
	@Test(priority=1)
	public void loginErrorTest() throws InterruptedException, IOException {		
		homePage= loginPage.signIn(invalidUserName, invalidPassword);
		String actualErrorText= loginPage.getLoginErrorMsg();
		Assert.assertEquals(loginErrorMessage,actualErrorText);	
		log.info("Login error test is Successfull");
	}
	// Positive test for github login
	@Test(priority=2)
	public void loginTest() throws InterruptedException, IOException {
		homePage= loginPage.signIn(prop.getProperty("username"), prop.getProperty("password"));		
		log.info("Login Successfull");
	}

	@AfterMethod()
	public void tearDown(ITestResult result)
	{
		driver.close();
	}
	
}
