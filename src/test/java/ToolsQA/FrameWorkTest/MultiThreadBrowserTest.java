package ToolsQA.FrameWorkTest;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.*;

import core.framework.Globals;
import lib.Log;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Log.Level;

public class MultiThreadBrowserTest {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;

	int browserIndex = 0;
	
	public int GetBrowserCurrentIndex()
	{
		if(browserIndex < 3)
		{
			browserIndex++;
			return browserIndex;
		}
		browserIndex = 0;
		return browserIndex;
	}
	
	@BeforeMethod
	public void SetUp(Method method)
	{
		Log.Report(Level.INFO,
				"Web Driver instance found to be inactive for the Test Case :"
						+ method.getName() + " ,hence re-initiating");
		String BrowserVar = "BROWSER" + GetBrowserCurrentIndex();
		Web.webdriver = Web.getDriver(Stock
				.getConfigParam(BrowserVar));
		Log.Report(Level.INFO,
				"Web Driver instance re-initiated successfully the Test Case :"
						+ method.getName());
	}
	
	@BeforeClass
	public void InitTest() throws Exception {
		Reporter.initializeModule(this.getClass().getName());
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), Globals.GC_MANUAL_TC_NAME);
	}

	@DataProvider(parallel = true)
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	@Test(dataProvider = "setData", threadPoolSize = 3)
	public void Test001(int itr, Map<String, String> testdata) {
		long ThreadID = Thread.currentThread().getId();
		System.out.println("Thread ID of Test is " + ThreadID);
		Web.getDriver().get(testdata.get("URL"));
		int i = 0;
		while (i < 10) {
			Web.getDriver().navigate().refresh();
			i++;
		}
	}

	// @Test(dataProvider = "setData")
	// public void Test002(int itr, Map<String, String> testdata)
	// {
	// Web.getDriver().get("http://google.com");
	// int i = 0;
	// while(i < 1000)
	// {
	// Web.getDriver().navigate().refresh();
	// }
	// }
	//
	// @Test(dataProvider = "setData")
	// public void Test003(int itr, Map<String, String> testdata)
	// {
	// Web.getDriver().get("http://yahoo.com");
	// int i = 0;
	// while(i < 1000)
	// {
	// Web.getDriver().navigate().refresh();
	// }
	// }
}
