package com.crm.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.crm.qa.base.BaseTest;

public class TestUtil extends BaseTest {
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 10;
	public static long EXPLICIT_WAIT = 20;
	static Sheet sheet;
	
	static Logger log = Logger.getLogger(TestUtil.class);
	
	public static boolean isElementPresent(String locator) {
		if(driver.findElements(By.xpath(locator)).size()>0) {
			return true;	
		}
		else {
			return false;
		}
	}
	
	public static String getPageTitle() {
		return driver.getTitle();
	}
	
	public static boolean isElementVisible(String locator) {
		try {
			return driver.findElement(By.xpath(locator)).isDisplayed();
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	public static void waitForElementVisiblibility(WebElement element, Long time) {
		new WebDriverWait(driver,time).until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitForElementInVisiblibility(WebElement element, Long time) {
		new WebDriverWait(driver,time).until(ExpectedConditions.invisibilityOf(element));
	}
	
	public static void waitForElementToBeClickable(WebElement element, Long time) {
		new WebDriverWait(driver,time).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void waitForFrameAndSwitchToIt(WebElement element, Long time) {
		new WebDriverWait(driver,time).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}
	
	public static void takeScreenShot() throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File srcFile=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile,new File(System.getProperty("user.dir")+"/Screenshots/"+ System.currentTimeMillis()+".png"));
	}
	
	public static Object [][] getExcelData(String filePath, String sheetName) throws IOException{
		File file = new File(filePath);
		FileInputStream fileInput= new FileInputStream(file);
		//workbook= new XSSFWorkbook(fileInput);
		XSSFWorkbook workbook = new XSSFWorkbook(fileInput); 
		sheet= workbook.getSheet(sheetName);
	    Object [][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()]; 
	    
	    for(int i=0;i<sheet.getLastRowNum();i++){
	    	for(int j=0;j<sheet.getRow(0).getLastCellNum();j++){
	    		data[i][j]=sheet.getRow(i+1).getCell(j).toString();
	    	
	    	}
	    }
	    fileInput.close();
	    workbook.close();
	    return data;
	    
	}
	
	public static void writeExcelData(String[][] array, String filePath, String sheetName) throws IOException{
		File file = new File(filePath);
		FileInputStream fileInput= new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fileInput); 
		sheet= workbook.getSheet(sheetName);
		int newRow = sheet.getLastRowNum()+1;
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[i].length;j++){
				Cell cell= sheet.createRow(newRow+i).createCell(j);
				cell.setCellValue(array[i][j]);
			
			}
		}
		
		FileOutputStream fileOutput = new FileOutputStream(filePath);
		workbook.write(fileOutput);
		workbook.close();
		fileInput.close();
		fileOutput.close();
	}
	
	public static void selectFromDropdown(String dropdownTextXpath){
		Actions builder = new Actions(driver);
		WebElement element = driver.findElement(By.xpath(dropdownTextXpath));
		
		try {
			builder.moveToElement(element).build();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		builder.click(element).build().perform();
	}
	
	public static void selectBootstrapDropDownValue(String locatorXPath, String dowpdownText){		
		//Click on the main menu icon for dropdown
		
		List<WebElement> dropdownValues = driver.findElements(By.xpath(locatorXPath));
		System.out.println("Number of dropdown $$$$$$$$$$ " + dropdownValues.size());
		for(WebElement dropdownValue: dropdownValues){
		
			if(dropdownValue.getText().trim().equalsIgnoreCase(dowpdownText)){
				waitForElementVisiblibility(dropdownValue, TestUtil.EXPLICIT_WAIT);
				dropdownValue.click();
				break;
			}
			else{
				log.error("Dropdown value doesn't exist");
			}
		}	
	}
	
	public static void customWaitForElement(String elementXpath, int searchCount){
		for(int i=0;i<searchCount;i++)
		{
			try{
				driver.findElement(By.xpath(elementXpath));
				break;
			}
			catch(Exception e)
			{
				try 
					{
						Thread.sleep(1000);
					} catch (InterruptedException e1) 
					{
						System.out.println("Waiting for element to appear on DOM");
					}
			}		 
		}
	}
	
	public static int getHttpResponse(String linkurl) throws IOException{
		URL url = new URL(linkurl);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setConnectTimeout(3000);
		connection.connect();
		return connection.getResponseCode();
	}
	
	public static List<String> findAllBrokenLinks() throws IOException{
		List<WebElement> allLinks = driver.findElements(By.tagName("a"));
		log.info("Total Number of links"+allLinks.size());
		List<String> brokenLinks= new ArrayList<String>();
		for(WebElement link: allLinks){
			String href= link.getAttribute("href");
		
			//!href.replace("\\s","").isEmpty()
			 if(href!=null && href !="" && !href.replace("\\s","").isEmpty()){
				 if((getHttpResponse(href)!=HttpURLConnection.HTTP_NOT_FOUND)){
					 brokenLinks.add(href);
				 }
			 }	
		}
		
		return brokenLinks;
		
	}
	public static void changeBackgroundColorJS(String color, WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.backgroundColor = '"+color+"';", element);
	}
}
