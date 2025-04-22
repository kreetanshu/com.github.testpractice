package com.crm.qa.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.apache.logging.log4j.LogManager.getLogger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.BaseTest;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;


public class LoginPageTest extends BaseTest {
	HomePage homePage;
	LoginPage loginPage;
	static String invalidUserName="shsjhhhskh";
	static String invalidPassword="hjshfs";
	static String loginErrorMessage="Incorrect username or password.";
	
	private static Logger log = getLogger(LoginPageTest.class);
	

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
