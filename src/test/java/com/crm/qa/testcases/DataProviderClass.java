package com.crm.qa.testcases;

import java.io.IOException;

import org.testng.annotations.*;

import com.crm.qa.base.BaseTest;
import com.crm.qa.util.TestUtil;

public class DataProviderClass extends BaseTest {
	static final String testDataPath = ".//src/main/java/com/crm/qa/testdata/TestData.xlsx" ;
	static final String sheetName = "RepositoryNames";
	///com.myCRMTest-master/src/main/java/com/crm/qa/testdata/TestData.xlsx
	
	@DataProvider(name ="getSearchRepo")
	public Object [][] getSearchRepositoriesName() throws IOException{
	  return TestUtil.getExcelData(testDataPath, sheetName);
		//Object arr [][] = new Object[][] {{"amber123"},{"naresh_ROR"}};
		//return arr;
	}

}
