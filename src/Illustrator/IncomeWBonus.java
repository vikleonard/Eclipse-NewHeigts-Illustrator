package Illustrator;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.WebDriver;

public class IncomeWBonus extends Illustration
{
	//int randomAge = ThreadLocalRandom.current().nextInt(40, 74);
	
	public IncomeWBonus(WebDriver driver) 
	{
		super(driver);		
		this.setRiderOption("High Point 365 Lifetime Income Benefit w/ Bonus");
		this.setRider("With Purchase Payment Bonus [Guaranteed + Accumulation], Income after 1 year");
	}
	
	public void setCaseRequiredFields() {
	this.setPreparedBy("Victor Lopez");
	//this.setFirmName("tiempo development");
	//this.setEmail("tiempo.test@test.com.mx");
	//this.setPhone("7777777777");
	}
	
	public void setProfileRequiredTab() throws InterruptedException{
			this.setClientFirstName("Victor Leonardo");
	this.setClientLastName("Lopez Armenta");
	this.setClientGender("Male");
	}

}
