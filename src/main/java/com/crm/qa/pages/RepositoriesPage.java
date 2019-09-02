package com.crm.qa.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.BaseTest;
import com.crm.qa.util.TestUtil;

public class RepositoriesPage extends BaseTest {
	
	static String codeRepoNameXapth= "//a[@itemprop='name codeRepository']";
	final static String pageCountXpath= "//a[contains(text(),'Repositories')]//span";
	final static String nextButtonXpath= ".//div[@class='BtnGroup']//a[text()='Next']";
	
	public RepositoriesPage() throws IOException {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath =".//div[@class='BtnGroup']//a[text()='Next']")
	WebElement nextButton;
	
	@FindBy(xpath= pageCountXpath)
	WebElement repoCountHeader;
	
	public int countAllRepoLinks(){
		TestUtil.waitForElementVisiblibility(repoCountHeader, TestUtil.EXPLICIT_WAIT);
		int linkCount=0;
		Boolean nextButtonStatus=true;
		Boolean flag= true;
		while(flag){
			nextButtonStatus = TestUtil.isElementPresent(nextButtonXpath);		    
			linkCount += driver.findElements(By.xpath(codeRepoNameXapth)).size();
		
	     	if(nextButtonStatus) {			 
			nextButton.click();
		     }
		    else{
			flag=false;
		    }
		}
		return linkCount;
	}
	
	public int getRepoCountFromHeader(){
		int expectedRepoCount = Integer.parseInt(repoCountHeader.getText().trim());
		return expectedRepoCount;
	}
	
}
