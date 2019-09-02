package com.crm.qa.pages;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crm.qa.base.BaseTest;
import com.crm.qa.util.TestUtil;

public class LoginPage extends BaseTest{

	public LoginPage() throws IOException {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath=".//a[@href='/login']")
	WebElement signInBtn;
	
	@FindBy(id="login_field")
	WebElement username;
	
	@FindBy(id="password")
	WebElement password;
	
	@FindBy(xpath=".//input[@value='Sign in']")
	WebElement logInBtn;
	
	@FindBy(css="div[class='flash flash-full flash-error']")
	WebElement loginErrorBox;
	
	public HomePage signIn(String  uname, String pwd) throws IOException {
		signInBtn.click();
		TestUtil.waitForElementVisiblibility(username, TestUtil.EXPLICIT_WAIT);
		username.sendKeys(uname);
		password.sendKeys(pwd);
		logInBtn.click();
		return new HomePage(); 
	}
	
	public String getLoginErrorMsg(){
		TestUtil.waitForElementVisiblibility(loginErrorBox,TestUtil.EXPLICIT_WAIT);
		return loginErrorBox.getText().trim();
	}

}
