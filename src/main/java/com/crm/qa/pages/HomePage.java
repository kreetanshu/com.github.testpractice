package com.crm.qa.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.BaseTest;
import com.crm.qa.util.TestUtil;

public class HomePage extends BaseTest {
	String searchReporBoxXpath = "//input[@id='dashboard-repos-filter-left']";
	final static String dropdownArrow = ".//span[@class='dropdown-caret']//parent::summary[@aria-label='View profile and more']";
	static String viewRepoLinkXpath= ".//a[@role='menuitem'][@class='dropdown-item']";
	final static String repositoryText = "Your repositories";
	
	public HomePage() throws IOException {
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(linkText="Explore GitHub")
	WebElement exploreGithub;
	
	@FindBy(id="dashboard-repos-filter-left")
	WebElement searchRepoBox;
	
	@FindBy(xpath =dropdownArrow)
	WebElement viewProfileDropdown;
    
	
	
	public boolean validateExploreGithubText() {
		return exploreGithub.isDisplayed();
	}
	
	public void clickProfileDropdown(){
		viewProfileDropdown.click();
	}
	
	public RepositoriesPage clickAndNavigateRepositories() throws IOException{
		clickProfileDropdown();
		TestUtil.selectBootstrapDropDownValue(viewRepoLinkXpath, repositoryText);
		return new RepositoriesPage();
	}
	public String searchRepository(String searchText){
		TestUtil.waitForElementVisiblibility(searchRepoBox, TestUtil.EXPLICIT_WAIT);
		searchRepoBox.sendKeys(searchText);
		TestUtil.changeBackgroundColorJS("yellow", searchRepoBox);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement searchLink= driver.findElement(By.xpath("//ul[@class='list-style-none filterable-active']//span[@class='css-truncate css-truncate-target'][contains(text(),'"+searchText+"')]"));
		TestUtil.waitForElementVisiblibility(searchLink, 15L);
		return searchLink.getText();
	   
	}
	

}
