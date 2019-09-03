package com.crm.qa.testcases;

import java.io.IOException;

import org.apache.log4j.Logger;
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

public class RepositoriesPageTest extends BaseTest {
	LoginPage loginPage;
	HomePage homePage;
	RepositoriesPage repositoriesPage;
	
   static Logger log = Logger.getLogger(RepositoriesPageTest.class);
	

	@BeforeMethod
	public void setUp() throws IOException {
		initialization();
		loginPage = new LoginPage();
		homePage = new HomePage();
		repositoriesPage = new RepositoriesPage();
		loginPage.signIn(prop.getProperty("username"), prop.getProperty("password"));
		log.info("login successfull");
	}
	@Test(priority=1)
	public void repositoryLinkCountTest() throws InterruptedException, IOException {		
		repositoriesPage = homePage.clickAndNavigateRepositories();
		int expectedCount= repositoriesPage.getRepoCountFromHeader();
		int actualCount =repositoriesPage.getRepoCountFromHeader();
		Assert.assertEquals(expectedCount, actualCount);
		log.info("Repository Count Test is successfull");
	}

	@AfterMethod()
	public void tearDown(ITestResult result)
	{
		if(ITestResult.FAILURE==result.getStatus()){
			try {
				TestUtil.takeScreenShot();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		driver.close();
	}
}
