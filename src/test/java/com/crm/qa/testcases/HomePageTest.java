package com.crm.qa.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.BaseTest;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.pages.RepositoriesPage;
import com.crm.qa.util.TestUtil;

public class HomePageTest extends BaseTest {
	LoginPage loginPage;
	HomePage homePage;
	RepositoriesPage repoPage;

	final static String expectedTitle="GitHub";
	final static String expectedUrlDropdownTest="https://github.com/kreetanshu/com.github.testpractice";
	String searchReporsitoryText ="Amber123";
	
	private static Logger log = LogManager.getLogger(LoginPageTest.class);
	
	@BeforeMethod
	public void setUp() throws IOException {
		initialization(); 
		loginPage = new LoginPage();
		homePage = new HomePage();
		loginPage.signIn(prop.getProperty("username"), prop.getProperty("password"));
		log.info("login successfull");
	}
	// Test to validate the title of home page after landing
	@Test(priority=1)
	public void validateTitle() {
		Assert.assertEquals(TestUtil.getPageTitle(), expectedTitle);
		log.info("validate title successfull");	
	}
	
	// Test to validate the dropdown selection and navigation to new url
	@Test(priority=2)
	public void searchAndSelectDropdownTest() {
		homePage.selectFromDropdown();
		Assert.assertEquals(driver.getCurrentUrl(), expectedUrlDropdownTest);
		log.info("dropdown test with url navigation successfull");		
	}
	
	// Test to validate the search results, the search data is read from excel using ApacePOI library
	@Test(priority=2,alwaysRun=true,dataProvider="getSearchRepo", dataProviderClass= DataProviderClass.class)
	public void searchRepository(String searchText)
	{   searchReporsitoryText = searchText;
		String actualText= homePage.searchRepository(searchReporsitoryText);
		Assert.assertEquals(actualText,searchText);
		log.info("repository search successfull");
	}
	// Test to validate the selection and navigation to a repositories
	@Test(priority=3)
	public void navigateRepositoryPage() throws IOException
	{
		repoPage = homePage.clickAndNavigateRepositories();
		System.out.println("*****"+ driver.getTitle());
		log.info("navigation to repository page is successfull");
	}

	@AfterMethod()
	public void tearDown(ITestResult result)
	{
		driver.close();
	}
}
