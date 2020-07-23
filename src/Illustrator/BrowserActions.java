package Illustrator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;



public class BrowserActions 
{
	
	WebDriver driverBrowser;
		
	public WebDriver browser(String browser)
	{	
		switch (browser)
		{
		 	
			case "Firefox":
			{
				driverBrowser = new FirefoxDriver();
				return driverBrowser;
			}
				
			case "Chrome":
			{
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			 	ChromeOptions options = new ChromeOptions(); 
			 	
			 	String downloadFilePath = "C:/Screenshots";
			 	Map<String, Object> prefs = new HashMap <String, Object>();
			 	prefs.put("download.prompt_for_download", false); //disable dialog popup 
			 	prefs.put("download.default_directory", downloadFilePath);
			 	options.setExperimentalOption("prefs", prefs);
			 	
//		
			 	driverBrowser = new ChromeDriver(options);
			 	return driverBrowser;
			 			
			}
			 	
			case "InternetExplorer":
			{
			    System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
				driverBrowser = new InternetExplorerDriver();
				return driverBrowser;
		    }
			
			case "Edge":
			{
			    System.setProperty("webdriver.edge.driver", "MicrosoftWebDriver.exe");
				driverBrowser = new EdgeDriver();
				return driverBrowser;
		    }
		}
		return driverBrowser;
	}

}
