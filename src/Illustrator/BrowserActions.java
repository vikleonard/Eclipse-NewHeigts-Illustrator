package Illustrator;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



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
			 	options.addArguments("--test-type");
			 	options.addArguments("chrome.switches","--disable-extensions");
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
