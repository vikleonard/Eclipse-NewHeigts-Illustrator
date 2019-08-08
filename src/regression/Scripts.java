package regression;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Scripts {
	
	String baseUrl = "http://localhost:53165/";
	
	WebDriver driver = new ChromeDriver();
	
	
	String multiContractStates[] = {"AK", "AL", "AR", "AZ", "CA", "CO", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "KS", "KY", "LA", 
	"MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "NE", "NH", "NJ", "NV", "OH", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "WA", "WI", "WV"};
	int contMultiContracts = 0;
	String singleContract[] = {"CT", "DE", "IA","MA", "ND", "NM", "OK", "VA", "VT", "WY"};
	int contSingleContracts = 0;
	
	//******** SCRIPT THAT CHECKS IF THE STATE IS OR NOT MULTICONTRACT. VALUES ARE GRABBED FROM SS IN THE ABOVE ARRAYS.
//	@Test
//	public void checkMultiContractStates() throws InterruptedException
//	{	
//		driver.get("http://illustratorperf.cloudapp.net/Illustrator?state=AK");	
//		
//		WebElement statesDropdown = driver.findElement(By.id("State"));
//		
//		Select stateSelected = new Select(statesDropdown);
//		
//		List<WebElement> statesList = stateSelected.getOptions();
//		
//		for (int x=0; x<statesList.size(); x++)
//		{
//			//System.out.println("State: " + states.get(x).getText());
//			stateSelected.selectByIndex(x);
//			Thread.sleep(1000);
//			 if(driver.findElements(By.name("isMultiContract")).size()>0)
//			 {
//				 System.out.println(statesList.get(x).getText() + " state is MULTI-contract");
//				 Assert.assertEquals(statesList.get(x).getText(), multiContractStates[contMultiContracts++]);
//			 }
//			 else {
//				 System.out.println(statesList.get(x).getText() + " state is SINLGE-contract");
//				 Assert.assertEquals(statesList.get(x).getText(), singleContract[contSingleContracts++]);
//			 }
//			
//		}
//	}
	
//	//******** SCRIPT THAT CHECKS THE RIDERS THAT EACH STATE HAS. ONLY NC SHOULD HAVE 2 RIDERS. ********
//	@Test// (dependsOnMethods = "checkMultiContractStates")
//	public void checkRidersPerState() throws InterruptedException
//	{
//		
//		driver.get("http://illustratorperf.cloudapp.net/Illustrator?state=AK");
//		WebElement statesDropdown = driver.findElement(By.id("State"));
//		WebElement ridersDropdown = driver.findElement(By.id("RiderOption"));
//		
//		Select stateSelected = new Select(statesDropdown);
//		Select riders = new Select(ridersDropdown);
//		
//		List<WebElement> statesList = stateSelected.getOptions();
//		System.out.println(statesList.size());
//		
//		
//		for (int x=0; x<statesList.size(); x++)
//		{
//			stateSelected.selectByIndex(x);
//			Thread.sleep(1000);
//			List<WebElement> ridersList = riders.getOptions();
//			if ((statesList.get(x).getText()).contentEquals("NC"))
//			{
//				System.out.println("NC");
//			}
//			System.out.println(statesList.get(x).getText() + " Has " + ridersList.size() + " riders");
//			
//		}
//	}
	
	@Test// (dependsOnMethods = "checkMultiContractStates")
	public void checkProductsPerState() throws InterruptedException
	{
		
		driver.get("http://illustratorperf.cloudapp.net/Illustrator?state=AK");
		WebElement statesDropdown = driver.findElement(By.id("State"));
		WebElement ridersDropdown = driver.findElement(By.id("RiderOption"));
		
		Select stateSelected = new Select(statesDropdown);
		Select riders = new Select(ridersDropdown);
		
		List<WebElement> statesList = stateSelected.getOptions();
		System.out.println(statesList.size());
		
		
		for (int x=0; x<statesList.size(); x++)
		{
			stateSelected.selectByIndex(x);
			Thread.sleep(1000);
			List<WebElement> ridersList = riders.getOptions();
			if ((statesList.get(x).getText()).contentEquals("NC"))
			{
				System.out.println("NC");
			}
			System.out.println(statesList.get(x).getText() + " Has " + ridersList.size() + " riders");
			
		}
	}

}
