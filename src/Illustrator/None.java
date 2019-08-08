package Illustrator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.WebDriver;


public class None extends Illustration
{

	//Random randomLib = new Random();
	int randomAge = ThreadLocalRandom.current().nextInt(0, 74);
	
	public None(WebDriver driver) 
	{
 		super(driver);		

	}
	
	public void setCaseRequiredFields() {
		this.setRiderOption("None");
		this.setPreparedBy("Victor Lopez");
		//this.setFirmName("tiempo development");
		//this.setEmail("tiempo.test@test.com.mx");
		//this.setPhone("1234567890");
	}
	
	public void setProfileRequiredTab(){
		this.setClientFirstName("Victor Leonardo");
		this.setClientLastName("Lopez Armenta");
		this.setClientCurrentAge(Integer.toString(randomAge));
		this.setClientGender("Male");
	}
}
