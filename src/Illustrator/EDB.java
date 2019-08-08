package Illustrator;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.WebDriver;

public class EDB extends Illustration
{
	int randomAge = ThreadLocalRandom.current().nextInt(0, 74);
	
	public EDB(WebDriver driver) 
	{
		super(driver);		
		this.setRiderOption("High Point Enhanced Death Benefit");
		this.setRider("Without bonus");
	}
	
	public void setCaseRequiredFields() {
	String phone = "7777777777";
	this.setPreparedBy("Victor Lopez");
	//this.setFirmName("tiempo development");
	//this.setEmail("tiempo.test@test.com.mx");
	//this.setPhone(phone);
	}
	
	public void setProfileRequiredTab() throws InterruptedException{
			this.setClientFirstName("Victor Leonardo");
	this.setClientLastName("Lopez Armenta");
	this.setClientCurrentAge(Integer.toString(randomAge));
	this.setClientGender("Male");
	}
	

}
