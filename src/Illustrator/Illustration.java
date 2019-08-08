package Illustrator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public abstract class Illustration {
	WebDriver driver;

	// CONSTRUCTOR
	public Illustration(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	//MODAL POPUP TO SELECT A STATE
	@FindBy (id = "StatesModal")
	WebElement stateModal;

	// ***************** CASE TAB ELEMENTS ****************
	@FindBy(id = "IllustrationDate")
	WebElement illustrationDate;

	@FindBy(id = "PreparedBy")
	WebElement preparedBy;

	@FindBy(id = "FirmName")
	WebElement firmName;

	@FindBy(id = "Email")
	WebElement email;

	@FindBy(id = "SingleState")
	WebElement state;

	@FindBy(id = "Phone")
	WebElement phone;

	@FindBy(name = "SurrenderChargePeriod")
	WebElement product;

	@FindBy(id = "TaxStatus")
	WebElement taxStatus;

	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/div[1]/div[5]/div/div[3]/div[1]/div[2]/div[1]/label/span[3]")
	WebElement SP500_A;

	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/div[1]/div[5]/div/div[3]/div[1]/div[2]/div[2]/label/span[3]")
	WebElement SP500_B;

	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/div[1]/div[5]/div/div[3]/div[2]/div[2]/div[1]/label/span[3]")
	WebElement EAFE_A;

	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/div[1]/div[5]/div/div[3]/div[2]/div[2]/div[2]/label/span[3]")
	WebElement EAFE_B;

	@FindBy(xpath = ".//*[@id='create_illustration']/div/div/section[1]/div[1]/div[5]/div[3]/div[3]/div[2]/div[1]/label/span[3]")
	WebElement MOZAIC_A;

	@FindBy(xpath = ".//*[@id='create_illustration']/div/div/section[1]/div[1]/div[5]/div[3]/div[3]/div[2]/div[2]/label/span[3]")
	WebElement MOZAIC_B;
	
	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/div[1]/div[5]/div/div[3]/div[3]/div[2]/div[1]/label/span[3]")
	WebElement MOZAIC_II_A;
	
	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/div[1]/div[5]/div/div[3]/div[3]/div[2]/div[2]/label/span[3]")
	WebElement MOZAIC_II_B;
	
	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/div[1]/div[5]/div/div[3]/div[4]/div[2]/div[1]/label/span[3]")
	WebElement ZEBRA_A;

	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/div[1]/div[5]/div/div[3]/div[4]/div[2]/div[2]/label/span[3]")
	WebElement ZEBRA_B;
	

	@FindBy(id = "RiderOption")
	WebElement riderOption;
	
	@FindBy (xpath = "//select[@name='Rider']")
	WebElement rider;

	@FindBy(css = "label[for='JointOrSingle-1']")
	WebElement single;

	@FindBy(css = "label[for='JointOrSingle-2']")
	WebElement joint;

	@FindBy(css = "label[for='TypeOfIllustration-1']")
	WebElement purchasePayment;

	@FindBy(css = "label[for='TypeOfIllustration-2']")
	WebElement incomeGoal;

	@FindBy(id = "PremiumIncome")
	WebElement premiumIncome;

	@FindBy(id = "MonthlyIncome")
	WebElement monthlyIncome;
	
	@FindBy (id = "income-special-case")
	WebElement continueAlertOver1m;
	
	@FindBy (xpath = "//input[@ng-disabled='!purchaseOver1M']")
	WebElement nextContinueAlertOver1m;

	@FindBy(className = "btn-cancel")
	WebElement cancel;

	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[1]/input[2]")
	WebElement nextCase;
	
	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[2]/input[3]")
	WebElement nextProfile;
	
	@FindBy(xpath = "//*[@id=\"create_illustration\"]/div/div/section[4]/input[3]")
	WebElement nextIncome;

	// ***************** CASE TAB METHODS ****************
	public void setStateModal(String randomState){
		this.stateModal.sendKeys(randomState);
		driver.findElement(By.id("ModalCreateIllustration")).click();
		
	}
	
	public void setContinueAlertOver1m() {
		
		continueAlertOver1m.click();
	}
	
	public void clickNextContinueAlertOver1m() {
		nextContinueAlertOver1m.click();
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getIllustrationDate() {
		return illustrationDate;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy.sendKeys(preparedBy);
	}

	public void setFirmName(String firmName) {
		this.firmName.sendKeys(firmName);
	}

	public void setEmail(String email) {
		this.email.sendKeys(email);
	}

	public void setState(String randomState) {
		Select stateDropdown = new Select (this.state);
		//stateDropdown.selectByValue(state);
		stateDropdown.selectByVisibleText(randomState);
		//this.state.sendKeys(state);
	}

	public void setPhone(String phone) {
		this.phone.sendKeys(phone);
	}

	public void setProduct(String product) {
		this.product.sendKeys(product);
	}

	public void setTaxStatus(String taxStatus) {
		this.taxStatus.sendKeys(taxStatus);
	}
	
	public void setRiderOption(String riderOption) {
		this.riderOption.sendKeys(riderOption);
	}
	
	public void setRider(String rider) {
		this.rider.sendKeys(rider);
	}

	public void click_SP500_A() {
		this.SP500_A.click();
	}

	public void click_SP500_B() {
		this.SP500_B.click();
	}

	public void click_EAFE_A() {
		this.EAFE_A.click();
	}

	public void click_EAFE_B() {
		this.EAFE_B.click();
	}

	public void click_MOZAIC_A() {
		this.MOZAIC_A.click();
	}

	public void click_MOZAIC_B() {
		this.MOZAIC_B.click();
	}
	
	public void click_MOZAIC_II_A(){
		this.MOZAIC_II_A.click();
	}
	
	public void click_MOZAIC_II_B(){
		this.MOZAIC_II_B.click();
	}
	
	public void click_ZEBRA_A(){
		this.ZEBRA_A.click();
	}
	
	public void click_ZEBRA_B(){
		this.ZEBRA_B.click();
	}

	public void clickSingle() {
		this.single.click();
	}

	public void clickJoint() {
		this.joint.click();
	}

	public void clickPurchaseIllustration() {
		this.purchasePayment.click();
	}

	public void clickIncomeIllustration() {
		this.incomeGoal.click();
	}

	public void setPurchasePayment(String purchasePayment) {
		this.premiumIncome.click();
		this.premiumIncome.clear();
		this.premiumIncome.sendKeys(purchasePayment);
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome.sendKeys(monthlyIncome);
	}

	// ***************** PROFILE TAB ELEMENTS ****************
	@FindBy(id = "OwnerType")
	WebElement ownerType;

	@FindBy(id = "EntityName")
	WebElement entityName;

	@FindBy(id = "ClientFirstName")
	WebElement clientFirstName;

	@FindBy(id = "ClientLastName")
	WebElement clientLastName;

	@FindBy(id = "ClientCurrentAge")
	WebElement clientCurrentAge;

	@FindBy(id = "ClientGender")
	WebElement clientGender;

	@FindBy(xpath = ".//*[@id='OwnerInput']")
	WebElement ownerInput;

	@FindBy(xpath = ".//*[@id='AnnuitantInput']")
	WebElement annuitantInput;

	@FindBy(xpath = ".//*[@id='coveredLifeInput']")
	WebElement coveredLifeInput;

	@FindBy(xpath = ".//*[@id='create_illustration']/div/div/section[2]/div/div[5]/div/a/span")
	WebElement addOwner;
	
	@FindBy(xpath = ".//*[@id='create_illustration']/div/div/section[2]/div/div[4]/div/a/span")
	WebElement addOwnerQualified;

	@FindBy(id = "JointFirstName")
	WebElement jointFirstName;

	@FindBy(id = "JointLastName")
	WebElement jointLastName;

	@FindBy(id = "JointCurrentAge")
	WebElement jointCurrentAge;

	@FindBy(id = "JointGender")
	WebElement jointGender;

	@FindBy(xpath = ".//*[@id='coOwnerInput']")
	WebElement coOwnerInput;

	@FindBy(xpath = ".//*[@id='coAnnuitantInput']")
	WebElement coAnnuitantInput;

	@FindBy(xpath = ".//*[@id='jointCoveredLifeInput']")
	WebElement jointCoveredLifeInput;

	@FindBy(className = "buttonPrevious")
	WebElement buttonPrevious;

	// ***************** PROFILE TAB METHODS ****************
	public void setOwnerType(String ownerType) {
		this.ownerType.sendKeys(ownerType);
	}

	public void setEntityName(String entityName) {
		this.entityName.sendKeys(entityName);
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName.sendKeys(clientFirstName);
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName.sendKeys(clientLastName);
	}

	public void setClientCurrentAge(String clientCurrentAge) {
		this.clientCurrentAge.click();
		this.clientCurrentAge.clear();
		this.clientCurrentAge.sendKeys(clientCurrentAge);
	}

	public void setClientGender(String clientGender) {
		this.clientGender.sendKeys(clientGender);
	}

	public void clickOwner() {
		this.ownerInput.click();
	}

	public void clickAnnuitant() {
		this.annuitantInput.click();
	}

	public void clickCoveredLife() {
		this.coveredLifeInput.click();
	}

	public void clickAddOwner() {
		this.addOwner.click();
	}
	
	public void clickAddOwnerQualified() {
		this.addOwnerQualified.click();
	}

	public void setJointFirstName(String jointFirstName) {
		this.jointFirstName.sendKeys(jointFirstName);
	}

	public void setJointLastName(String jointLastName) {
		this.jointLastName.sendKeys(jointLastName);
	}

	public void setJointCurrentAge(String jointCurrentAge) {
		this.jointCurrentAge.click();
		this.jointCurrentAge.clear();
		this.jointCurrentAge.sendKeys(jointCurrentAge);
	}

	public void setJointGender(String jointGender) {
		this.jointGender.sendKeys(jointGender);
	}

	public void clickCoOwner() {
		this.coOwnerInput.click();
	}

	public void clickCoAnnuitant() {
		this.coAnnuitantInput.click();
	}

	public void clickJointCoveredLife() {
		this.jointCoveredLifeInput.click();
	}

	// ***************** INCOME TAB ELEMENTS ****************
	@FindBy(id = "ClientIncomeStartAge")
	WebElement incomeStartAge;

	@FindBy(id = "JointIncomeStartAge")
	WebElement jointIncomeStartAge;

	@FindBy(id = "PreIncomeWithdraw")
	WebElement preIncomeWithdraw;

	@FindBy(name = "Contracts[0].MonthAnnualOption")
	WebElement monthAnnualOption;

	@FindBy(name = "Contracts[0].PercentAmountOption")
	WebElement percentAmountOption;

	//@FindBy(id = "AddMoreWithdraw")
	//WebElement addMoreWithdrawal;
	
	@FindBy(linkText = "Add another Free Partial Withdrawal")
	WebElement addMoreWithdrawal;

	@FindBy(id = "Withdrawals_0_StartAge")
	WebElement withdraws_0_StartAge;

	@FindBy(id = "Withdrawals_0_StopAge")
	WebElement withdraws_0_StopAge;

	@FindBy(name = "Contracts[0].Withdrawals[0].AmountOption")
	WebElement withdraws_0_AmountOption;

	@FindBy(name = "Contracts[0].Withdrawals[0].PercentOption")
	WebElement withdraws_0_PercentOption;

	@FindBy(id = "Withdrawals_1_StartAge")
	WebElement withdraws_1_StartAge;

	@FindBy(id = "Withdrawals_1_StopAge")
	WebElement withdraws_1_StopAge;

	@FindBy(name = "Contracts[0].Withdrawals[1].AmountOption")
	WebElement withdraws_1_AmountOption;

	@FindBy(name = "Contracts[0].Withdrawals[1].PercentOption")
	WebElement withdraws_1_PercentOption;

	@FindBy(id = "Withdrawals_2_StartAge")
	WebElement withdraws_2_StartAge;

	@FindBy(id = "Withdrawals_2_StopAge")
	WebElement withdraws_2_StopAge;

	@FindBy(name = "Contracts[0].Withdrawals[2].AmountOption")
	WebElement withdraws_2_AmountOption;

	@FindBy(name = "Contracts[0].Withdrawals[2].PercentOption")
	WebElement withdraws_2_PercentOption;

	@FindBy(id = "Withdrawals_3_StartAge")
	WebElement withdraws_3_StartAge;

	@FindBy(id = "Withdrawals_3_StopAge")
	WebElement withdraws_3_StopAge;

	@FindBy(name = "Contracts[0].Withdrawals[3].AmountOption")
	WebElement withdraws_3_AmountOption;

	@FindBy(name = "Contracts[0].Withdrawals[3].PercentOption")
	WebElement withdraws_3_PercentOption;

	@FindBy(id = "Withdrawals_4_StartAge")
	WebElement withdraws_4_StartAge;

	@FindBy(id = "Withdrawals_4_StopAge")
	WebElement withdraws_4_StopAge;

	@FindBy(name = "Contracts[0].Withdrawals[4].AmountOption")
	WebElement withdraws_4_AmountOption;

	@FindBy(name = "Contracts[0].Withdrawals[4].PercentOption")
	WebElement withdraws_4_PercentOption;

	@FindBy(id = "Withdrawals_5_StartAge")
	WebElement withdraws_5_StartAge;

	@FindBy(id = "Withdrawals_5_StopAge")
	WebElement withdraws_5_StopAge;

	@FindBy(name = "Contracts[0].Withdrawals[5].AmountOption")
	WebElement withdraws_5_AmountOption;

	@FindBy(name = "Contracts[0].Withdrawals[5].PercentOption")
	WebElement withdraws_5_PercentOption;

	@FindBy(id = "Withdrawals_6_StartAge")
	WebElement withdraws_6_StartAge;

	@FindBy(id = "Withdrawals_6_StopAge")
	WebElement withdraws_6_StopAge;

	@FindBy(name = "Contracts[0].Withdrawals[6].AmountOption")
	WebElement withdraws_6_AmountOption;

	@FindBy(name = "Contracts[0].Withdrawals[6].PercentOption")
	WebElement withdraws_6_PercentOption;
	
	@FindBy (id="up-to-seven")
	WebElement maxWithdrawals;

	// ***************** INCOME TAB METHODS ****************
	public void setIncomeStartAge(String incomeStartAge) {
		this.incomeStartAge.clear();
		this.incomeStartAge.sendKeys(incomeStartAge);
	}

	public void setJointIncomeStartAge(String jointIncomeStartAge) {
		this.jointIncomeStartAge.clear();
		this.jointIncomeStartAge.sendKeys(jointIncomeStartAge);
	}

	public void clickPreIncomeWithdraw() {
		this.preIncomeWithdraw.click();
	}

	public void setMonthAnnualOption(String monthAnnualOption) {
		this.monthAnnualOption.sendKeys(monthAnnualOption);
	}

	public void setPercentAmountOption(String percentAmountOption) {
		this.percentAmountOption.sendKeys(percentAmountOption);
	}

	public void clickAddMoreWithrawal() {
		this.addMoreWithdrawal.click();
	}
	
	public String getAddMoreWithdrawalsText()
	{
		return this.addMoreWithdrawal.getText();
	}

	public void setWithdraws_0_StartAge(String withdraws_0_StartAge) {
		this.withdraws_0_StartAge.click();
		this.withdraws_0_StartAge.clear();
		this.withdraws_0_StartAge.sendKeys(withdraws_0_StartAge);
	}

	public void setWithdraws_0_StartStopAge(String withdraws_0_StartStopAge) {
		this.withdraws_0_StopAge.click();
		this.withdraws_0_StopAge.clear();
		this.withdraws_0_StopAge.sendKeys(withdraws_0_StartStopAge);
	}

	public void setWithdraws_0_AmountOption(String withdraws_0_AmountOption) {
		this.withdraws_0_AmountOption.click();
		this.withdraws_0_AmountOption.clear();
		this.withdraws_0_AmountOption.sendKeys(withdraws_0_AmountOption);
	}

	public void setWithdraws_0_PercentOption(String withdraws_0_PercentOption) {
		this.withdraws_0_PercentOption.click();
		this.withdraws_0_PercentOption.clear();
		this.withdraws_0_PercentOption.sendKeys(withdraws_0_PercentOption);
	}

	public void setWithdraws_1_StartAge(String withdraws_1_StartAge) {
		this.withdraws_1_StartAge.click();
		this.withdraws_1_StartAge.clear();
		this.withdraws_1_StartAge.sendKeys(withdraws_1_StartAge);
	}

	public void setWithdraws_1_StartStopAge(String withdraws_1_StartStopAge) {
		this.withdraws_1_StopAge.click();
		this.withdraws_1_StopAge.clear();
		this.withdraws_1_StopAge.sendKeys(withdraws_1_StartStopAge);
	}

	public void setWithdraws_1_AmountOption(String withdraws_1_AmountOption) {
		this.withdraws_1_AmountOption.click();
		this.withdraws_1_AmountOption.clear();
		this.withdraws_1_AmountOption.sendKeys(withdraws_1_AmountOption);
	}

	public void setWithdraws_1_PercentOption(String withdraws_1_PercentOption) {
		this.withdraws_1_PercentOption.click();
		this.withdraws_1_PercentOption.clear();
		this.withdraws_1_PercentOption.sendKeys(withdraws_1_PercentOption);
	}

	public void setWithdraws_2_StartAge(String withdraws_2_StartAge) {
		this.withdraws_2_StartAge.click();
		this.withdraws_2_StartAge.clear();
		this.withdraws_2_StartAge.sendKeys(withdraws_2_StartAge);
	}

	public void setWithdraws_2_StartStopAge(String withdraws_2_StartStopAge) {
		this.withdraws_2_StopAge.click();
		this.withdraws_2_StopAge.clear();
		this.withdraws_2_StopAge.sendKeys(withdraws_2_StartStopAge);
	}

	public void setWithdraws_2_AmountOption(String withdraws_2_AmountOption) {
		this.withdraws_2_AmountOption.click();
		this.withdraws_2_AmountOption.clear();
		this.withdraws_2_AmountOption.sendKeys(withdraws_2_AmountOption);
	}

	public void setWithdraws_2_PercentOption(String withdraws_2_PercentOption) {
		this.withdraws_2_PercentOption.click();
		this.withdraws_2_PercentOption.clear();
		this.withdraws_2_PercentOption.sendKeys(withdraws_2_PercentOption);
	}

	public void setWithdraws_3_StartAge(String withdraws_3_StartAge) {
		this.withdraws_3_StartAge.click();
		this.withdraws_3_StartAge.clear();
		this.withdraws_3_StartAge.sendKeys(withdraws_3_StartAge);
	}

	public void setWithdraws_3_StartStopAge(String withdraws_3_StartStopAge) {
		this.withdraws_3_StopAge.click();
		this.withdraws_3_StopAge.clear();
		this.withdraws_3_StopAge.sendKeys(withdraws_3_StartStopAge);
	}

	public void setWithdraws_3_AmountOption(String withdraws_3_AmountOption) {
		this.withdraws_3_AmountOption.click();
		this.withdraws_3_AmountOption.clear();
		this.withdraws_3_AmountOption.sendKeys(withdraws_3_AmountOption);
	}

	public void setWithdraws_3_PercentOption(String withdraws_3_PercentOption) {
		this.withdraws_3_PercentOption.click();
		this.withdraws_3_PercentOption.clear();
		this.withdraws_3_PercentOption.sendKeys(withdraws_3_PercentOption);
	}

	public void setWithdraws_4_StartAge(String withdraws_4_StartAge) {
		this.withdraws_4_StartAge.click();
		this.withdraws_4_StartAge.clear();
		this.withdraws_4_StartAge.sendKeys(withdraws_4_StartAge);
	}

	public void setWithdraws_4_StartStopAge(String withdraws_4_StartStopAge) {
		this.withdraws_4_StopAge.click();
		this.withdraws_4_StopAge.clear();
		this.withdraws_4_StopAge.sendKeys(withdraws_4_StartStopAge);
	}

	public void setWithdraws_4_AmountOption(String withdraws_4_AmountOption) {
		this.withdraws_4_AmountOption.click();
		this.withdraws_4_AmountOption.clear();
		this.withdraws_4_AmountOption.sendKeys(withdraws_4_AmountOption);
	}

	public void setWithdraws_4_PercentOption(String withdraws_4_PercentOption) {
		this.withdraws_4_PercentOption.click();
		this.withdraws_4_PercentOption.clear();
		this.withdraws_4_PercentOption.sendKeys(withdraws_4_PercentOption);
	}

	public void setWithdraws_5_StartAge(String withdraws_5_StartAge) {
		this.withdraws_5_StartAge.click();
		this.withdraws_5_StartAge.clear();
		this.withdraws_5_StartAge.sendKeys(withdraws_5_StartAge);
	}

	public void setWithdraws_5_StartStopAge(String withdraws_5_StartStopAge) {
		this.withdraws_5_StopAge.click();
		this.withdraws_5_StopAge.clear();
		this.withdraws_5_StopAge.sendKeys(withdraws_5_StartStopAge);
	}

	public void setWithdraws_5_AmountOption(String withdraws_5_AmountOption) {
		this.withdraws_5_AmountOption.click();
		this.withdraws_5_AmountOption.clear();
		this.withdraws_5_AmountOption.sendKeys(withdraws_5_AmountOption);
	}

	public void setWithdraws_5_PercentOption(String withdraws_5_PercentOption) {
		this.withdraws_5_PercentOption.click();
		this.withdraws_5_PercentOption.clear();
		this.withdraws_5_PercentOption.sendKeys(withdraws_5_PercentOption);
	}

	public void setWithdraws_6_StartAge(String withdraws_6_StartAge) {
		this.withdraws_6_StartAge.click();
		this.withdraws_6_StartAge.clear();
		this.withdraws_6_StartAge.sendKeys(withdraws_6_StartAge);
	}

	public void setWithdraws_6_StartStopAge(String withdraws_6__StartStopAge) {
		this.withdraws_6_StopAge.click();
		this.withdraws_6_StopAge.clear();
		this.withdraws_6_StopAge.sendKeys(withdraws_6__StartStopAge);
	}

	public void setWithdraws_6_AmountOption(String withdraws_6__AmountOption) {
		this.withdraws_6_AmountOption.click();
		this.withdraws_6_AmountOption.clear();
		this.withdraws_6_AmountOption.sendKeys(withdraws_6__AmountOption);
	}

	public void setWithdraws_6_PercentOption(String withdraws_6__PercentOption) {
		this.withdraws_6_PercentOption.click();
		this.withdraws_6_PercentOption.clear();
		this.withdraws_6_PercentOption.sendKeys(withdraws_6__PercentOption);
	}
	
	
	
	public String getMaxWithdrawalsText(){
		return this.maxWithdrawals.getText();
		
	}

	// ***************** OUTPUTS TAB ELEMENTS ****************
	//@FindBy(xpath = ".//*[@id='create_illustration']/div/div/section[5]/div/div[1]/div[2]/label[1]")
	@FindBy(css = "label[for='PreferredCreditingMethod-1']")
	WebElement strongest;
	
	@FindBy(css = "label[for='PreferredCreditingMethod-3']")
	WebElement backcastedMethod;
	
	@FindBy (xpath = ".//*[@id='step-4']/div/div[3]/div/label[4]")
	WebElement backcastedOutput;

	@FindBy(css = "label[for='PreferredCreditingMethod-2']")
	WebElement levelAssumed;

	@FindBy(id = "OverrideFixed")
	WebElement overrideFixed;

	@FindBy(name = "Contracts[0].RateIllustration")
	WebElement rateIllustration;

	@FindBy(css = "label[for='PreferredCreditingMethod-4']")
	WebElement variableAssumed;

	@FindBy(css = "label[for='VariableAssumedDiscreteIndex-1']")
	WebElement variableAssumed1;

	@FindBy(css = "label[for='VariableAssumedDiscreteIndex-2']")
	WebElement variableAssumed2;

	@FindBy(css = "label[for='VariableAssumedDiscreteIndex-3']")
	WebElement variableAssumed3;
	
	@FindBy(xpath=".//*[@id='step-4']/div/div[3]/div/label[4]")
	WebElement LABackcasted;

	@FindBy(xpath = ".//*[@id='create_illustration']/div/div/section[5]/div/div[3]/div[2]/label")
	WebElement creditingOutputMostrecent10years;

	@FindBy(css = "input[value='Save']")
	WebElement save;

	// ***************** OUTPUTS TAB METHODS ****************
	public void clickStrongest() {
		this.strongest.click();
	}
	
	//For NO NAIC illustrations
	public void clickBackcastedOutput() {
		this.backcastedOutput.click();
	}
	
	public void clickBackcastedMethod(){
		this.backcastedMethod.click();
	}

	public void clickLevelAssumed() {
		this.levelAssumed.click();
	}

	public void clickOverrideFixed() {
		this.overrideFixed.click();
	}

	public void setRateIllustration(String rateIllustration) {
		Select rateLevelAssumed = new Select(this.rateIllustration);
		rateLevelAssumed.selectByVisibleText(rateIllustration);
	}

	public void clickVariableAssumed() {
		this.variableAssumed.click();
	}

	public void clickVariableAssumed1() {
		this.variableAssumed1.click();
	}

	public void clickVariableAssumed2() {
		this.variableAssumed2.click();
	}

	public void clickVariableAssumed3() {
		this.variableAssumed3.click();
	}

	public void clickLABackcasted(){
		this.backcastedMethod.click();
	}

	// ***************** REPORTS TAB ELEMENTS ****************
	@FindBy(className = "buttonSaveStart")
	WebElement startOver;

	@FindBy(linkText = "Preview Base Output")
	WebElement previewReport;

	@FindBy(linkText = "Print Full Illustration")
	WebElement pdfReport;

	// ***************** REPORTS TAB METHODS ****************
	public void clickStartOver() {
		this.startOver.click();
	}

	// ***************** PREVIEW REPORT ELEMENTS ****************
	@FindBy(linkText = "Download Complete Illustration PDF")
	WebElement downloadPdfLink;
	
	@FindBy(xpath = "html/body/section/div/div[3]/div[1]/div[1]/img")
	WebElement nwLogoElement;
	
	@FindBy(className = "new-report-header")
	WebElement reportHeader;

	public String getDownloadPdfLinkText() {
		return downloadPdfLink.getText();
	}
	
	public void logoPresent(){
		
	}
	

	public boolean isReportHeaderDisplayed() {
		return reportHeader.isDisplayed(); 
	}	
	public void clickSave() {
		this.save.click();
	}

	public void clickCancelButton() {
		this.cancel.click();
	}

	public void clickPrevious() {
		this.buttonPrevious.click();
	}

//	public void clickNextButton() {
//		this.nextButton.click();
//	}
	public void clickNextCase() {
		this.nextCase.click();
	}
	
	public void clickNextProfile() {
		this.nextProfile.click();
	}
	
	public void clickNextIncome() {
		this.nextIncome.click();
	}

	// ***************** CLICK TABS ****************************
	@FindBy(xpath = ".//*[@id='wizard']/ul/li[1]/a/span")
	WebElement clickCaseTab;

	
	public void clickCaseTAB() {
		this.clickCaseTab.click();
	}

	
	// ***************** CLICK REPORTS ****************************
	public void clickPreviewReport() throws InterruptedException {
		this.previewReport.click();
	}
	
	public void clickPdfReport() throws InterruptedException {
		//startLoadTime = System.currentTimeMillis();
		this.pdfReport.click();
		//return startLoadTime;
	}

	public void waitTimer() throws InterruptedException {
		Thread.sleep(1000);
	}

}
