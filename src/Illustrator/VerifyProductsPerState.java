package Illustrator;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class VerifyProductsPerState {
	
	WebDriver driver = new ChromeDriver();
	
	@Test
	public void ALProduct(){
		driver.get("http://illustrator3.cloudapp.net/Illustrator?state=AL");
		ExtractProducts();	
	}
	
	
	public void ExtractProducts(){
		
		//WebElement SCP = driver.findElement(By.name("SurrenderChargePeriod"));
		
		Select dropdownProducts = new Select(driver.findElement(By.name("SurrenderChargePeriod")));		
		List<WebElement> t = dropdownProducts.getOptions();
		
		for(int i=0; i<t.size(); i++){
		System.out.println(t.get(i).getText());
	}

}
}
