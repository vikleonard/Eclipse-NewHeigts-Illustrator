package Illustrator;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//import org.apache.pdfbox.exceptions.COSVisitorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestScenarios {
	// Test
	String illustratorPage;
	WebDriver driver;
	int randomJointAge = ThreadLocalRandom.current().nextInt(0, 74);
	int randomJointIncomeAge = ThreadLocalRandom.current().nextInt(40, 74);
	// static String parentWindow="";
	static String editWindow = "";
	long startLoadTime;
	long finishLoadTime;
	long totalLoadTime;
	int contScreenshots = 1;
	String browser = "";
	String[] noNaicStates = {"AR", "AZ", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "KS", "KY", "LA", "MA",
			"MD", "MI", "MT", "ND", "NE", "NH", "NJ", "NM", "SD", "TN", "VA", "VT", "WI", "WY" };
	String[] naicStates = { "AL", "CO", "ME", "RI", "WV" };
	int incomeBonusRandomAge = ThreadLocalRandom.current().nextInt(40, 74);

	public static String getBrowserName() throws ParserConfigurationException,
	SAXException, IOException {
		Document xmlBaseSettings = getXmlSettings();
		NodeList xmlBrowserList = xmlBaseSettings
				.getElementsByTagName("browser");
		Element elementBrowser = (Element) xmlBrowserList.item(0);
		return elementBrowser.getFirstChild().getNodeValue();
	}

	public static String getURLBase() throws ParserConfigurationException,
	SAXException, IOException {
		Document xmlBaseSettings = getXmlSettings();
		NodeList xmlBrowserList = xmlBaseSettings.getElementsByTagName("url");
		Element elementBrowser = (Element) xmlBrowserList.item(0);
		return elementBrowser.getFirstChild().getNodeValue();
	}

	public static Document getXmlSettings() throws SAXException, IOException,
	ParserConfigurationException {
		// Open and read xml file
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		return docBuilder.parse(new File("config.xml"));
	}

	public static void readXmlSettings() throws SAXException, IOException,
	ParserConfigurationException {
		// Open and read xml file
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document xmlBaseSettings = docBuilder.parse(new File("config.xml"));
	}

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { TestScenarios.class });
		testng.addListener(tla);
		testng.run();
	}

	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws ParserConfigurationException,
	SAXException, IOException {
		BrowserActions selectBrowser = new BrowserActions();
		System.out.println("BEFORE TEST --- BEGIN");
		// Browsers available: Chrome and InternetExplorer (Type browser name exactly as mentioned including capital letters)
		String browser = getBrowserName();
		System.out.print(browser);
		selectBrowser.browser(browser);
		this.driver = selectBrowser.driverBrowser;
		System.out.print("Scripts start at: " + LocalDateTime.now().getHour() + ":");
		System.out.println(LocalDateTime.now().getMinute());
	}

	@AfterClass
	public void end() {
		System.out.print("Scripts end at: " + LocalDateTime.now().getHour() + ":");
		System.out.println(LocalDateTime.now().getMinute());
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws ParserConfigurationException,	SAXException, IOException {
		String urlBase = getURLBase();
		driver.get(urlBase);
		//driver.manage().window().maximize();
		editWindow = driver.getWindowHandle();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
//		startLoadTime = 0;
//	    finishLoadTime = 0;
//	    totalLoadTime = 0;
	}

	@AfterMethod
	public void afterMethod() {
		//System.out.println("END METHOD");

	}

	//*********************************************** NO NAIC *********************************************************

	@Test(groups = { "None", "NoNaic", "Strongest" })
	public void TS01_NoneNoNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		WebDriverWait wait = new WebDriverWait(driver,40);

		String randomState = (noNaicStates[new Random().nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.setProduct("New Heights 9");
		none.setTaxStatus("Non-Qualified");
		none.click_SP500_A();
		none.setPurchasePayment("50000");
		
		none.clickNextCase();
		none.waitTimer();
		none.setProfileRequiredTab();
		none.clickNextProfile();		

		none.waitTimer();
		none.clickPreIncomeWithdraw();
		none.setMonthAnnualOption("Monthly");
		none.setPercentAmountOption("Percentage");
		none.waitTimer();
		none.setWithdraws_0_StartAge("1");
		none.setWithdraws_0_StartStopAge("5");
		none.setWithdraws_0_PercentOption("3");
		none.clickAddMoreWithrawal();
		none.setWithdraws_1_StartAge("6");
		none.setWithdraws_1_StartStopAge("7");
		none.setWithdraws_1_PercentOption("4");
		none.clickNextIncome();
		//none.clickNextButton();

		none.waitTimer();
		none.setEmail("vlopez@tiempo.com");
		none.clickStrongest();
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
//		none.clickPreviewReport();
//		none.waitTimer();
//		validatePreviewReport(none);
//		none.waitTimer();
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS01_NoneNoNaicStrongest -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "None", "NoNaic", "LevelAssumed" })
	public void TS02_NoneNoNaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		
		none.setCaseRequiredFields();
		none.setProduct("New Heights 10");
		none.setTaxStatus("Non-Qualified");
		none.click_EAFE_B();
		none.setPurchasePayment("800000");
		none.clickNextCase();
		none.waitTimer();

		none.setProfileRequiredTab();
		none.clickNextProfile();
		none.waitTimer();

		none.waitTimer();
		none.clickNextIncome();

		none.clickLevelAssumed();
		none.waitTimer();
		none.clickOverrideFixed();
		none.waitTimer();
		none.setRateIllustration("1.00%");
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS02_NoneNoNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "None", "NoNaic", "VariableAssumed" })
	public void TS03_NoneNoNaicVariableAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		none.setCaseRequiredFields();
		none.setProduct("New Heights 12");
		none.setTaxStatus("Non-Qualified");
		none.click_MOZAIC_II_B();
		none.setPurchasePayment("800000");
		none.clickNextCase();
		none.waitTimer();

		none.setProfileRequiredTab();
		none.clickNextProfile();
		none.waitTimer();

		none.waitTimer();
		none.clickNextIncome();
		none.waitTimer();

		none.clickVariableAssumed();
		none.waitTimer();
		none.clickVariableAssumed3();
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS03_NoneNoNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "None", "NoNaic", "LABackcasted" })
	public void TS04_NoneNoNaicLABackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		none.setCaseRequiredFields();
		none.setProduct("New Heights 12");
		none.setTaxStatus("Non-Qualified");
		none.click_MOZAIC_II_B();
		none.setPurchasePayment("800000");
		none.clickNextCase();

		none.setProfileRequiredTab();
		none.clickNextProfile();

		none.waitTimer();
		none.clickNextIncome();

		none.clickLABackcasted();
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS04_NoneNoNaicLABackcasted -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "NoNaic", "Strongest" }) 
	public void TS05_IncomeNoNaicStrongest() throws InterruptedException,
	IOException {
		
		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		
		//none.waitTimer();
		Income income = new Income(driver);
		income.waitTimer();

		income.setCaseRequiredFields();
		// income.setState("ID");
		income.setProduct("New Heights 9");
		income.setTaxStatus("Non-Qualified");
		income.click_SP500_B();
		income.clickSingle();
		income.clickPurchaseIllustration();
		income.setPurchasePayment("50000");
		income.clickNextCase();
		//income.setContinueAlertOver1m();
		//income.clickNextContinueAlertOver1m();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickNextProfile();
		income.waitTimer();

		income.waitTimer();
		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("Monthly");
		income.setPercentAmountOption("Percentage");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("3");
		income.setWithdraws_0_PercentOption("3");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("4");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_PercentOption("4");
		income.clickNextIncome();
		income.waitTimer();

		income.waitTimer();
		income.clickStrongest();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS05_IncomeNoNaicStrongest --"
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "NoNaic", "LevelAssumed" })
	public void TS06_IncomeNoNaicLevelAssumed() throws InterruptedException,
	IOException {
		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		Income income = new Income(driver);

		income.setCaseRequiredFields();
		income.setProduct("New Heights 9");
		income.setTaxStatus("Non-Qualified");
		income.click_EAFE_B();
		income.clickIncomeIllustration();
		income.setMonthlyIncome("1000000");
		income.clickNextCase();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickAddOwner();
		income.setJointFirstName("Janeth");
		income.setJointLastName("Rivera");
		income.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		income.setJointGender("Female");
		income.waitTimer();
		income.clickCoAnnuitant();
		income.clickNextProfile();
		income.waitTimer();

		income.waitTimer();
		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("Monthly");
		income.setPercentAmountOption("Amount");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("2");
		income.setWithdraws_0_AmountOption("100");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("2");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_AmountOption("500");
		income.clickNextIncome();
		income.waitTimer();

		income.clickLevelAssumed();
		income.waitTimer();
		income.clickOverrideFixed();
		income.setRateIllustration("1.00%");
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS06_IncomeNoNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "NoNaic", "VariableAssumed" })
	public void TS07_IncomeNoNaicVariableAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();

		Income income = new Income(driver);

		income.setCaseRequiredFields();

		income.setProduct("New Heights 10");
		income.setTaxStatus("Qualified");
		income.click_MOZAIC_II_A();
		income.clickSingle();
		income.clickIncomeIllustration();
		income.setMonthlyIncome("1000000");
		income.clickNextCase();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickAddOwnerQualified();
		income.setJointFirstName("Janeth");
		income.setJointLastName("Rivera");
		income.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		income.setJointGender("Female");
		income.clickNextProfile();
		income.waitTimer();

		income.waitTimer();
		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("annual");
		income.setPercentAmountOption("Amount");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("2");
		income.setWithdraws_0_AmountOption("100");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("2");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_AmountOption("500");
		income.clickNextIncome();
		income.waitTimer();

		income.clickVariableAssumed();
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS07_IncomeNoNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "NoNaic", "LABackcasted" })
	public void TS08_IncomeNoNaicLABackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();

		Income income = new Income(driver);

		income.setCaseRequiredFields();

		income.setProduct("New Heights 10");
		income.setTaxStatus("Qualified");
		income.click_MOZAIC_II_A();
		income.clickSingle();
		income.clickIncomeIllustration();
		income.setMonthlyIncome("1000000");
		income.clickNextCase();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickAddOwnerQualified();
		income.setJointFirstName("Janeth");
		income.setJointLastName("Rivera");
		income.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		income.setJointGender("Female");
		income.clickNextProfile();
		income.waitTimer();

		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("annual");
		income.setPercentAmountOption("Amount");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("2");
		income.setWithdraws_0_AmountOption("100");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("2");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_AmountOption("500");
		income.clickNextIncome();
		income.waitTimer();

		income.clickLABackcasted();
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS08_IncomeNoNaicLABackcasted -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}


	@Test(groups = { "IncomeBonus", "NoNaic", "Strongest" })
	public void TS09_IncomeBonusNoNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		incomeWbonus.setProduct("New Heights 10");
		incomeWbonus.setTaxStatus("Qualified");
		incomeWbonus.click_EAFE_B();
		incomeWbonus.clickJoint();
		incomeWbonus.setPurchasePayment("45000");
		incomeWbonus.clickNextCase();
		//incomeWbonus.setContinueAlertOver1m();
		//incomeWbonus.clickNextContinueAlertOver1m();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Amount");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_AmountOption("100");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("5");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickStrongest();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS09_IncomeBonusNoNaicStrongest -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "IncomeBonus", "NoNaic", "LevelAssumed" })
	public void TS10_IncomeBonusNoNaicLevelAssumed()
			throws InterruptedException, IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		none.waitTimer();
		incomeWbonus.setProduct("New Heights 9");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_EAFE_A();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("1000000");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.waitTimer();
		incomeWbonus.clickAnnuitant();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Amount");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_AmountOption("100");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.setWithdraws_1_StartAge("3");
		incomeWbonus.setWithdraws_1_StartStopAge("5");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickLevelAssumed();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS10_IncomeBonusNoNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "IncomeBonus", "NoNaic", "VariableAssumed" })
	public void TS11_IncomeBonusNoNaicVariableAssumed()
			throws InterruptedException, IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		incomeWbonus.waitTimer();
		incomeWbonus.setProduct("New Heights 9");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_MOZAIC_II_A();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("500");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.waitTimer();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Percent");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_2_StartAge("3");
		incomeWbonus.setWithdraws_2_StartStopAge("5");
		incomeWbonus.setWithdraws_2_PercentOption("7");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickVariableAssumed();
		incomeWbonus.waitTimer();
		incomeWbonus.clickVariableAssumed1();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out
		.println("PASSED -- TS11_IncomeBonusNoNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "IncomeBonus", "NoNaic", "LABackcasted" })
	public void TS12_IncomeBonusNoNaicLABackcasted()
			throws InterruptedException, IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		none.waitTimer();
		incomeWbonus.setProduct("New Heights 9");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_MOZAIC_II_A();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("1000000");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.waitTimer();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Percent");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_2_StartAge("3");
		incomeWbonus.setWithdraws_2_StartStopAge("5");
		incomeWbonus.setWithdraws_2_PercentOption("7");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickLABackcasted();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out
		.println("PASSED -- TS12_IncomeBonusNoNaicLABackcasted -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}


	@Test(groups = { "EDB", "NoNaic", "Strongest" })
	public void TS13_EDBNoNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		none.waitTimer();
		edb.waitTimer();
		edb.setProduct("New Heights 12");
		edb.setTaxStatus("Non-Qualified");
		edb.click_SP500_A();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Percent");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_PercentOption("7");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_PercentOption("7");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_PercentOption("7");
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickStrongest();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS13_EDBNoNaicStrongest -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
				System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "NoNaic", "LevelAssumed" })
	public void TS14_EDBNoNaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		none.waitTimer();
		edb.waitTimer();
		edb.setProduct("New Heights 12");
		edb.setTaxStatus("Non-Qualified");
		edb.click_SP500_A();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed", edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickLevelAssumed();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS14_EDBNoNaicLevelAssumed -- " + driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "NoNaic", "VariableAssumed" })
	public void TS15_EDBNoNaicVariableAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		none.waitTimer();
		edb.waitTimer();
		edb.setProduct("New Heights 10");
		edb.setTaxStatus("Non-Qualified");
		edb.click_MOZAIC_II_B();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickVariableAssumed();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS15_EDBNoNaicVariableAssumed -- " + driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "NoNaic", "LABackcasted" })
	public void TS16_EDBNoNaicLABackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		none.waitTimer();
		edb.waitTimer();
		edb.setProduct("New Heights 10");
		edb.setTaxStatus("Non-Qualified");
		edb.click_MOZAIC_II_B();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickLABackcasted();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS16_EDBNoNaicLABackcasted -- " + driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}
	
	@Test(groups = { "EDBBonus", "NoNaic", "Strongest" })
	public void TS17_EDBwBNoNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.waitTimer();
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 9");
		edbWBonus.setTaxStatus("Non-Qualified");
		edbWBonus.click_SP500_A();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickAddOwner();
		edbWBonus.setJointFirstName("Janeth");
		edbWBonus.setJointLastName("Rivera");
		edbWBonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edbWBonus.setJointGender("Female");
		edbWBonus.waitTimer();
		edbWBonus.clickCoOwner();
		edbWBonus.clickCoAnnuitant();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();

		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("2");
		edbWBonus.setWithdraws_0_AmountOption("100");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_1_StartAge("2");
		edbWBonus.setWithdraws_1_StartStopAge("3");
		edbWBonus.setWithdraws_1_AmountOption("200");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_2_StartAge("3");
		edbWBonus.setWithdraws_2_StartStopAge("5");
		edbWBonus.setWithdraws_2_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_3_StartAge("8");
		edbWBonus.setWithdraws_3_StartStopAge("11");
		edbWBonus.setWithdraws_3_AmountOption("4000");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_4_StartAge("13");
		edbWBonus.setWithdraws_4_StartStopAge("15");
		edbWBonus.setWithdraws_4_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_5_StartAge("18");
		edbWBonus.setWithdraws_5_StartStopAge("19");
		edbWBonus.setWithdraws_5_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_6_StartAge("20");
		edbWBonus.setWithdraws_6_StartStopAge("21");
		edbWBonus.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",edbWBonus.getMaxWithdrawalsText());
		edbWBonus.clickNextIncome();
		none.waitTimer();

		edbWBonus.clickStrongest();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS17_EDBwBNoNaicStrongest -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDBBonus", "NoNaic", "LevelAssumed" })
	public void TS18_EDBwBNoNaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.waitTimer();
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 10");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_EAFE_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();

		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickLevelAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickOverrideFixed();
		edbWBonus.waitTimer();
		edbWBonus.setRateIllustration("2.50%");
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS18_EDBwBNoNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDBBonus", "NoNaic", "VariableAssumed" })
	public void TS19_EDBwBNoNaicVariableAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.waitTimer();
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 9");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_MOZAIC_II_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();

		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickVariableAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS19_EDBwBNoNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDBBonus", "NoNaic", "LABackcasted" })
	public void TS20_EDBwBNoNaicLABackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.waitTimer();;
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 9");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_MOZAIC_II_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();

		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickLABackcasted();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS20_EDBwBNoNaicLABackcasted -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}
	
	
	// *********************************************** NAIC ********************************************************

	@Test(groups = { "None", "Naic", "Strongest" })
	public void TS21_NoneNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.setProduct("New Heights 9");
		none.setTaxStatus("Non-Qualified");
		none.click_SP500_A();
		none.setPurchasePayment("500000");
		none.clickNextCase();
		none.waitTimer();

		none.setProfileRequiredTab();
		none.clickNextProfile();

		none.waitTimer();
		none.clickPreIncomeWithdraw();
		none.setMonthAnnualOption("Monthly");
		none.setPercentAmountOption("Percentage");
		none.waitTimer();
		none.setWithdraws_0_StartAge("1");
		none.setWithdraws_0_StartStopAge("5");
		none.setWithdraws_0_PercentOption("3");
		none.clickAddMoreWithrawal();
		none.setWithdraws_1_StartAge("6");
		none.setWithdraws_1_StartStopAge("7");
		none.setWithdraws_1_PercentOption("4");
		none.clickNextIncome();
		none.waitTimer();

		none.clickStrongest();
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS21_NoneNaicStrongest -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "None", "Naic", "LevelAssumed" })
	public void TS22_NoneNaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.setProduct("New Heights 10");
		none.setTaxStatus("Non-Qualified");
		none.click_EAFE_B();
		none.setPurchasePayment("800000");
		none.clickNextCase();

		none.setProfileRequiredTab();
		none.clickNextProfile();

		none.waitTimer();
		none.clickNextIncome();
		none.waitTimer();

		none.clickLevelAssumed();
		none.waitTimer();
		none.clickOverrideFixed();
		none.setRateIllustration("1.00%");
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS22_NoneNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "None", "Naic", "VariableAssumed" })
	public void TS23_NoneNaicVariableAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.setProduct("New Heights 12");
		none.setTaxStatus("Non-Qualified");
		none.click_SP500_B();
		none.setPurchasePayment("800000");
		none.clickNextCase();
		none.waitTimer();

		none.setProfileRequiredTab();
		none.clickNextProfile();

		none.waitTimer();
		none.clickNextIncome();
		none.waitTimer();

		none.clickVariableAssumed();
		none.waitTimer();
		none.clickVariableAssumed3();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS23_NoneNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "None", "Naic", "LABackcasted" })
	public void TS24_NoneNaicLABackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.setProduct("New Heights 12");
		none.setTaxStatus("Non-Qualified");
		none.click_SP500_B();
		none.setPurchasePayment("800000");
		none.clickNextCase();
		none.waitTimer();

		none.setProfileRequiredTab();
		none.clickNextProfile();

		none.waitTimer();
		none.clickNextIncome();
		
		none.waitTimer();
		none.clickLABackcasted();
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS24_NoneNaicLABackcasted -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "Naic", "Strongest" })
	public void TS25_IncomeNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		Income income = new Income(driver);
		income.waitTimer();

		income.setCaseRequiredFields();
		income.setProduct("New Heights 10");
		income.setTaxStatus("Non-Qualified");
		income.click_EAFE_A();
		income.clickSingle();
		income.clickPurchaseIllustration();
		income.setPurchasePayment("25000");
		income.clickNextCase();
		//income.setContinueAlertOver1m();
		//income.clickNextContinueAlertOver1m();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickNextProfile();
		income.waitTimer();

		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("Monthly");
		income.setPercentAmountOption("Percentage");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("3");
		income.setWithdraws_0_PercentOption("3");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("4");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_PercentOption("4");
		income.clickNextIncome();
		income.waitTimer();

		income.clickStrongest();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS25_IncomeNaicStrongest --"
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "Naic", "LevelAssumed" })
	public void TS26_IncomeNaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		Income income = new Income(driver);
		income.waitTimer();

		income.setCaseRequiredFields();
		income.setProduct("New Heights 9");
		income.setTaxStatus("Non-Qualified");
		income.click_SP500_B();
		income.clickIncomeIllustration();
		income.setMonthlyIncome("1000000");
		income.clickNextCase();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickAddOwner();
		income.setJointFirstName("Janeth");
		income.setJointLastName("Rivera");
		income.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		income.setJointGender("Female");
		income.waitTimer();
		income.clickCoAnnuitant();
		income.clickNextProfile();
		income.waitTimer();

		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("Monthly");
		income.setPercentAmountOption("Amount");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("2");
		income.setWithdraws_0_AmountOption("100");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("2");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_AmountOption("500");
		income.clickNextIncome();
		income.waitTimer();

		income.clickLevelAssumed();
		income.waitTimer();
		income.clickOverrideFixed();
		income.setRateIllustration("1.00%");
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS26_IncomeNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "Naic", "VariableAssumed" })
	public void TS27_IncomeNaicVariableAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		Income income = new Income(driver);

		income.setCaseRequiredFields();
		income.setProduct("New Heights 9");
		income.setTaxStatus("Qualified");
		income.click_EAFE_B();
		income.clickSingle();
		income.clickIncomeIllustration();
		income.setMonthlyIncome("1000000");
		income.clickNextCase();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickAddOwnerQualified();
		income.setJointFirstName("Janeth");
		income.setJointLastName("Rivera");
		income.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		income.setJointGender("Female");
		income.clickNextProfile();
		income.waitTimer();

		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("annual");
		income.setPercentAmountOption("Amount");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("2");
		income.setWithdraws_0_AmountOption("100");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("2");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_AmountOption("500");
		income.clickNextIncome();
		income.waitTimer();
		
		income.clickVariableAssumed();
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS27_IncomeNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "Naic", "LABackcasted" })
	public void TS28_IncomeNaicLABackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		Income income = new Income(driver);

		income.setCaseRequiredFields();
		income.setProduct("New Heights 9");
		income.setTaxStatus("Qualified");
		income.click_EAFE_B();
		income.clickSingle();
		income.clickIncomeIllustration();
		income.setMonthlyIncome("1000000");
		income.clickNextCase();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickAddOwnerQualified();
		income.setJointFirstName("Janeth");
		income.setJointLastName("Rivera");
		income.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		income.setJointGender("Female");
		income.clickNextProfile();
		income.waitTimer();
		
		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("annual");
		income.setPercentAmountOption("Amount");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("2");
		income.setWithdraws_0_AmountOption("100");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("2");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_AmountOption("500");
		income.clickNextIncome();
		income.waitTimer();

		income.clickLABackcasted();
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS28_IncomeNaicLABackcasted -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}


	@Test(groups = { "IncomeBonus", "Naic", "Strongest" })
	public void TS29_IncomeBonusNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();

		incomeWbonus.setProduct("New Heights 10");
		incomeWbonus.setTaxStatus("Qualified");
		incomeWbonus.click_SP500_B();
		incomeWbonus.clickJoint();
		incomeWbonus.setPurchasePayment("40000");
		incomeWbonus.clickNextCase();
		//incomeWbonus.setContinueAlertOver1m();
		//incomeWbonus.clickNextContinueAlertOver1m();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Amount");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_AmountOption("100");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("5");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickStrongest();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS29_IncomeBonusNaicStrongest -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "IncomeBonus", "Naic", "LevelAssumed" })
	public void TS30_IncomeBonusNaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		none.waitTimer();
		incomeWbonus.setProduct("New Heights 10");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_EAFE_A();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("1000");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.waitTimer();
		incomeWbonus.clickAnnuitant();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Amount");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_AmountOption("100");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.setWithdraws_1_StartAge("3");
		incomeWbonus.setWithdraws_1_StartStopAge("5");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickLevelAssumed();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS30_IncomeBonusNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}
	
	@Test(groups = { "IncomeBonus", "Naic", "VariableAssumed" })
	public void TS31_IncomeBonusNaicVariableAssumed()
			throws InterruptedException, IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		incomeWbonus.waitTimer();

		incomeWbonus.setProduct("New Heights 9");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_SP500_A();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("900000");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.waitTimer();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Percent");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_2_StartAge("3");
		incomeWbonus.setWithdraws_2_StartStopAge("5");
		incomeWbonus.setWithdraws_2_PercentOption("7");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickVariableAssumed();
		incomeWbonus.waitTimer();
		incomeWbonus.clickVariableAssumed1();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS31_IncomeBonusNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "IncomeBonus", "Naic", "LABackcasted" })
	public void TS32_IncomeBonusNaicLABackcasted()
			throws InterruptedException, IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		incomeWbonus.waitTimer();

		incomeWbonus.setProduct("New Heights 9");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_SP500_A();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("1000000");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.waitTimer();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Percent");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_2_StartAge("3");
		incomeWbonus.setWithdraws_2_StartStopAge("5");
		incomeWbonus.setWithdraws_2_PercentOption("7");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickLABackcasted();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS32_IncomeBonusNaicLABackcasted -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "Naic", "Strongest" })
	public void TS33_EDBNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		edb.waitTimer();

		edb.waitTimer();
		edb.setProduct("New Heights 12");
		edb.setTaxStatus("Non-Qualified");
		edb.click_EAFE_A();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Percent");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_PercentOption("7");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_PercentOption("7");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_PercentOption("7");
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickStrongest();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS33_EDBNaicStrongest -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "Naic", "LevelAssumed" })
	public void TS34_EDBNaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		edb.waitTimer();;
		edb.setProduct("New Heights 9");
		edb.setTaxStatus("Non-Qualified");
		edb.click_SP500_A();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickLevelAssumed();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS34_EDBNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "Naic", "VariableAssumed" })
	public void TS35_EDBNaicVariableAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		edb.waitTimer();

		edb.waitTimer();
		edb.setProduct("New Heights 12");
		edb.setTaxStatus("Non-Qualified");
		edb.click_SP500_B();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickVariableAssumed();
		edb.waitTimer();
		edb.clickVariableAssumed3();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS35_EDBNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "Naic", "LABackcasted" })
	public void TS36_EDBNaicLABackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		edb.waitTimer();

		edb.waitTimer();
		edb.setProduct("New Heights 12");
		edb.setTaxStatus("Non-Qualified");
		edb.click_SP500_B();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickLABackcasted();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS36_EDBNaicLABackcasted -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDBBonus", "Naic", "Strongest" })
	public void TS37_EDBwBNaicStrongest() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();

		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 9");
		edbWBonus.setTaxStatus("Non-Qualified");
		edbWBonus.click_SP500_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickAddOwner();
		edbWBonus.setJointFirstName("Janeth");
		edbWBonus.setJointLastName("Rivera");
		edbWBonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edbWBonus.setJointGender("Female");
		edbWBonus.waitTimer();
		edbWBonus.clickCoOwner();
		edbWBonus.clickCoAnnuitant();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();

		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("2");
		edbWBonus.setWithdraws_0_AmountOption("100");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_1_StartAge("2");
		edbWBonus.setWithdraws_1_StartStopAge("3");
		edbWBonus.setWithdraws_1_AmountOption("200");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_2_StartAge("3");
		edbWBonus.setWithdraws_2_StartStopAge("5");
		edbWBonus.setWithdraws_2_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_3_StartAge("8");
		edbWBonus.setWithdraws_3_StartStopAge("11");
		edbWBonus.setWithdraws_3_AmountOption("4000");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_4_StartAge("13");
		edbWBonus.setWithdraws_4_StartStopAge("15");
		edbWBonus.setWithdraws_4_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_5_StartAge("18");
		edbWBonus.setWithdraws_5_StartStopAge("19");
		edbWBonus.setWithdraws_5_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_6_StartAge("20");
		edbWBonus.setWithdraws_6_StartStopAge("21");
		edbWBonus.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edbWBonus.getMaxWithdrawalsText());
		edbWBonus.clickNextIncome();
		

		edbWBonus.clickStrongest();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS37_EDBwBNaicStrongest -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDBBonus", "Naic", "LevelAssumed" })
	public void TS38_EDBwBNaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();

		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 9");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_EAFE_A();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();

		edbWBonus.waitTimer();
		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickLevelAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickOverrideFixed();
		edbWBonus.waitTimer();
		edbWBonus.setRateIllustration("1.50%");
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS38_EDBwBNaicLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDBBonus", "Naic", "VariableAssumed" })
	public void TS39_EDBwBNaicVariableAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();

		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 10");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_SP500_A();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();

		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickVariableAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS39_EDBwBNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}
	
	@Test(groups = { "EDBBonus", "Naic", "LABackcasted" })
	public void TS40_EDBwBNaicLABackcasted() throws InterruptedException, IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();

		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 10");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_SP500_A();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();

		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickLABackcasted();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS40_EDBwBNaicVariableAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	// *********************************************** NAIC MOZAIC *********************************************************
	/*
	@Test(groups = { "None", "Mozaic", "LevelAssumed" })
	public void TS41_NoneMozaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.waitTimer();

		none.setProduct("New Heights 8");
		none.setTaxStatus("Qualified");
		none.click_MOZAIC_A();
		none.setPurchasePayment("25000");
		none.clickNextCase();
		none.waitTimer();

		none.setProfileRequiredTab();
		none.clickNextProfile();

		none.waitTimer();
		none.clickPreIncomeWithdraw();
		none.setMonthAnnualOption("Annual");
		none.setPercentAmountOption("Amount");
		none.waitTimer();
		none.setWithdraws_0_StartAge("1");
		none.setWithdraws_0_StartStopAge("3");
		none.setWithdraws_0_AmountOption("100");
		none.clickAddMoreWithrawal();
		none.setWithdraws_1_StartAge("4");
		none.setWithdraws_1_StartStopAge("5");
		none.setWithdraws_1_AmountOption("1000");
		none.clickNextIncome();
		none.waitTimer();

		none.clickLevelAssumed();
		none.clickOverrideFixed();
		none.waitTimer();
		none.setRateIllustration("1.75%");
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS41_NoneMozaicLevelAssumed -- "
				+ driver.getCurrentUrl());
	}

	@Test(groups = { "Income", "Mozaic", "LevelAssumed" })
	public void TS42_IncomeMozaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		Income income = new Income(driver);
		income.waitTimer();

		income.setCaseRequiredFields();
		income.setProduct("New Heights 8");
		income.setTaxStatus("Non-Qualified");
		income.click_MOZAIC_B();
		income.setPurchasePayment("10000");
		income.clickNextCase();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickNextProfile();
		income.waitTimer();

		income.waitTimer();
		income.clickNextIncome();
		income.waitTimer();

		income.clickLevelAssumed();
		income.clickOverrideFixed();
		income.waitTimer();
		income.setRateIllustration("1.50%");
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS42_IncomeMozaicLevelAssumed -- "
				+ driver.getCurrentUrl());
	}

	@Test(groups = { "IncomeBonus", "Mozaic", "LevelAssumed" })
	public void TS43_IncomeBonusMozaLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		incomeWbonus.waitTimer();

		incomeWbonus.setProduct("New Heights 12");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_MOZAIC_B();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("1000000");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.waitTimer();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge("65");
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("Annual");
		incomeWbonus.setPercentAmountOption("Percent");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_2_StartAge("3");
		incomeWbonus.setWithdraws_2_StartStopAge("5");
		incomeWbonus.setWithdraws_2_PercentOption("7");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickLevelAssumed();
		incomeWbonus.clickOverrideFixed();
		incomeWbonus.waitTimer();
		incomeWbonus.setRateIllustration("8.00%");
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS43_IncomeBonusMozaLevelAssumed -- "
				+ driver.getCurrentUrl());
	}

	@Test(groups = { "EDB", "Mozaic", "LevelAssumed" })
	public void TS44_EDBMozaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		edb.waitTimer();

		edb.waitTimer();
		edb.setProduct("New Heights 10");
		edb.setTaxStatus("Non-Qualified");
		edb.click_MOZAIC_B();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge("55");
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		none.waitTimer();

		edb.clickLevelAssumed();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS44_EDBMozaicLevelAssumed -- "
				+ driver.getCurrentUrl());
	}

	@Test(groups = { "EDBBonus", "Mozaic", "LevelAssumed" })
	public void TS45_EDBwBMozaicLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();

		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 12");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_MOZAIC_A();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();
		
		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickLevelAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS45_EDBwBMozaicLevelAssumed -- "
				+ driver.getCurrentUrl());
	}
*/	
	// *********************************************** NAIC MOZAIC II *********************************************************

	@Test(groups = { "None", "MozaicII", "LevelAssumed" })
	public void TS46_NoneMozaicIILevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.waitTimer();

		none.setProduct("New Heights 9");
		none.setTaxStatus("Qualified");
		none.click_MOZAIC_II_B();
		none.setPurchasePayment("1000000");
		none.clickNextCase();
		none.waitTimer();

		none.setProfileRequiredTab();
		none.clickNextProfile();

		none.waitTimer();
		none.clickPreIncomeWithdraw();
		none.setMonthAnnualOption("Annual");
		none.setPercentAmountOption("Amount");
		none.waitTimer();
		none.setWithdraws_0_StartAge("1");
		none.setWithdraws_0_StartStopAge("3");
		none.setWithdraws_0_AmountOption("100");
		none.clickAddMoreWithrawal();
		none.setWithdraws_1_StartAge("4");
		none.setWithdraws_1_StartStopAge("5");
		none.setWithdraws_1_AmountOption("1000");
		none.clickNextIncome();
		none.waitTimer();

		none.clickLevelAssumed();
		none.clickOverrideFixed();
		none.waitTimer();
		none.setRateIllustration("0.00%");
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS46_NoneMozaicIILevelAssumed( -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "MozaicII", "LevelAssumed" })
	public void TS47_IncomeMozaicIILevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		Income income = new Income(driver);
		income.waitTimer();

		income.setCaseRequiredFields();
		income.setProduct("New Heights 9");
		income.setTaxStatus("Non-Qualified");
		income.click_MOZAIC_II_A();
		income.setPurchasePayment("356000");
		income.clickNextCase();
		//income.setContinueAlertOver1m();
		//income.clickNextContinueAlertOver1m();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickNextProfile();
		income.waitTimer();

		income.waitTimer();
		income.clickNextIncome();
		income.waitTimer();

		income.clickLevelAssumed();
		income.clickOverrideFixed();
		income.waitTimer();
		income.setRateIllustration("1.00%");
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS47_IncomeMozaicIILevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "IncomeBonus", "MozaicII", "LevelAssumed" })
	public void TS48_IncomeBonusMozaicIILevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		incomeWbonus.waitTimer();

		incomeWbonus.setProduct("New Heights 12");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_MOZAIC_II_B();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("500");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.waitTimer();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("Annual");
		incomeWbonus.setPercentAmountOption("Percent");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_2_StartAge("3");
		incomeWbonus.setWithdraws_2_StartStopAge("5");
		incomeWbonus.setWithdraws_2_PercentOption("7");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickLevelAssumed();
		incomeWbonus.clickOverrideFixed();
		incomeWbonus.waitTimer();
		incomeWbonus.setRateIllustration("1.00%");
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS48_IncomeBonusMozaicIILevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "MozaicII", "LevelAssumed" })
	public void TS49_EDBMozaicIILevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		edb.waitTimer();

		edb.waitTimer();
		edb.setProduct("New Heights 10");
		edb.setTaxStatus("Non-Qualified");
		edb.click_MOZAIC_II_A();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		none.waitTimer();

		edb.clickLevelAssumed();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS49_EDBMozaicIILevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDBBonus", "MozaicII", "LevelAssumed" })
	public void TS50_EDBwBMozaicIILevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();

		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 12");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_MOZAIC_II_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();
		
		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickLevelAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS50_EDBwBMozaicIILevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}
	
	// *********************************************** ZEBRA *********************************************************

	@Test(groups = { "None", "Zebra", "LevelAssumed" })
	public void TS51_NoneZebraLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.waitTimer();

		none.setProduct("New Heights 10");
		none.setTaxStatus("Non-Qualified");
		none.click_ZEBRA_A();
		none.setPurchasePayment("500000");
		none.clickNextCase();
		none.waitTimer();

		none.setProfileRequiredTab();
		none.clickNextProfile();

		none.waitTimer();
		none.clickPreIncomeWithdraw();
		none.setMonthAnnualOption("Monthly");
		none.setPercentAmountOption("Percentage");
		none.waitTimer();
		none.setWithdraws_0_StartAge("1");
		none.setWithdraws_0_StartStopAge("5");
		none.setWithdraws_0_PercentOption("3");
		none.clickAddMoreWithrawal();
		none.setWithdraws_1_StartAge("6");
		none.setWithdraws_1_StartStopAge("7");
		none.setWithdraws_1_PercentOption("4");
		none.clickNextIncome();
		none.waitTimer();

		none.clickLevelAssumed();
		none.clickOverrideFixed();
		none.waitTimer();
		none.setRateIllustration("1.00%");
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		none.waitTimer();
		validatePreviewReport(none);
		none.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		none.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS51_NoneZebraLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "Income", "Zebra", "LevelAssumed" })
	public void TS52_IncomeZebraLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		Income income = new Income(driver);
		income.waitTimer();

		income.setCaseRequiredFields();
		income.setProduct("New Heights 9");
		income.setTaxStatus("Non-Qualified");
		income.click_ZEBRA_A();
		income.setPurchasePayment("800000");
		income.clickNextCase();
		income.waitTimer();

		income.setProfileRequiredTab();
		income.clickNextProfile();
		income.waitTimer();
		
		income.clickNextIncome();
		income.waitTimer();

		income.clickLevelAssumed();
		income.clickOverrideFixed();
		income.waitTimer();
		income.setRateIllustration("1.00%");
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		income.waitTimer();
		validatePreviewReport(none);
		income.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		income.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS52_IncomeZebraLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "IncomeBonus", "Zebra", "LevelAssumed" })
	public void TS53_IncomeBonusZebraLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		incomeWbonus.waitTimer();

		incomeWbonus.setProduct("New Heights 9");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_ZEBRA_B();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("1000000");
		incomeWbonus.clickNextCase();
		incomeWbonus.waitTimer();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setClientCurrentAge(Integer.toString(incomeBonusRandomAge));
		incomeWbonus.waitTimer();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextProfile();
		incomeWbonus.waitTimer();

		incomeWbonus.setIncomeStartAge(Integer.toString(incomeBonusRandomAge + 10));
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Percent");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_2_StartAge("3");
		incomeWbonus.setWithdraws_2_StartStopAge("5");
		incomeWbonus.setWithdraws_2_PercentOption("7");
		incomeWbonus.clickNextIncome();
		incomeWbonus.waitTimer();

		incomeWbonus.clickLevelAssumed();
		incomeWbonus.clickOverrideFixed();
		incomeWbonus.waitTimer();
		incomeWbonus.setRateIllustration("1.00%");
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		incomeWbonus.waitTimer();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		incomeWbonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS53_IncomeBonusZebraLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDB", "Zebra", "LevelAssumed" })
	public void TS54_EDBZebraLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		edb.waitTimer();

		edb.waitTimer();
		edb.setProduct("New Heights 10");
		edb.setTaxStatus("Non-Qualified");
		edb.click_ZEBRA_A();
		edb.setPurchasePayment("750000");
		edb.clickNextCase();
		edb.waitTimer();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge(Integer.toString(randomJointIncomeAge));
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextProfile();
		edb.waitTimer();

		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edb.getMaxWithdrawalsText());
		edb.clickNextIncome();
		edb.waitTimer();

		edb.clickLevelAssumed();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		edb.waitTimer();
		validatePreviewReport(none);
		edb.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edb.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS54_EDBZebraLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	@Test(groups = { "EDBBonus", "Moza", "LevelAssumed" })
	public void TS55_EDBwBMozaLevelAssumed() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.setCaseRequiredFields();
		edbWBonus.waitTimer();

		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 12");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_ZEBRA_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextCase();
		edbWBonus.waitTimer();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextProfile();
		edbWBonus.waitTimer();

		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextIncome();
		edbWBonus.waitTimer();

		edbWBonus.clickLevelAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		edbWBonus.waitTimer();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		startLoadTime = System.currentTimeMillis();
		edbWBonus.waitTimer();
		validatePdfReport();
		System.out.println("PASSED -- TS55_EDBwBMozaLevelAssumed -- "
				+ driver.getCurrentUrl() + " " + totalLoadTime + " Seconds");
		System.out.println("The Page load in: " + totalLoadTime + " seconds");
	}

	//**********************BACKCASTED***************************************
/*
	@Test
	public void TS56_NoneNoNaicStrongestBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();

		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.setProduct("New Heights 8");
		none.setTaxStatus("Non-Qualified");
		none.click_SP500_A();
		none.setPurchasePayment("500000");
		none.clickNextButton();

		none.setProfileRequiredTab();
		none.clickNextButton();

		none.waitTimer();
		none.clickPreIncomeWithdraw();
		none.setMonthAnnualOption("Monthly");
		none.setPercentAmountOption("Percentage");
		none.waitTimer();
		none.setWithdraws_0_StartAge("1");
		none.setWithdraws_0_StartStopAge("5");
		none.setWithdraws_0_PercentOption("3");
		none.clickAddMoreWithrawal();
		none.setWithdraws_1_StartAge("6");
		none.setWithdraws_1_StartStopAge("7");
		none.setWithdraws_1_PercentOption("4");
		none.clickNextButton();

		none.clickStrongest();
		none.waitTimer();
		none.clickBackcastedOutput();
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		validatePreviewReport(none);
		none.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS56_NoneNoNaicStrongestBackcasted() -- "
				+ driver.getCurrentUrl());
	}

	@Test//(groups = { "None", "NoNaic", "LevelAssumed" })
	public void TS57_NoneNoNaicLevelAssumedBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();

		none.setProduct("New Heights 10");
		none.setTaxStatus("Non-Qualified");
		none.click_EAFE_B();
		none.setPurchasePayment("800000");
		none.clickNextButton();

		none.setProfileRequiredTab();
		none.clickNextButton();

		none.waitTimer();
		none.clickNextButton();

		none.clickLevelAssumed();
		none.waitTimer();
		none.clickOverrideFixed();
		none.setRateIllustration("5.00%");
		none.waitTimer();
		none.clickBackcastedOutput();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		validatePreviewReport(none);
		none.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS57_NoneNoNaicLevelAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS58_NoneNoNaicVariableAssumedBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		none.setCaseRequiredFields();
		none.setProduct("New Heights 12");
		none.setTaxStatus("Non-Qualified");
		none.click_MOZAIC_B();
		none.setPurchasePayment("800000");
		none.clickNextButton();

		none.setProfileRequiredTab();
		none.clickNextButton();

		none.waitTimer();
		none.clickNextButton();

		none.clickVariableAssumed();
		none.waitTimer();
		none.clickVariableAssumed3();
		none.waitTimer();
		none.clickBackcastedOutput();
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		validatePreviewReport(none);
		none.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS58_NoneNoNaicVariableAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS59_IncomeNoNaicStrongestBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		none.waitTimer();
		Income income = new Income(driver);
		income.waitTimer();

		income.setCaseRequiredFields();
		// income.setState("ID");
		income.setProduct("New Heights 9");
		income.setTaxStatus("Non-Qualified");
		income.click_SP500_B();
		income.clickSingle();
		income.clickPurchaseIllustration();
		income.setPurchasePayment("800000");
		income.clickNextButton();

		income.setProfileRequiredTab();
		income.clickNextButton();

		income.waitTimer();
		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("Monthly");
		income.setPercentAmountOption("Percentage");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("3");
		income.setWithdraws_0_PercentOption("3");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("4");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_PercentOption("4");
		income.clickNextButton();

		income.waitTimer();
		income.clickStrongest();
		income.waitTimer();
		income.clickBackcastedOutput();
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		validatePreviewReport(none);
		income.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS59_IncomeNoNaicStrongestBackcasted --"
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS60_IncomeNoNaicLevelAssumedBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		Income income = new Income(driver);

		income.setCaseRequiredFields();
		income.setProduct("New Heights 8");
		income.setTaxStatus("Non-Qualified");
		income.click_EAFE_B();
		income.clickIncomeIllustration();
		income.setMonthlyIncome("800000");
		income.clickNextButton();

		income.setProfileRequiredTab();
		income.clickAddOwner();
		income.setJointFirstName("Janeth");
		income.setJointLastName("Rivera");
		income.setJointCurrentAge("80");
		income.setJointGender("Female");
		income.waitTimer();
		income.clickCoAnnuitant();
		income.clickNextButton();

		income.waitTimer();
		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("Monthly");
		income.setPercentAmountOption("Amount");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("2");
		income.setWithdraws_0_AmountOption("100");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("2");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_AmountOption("500");
		income.clickNextButton();

		income.clickLevelAssumed();
		income.waitTimer();
		income.clickOverrideFixed();
		income.setRateIllustration("0.00%");
		income.waitTimer();
		income.clickBackcastedOutput();
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		validatePreviewReport(none);
		income.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS60_IncomeNoNaicLevelAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS61_IncomeNoNaicVariableAssumedBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();

		Income income = new Income(driver);

		income.setCaseRequiredFields();

		income.setProduct("New Heights 10");
		income.setTaxStatus("Qualified");
		income.click_MOZAIC_A();
		income.clickSingle();
		income.clickIncomeIllustration();
		income.setMonthlyIncome("1000000");
		income.clickNextButton();

		income.setProfileRequiredTab();
		income.clickAddOwnerQualified();
		income.setJointFirstName("Janeth");
		income.setJointLastName("Rivera");
		income.setJointCurrentAge("40");
		income.setJointGender("Female");
		income.clickNextButton();

		income.waitTimer();
		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("annual");
		income.setPercentAmountOption("Amount");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("2");
		income.setWithdraws_0_AmountOption("100");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("2");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_AmountOption("500");
		income.clickNextButton();

		income.clickVariableAssumed();
		income.waitTimer();
		income.clickBackcastedOutput();
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		validatePreviewReport(none);
		income.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS61_IncomeNoNaicVariableAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS62_IncomeBonusNoNaicStrongestBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		incomeWbonus.setProduct("New Heights 10");
		incomeWbonus.setTaxStatus("Qualified");
		incomeWbonus.click_EAFE_B();
		incomeWbonus.clickJoint();
		incomeWbonus.setPurchasePayment("1000000");
		incomeWbonus.clickNextButton();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge("40");
		incomeWbonus.setJointGender("Female");
		incomeWbonus.clickNextButton();

		incomeWbonus.waitTimer();
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Amount");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_AmountOption("100");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("5");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.clickNextButton();

		incomeWbonus.clickStrongest();
		incomeWbonus.waitTimer();
		incomeWbonus.clickBackcastedOutput();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS62_IncomeBonusNoNaicStrongestBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS63_IncomeBonusNoNaicLevelAssumedBackcasted()
			throws InterruptedException, IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		none.waitTimer();
		incomeWbonus.setProduct("New Heights 8");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_EAFE_A();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("1000000");
		incomeWbonus.clickNextButton();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.waitTimer();
		incomeWbonus.clickAnnuitant();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge("40");
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextButton();

		incomeWbonus.waitTimer();
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Amount");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_AmountOption("100");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.setWithdraws_1_StartAge("3");
		incomeWbonus.setWithdraws_1_StartStopAge("5");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.clickNextButton();

		incomeWbonus.clickLevelAssumed();
		incomeWbonus.waitTimer();
		incomeWbonus.clickBackcastedOutput();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS63_IncomeBonusNoNaicLevelAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS64_IncomeBonusNoNaicVariableAssumedBackcasted()
			throws InterruptedException, IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);

		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();
		none.waitTimer();
		incomeWbonus.setProduct("New Heights 9");
		incomeWbonus.setTaxStatus("Non-Qualified");
		incomeWbonus.click_MOZAIC_A();
		incomeWbonus.clickJoint();
		incomeWbonus.clickIncomeIllustration();
		incomeWbonus.setMonthlyIncome("1000000");
		incomeWbonus.clickNextButton();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.waitTimer();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge("40");
		incomeWbonus.setJointGender("Female");
		incomeWbonus.waitTimer();
		incomeWbonus.clickCoOwner();
		incomeWbonus.clickCoAnnuitant();
		incomeWbonus.clickNextButton();

		incomeWbonus.waitTimer();
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Percent");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("3");
		incomeWbonus.setWithdraws_1_PercentOption("7");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_2_StartAge("3");
		incomeWbonus.setWithdraws_2_StartStopAge("5");
		incomeWbonus.setWithdraws_2_PercentOption("7");
		incomeWbonus.clickNextButton();

		incomeWbonus.clickVariableAssumed();
		incomeWbonus.waitTimer();
		incomeWbonus.clickVariableAssumed1();
		incomeWbonus.waitTimer();
		incomeWbonus.clickBackcastedOutput();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		validatePdfReport();
		System.out
		.println("PASSED -- TS64_IncomeBonusNoNaicVariableAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS65_EDBNoNaicStrongestBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		none.waitTimer();
		edb.waitTimer();
		edb.setProduct("New Heights 12");
		edb.setTaxStatus("Non-Qualified");
		edb.click_SP500_A();
		edb.setPurchasePayment("750000");
		edb.clickNextButton();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge("40");
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextButton();

		edb.waitTimer();
		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Percent");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_PercentOption("7");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_PercentOption("7");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_PercentOption("7");
		edb.clickNextButton();

		edb.clickStrongest();
		edb.waitTimer();
		edb.clickBackcastedOutput();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		validatePreviewReport(none);
		edb.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS65_EDBNoNaicStrongestBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test(groups = { "EDB", "NoNaic", "LevelAssumed" })
	public void TS66_EDBNoNaicLevelAssumedBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		none.waitTimer();
		edb.waitTimer();
		edb.setProduct("New Heights 12");
		edb.setTaxStatus("Non-Qualified");
		edb.click_SP500_A();
		edb.setPurchasePayment("750000");
		edb.clickNextButton();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge("40");
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextButton();

		edb.waitTimer();
		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edb.getMaxWithdrawalsText());
		edb.clickNextButton();

		edb.clickLevelAssumed();
		edb.waitTimer();
		edb.clickBackcastedOutput();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		validatePreviewReport(none);
		edb.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS66_EDBNoNaicLevelAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test(groups = { "EDB", "NoNaic", "VariableAssumed" })
	public void TS67_EDBNoNaicVariableAssumedBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		none.waitTimer();
		edb.waitTimer();
		edb.setProduct("New Heights 10");
		edb.setTaxStatus("Non-Qualified");
		edb.click_MOZAIC_B();
		edb.setPurchasePayment("750000");
		edb.clickNextButton();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge("40");
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextButton();

		edb.waitTimer();
		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Amount");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_AmountOption("100");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_AmountOption("200");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_3_StartAge("8");
		edb.setWithdraws_3_StartStopAge("11");
		edb.setWithdraws_3_AmountOption("4000");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_4_StartAge("13");
		edb.setWithdraws_4_StartStopAge("15");
		edb.setWithdraws_4_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_5_StartAge("18");
		edb.setWithdraws_5_StartStopAge("19");
		edb.setWithdraws_5_AmountOption("300");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_6_StartAge("20");
		edb.setWithdraws_6_StartStopAge("21");
		edb.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edb.getMaxWithdrawalsText());
		edb.clickNextButton();

		edb.clickVariableAssumed();
		edb.waitTimer();
		edb.clickBackcastedOutput();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		validatePreviewReport(none);
		edb.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS67_EDBNoNaicVariableAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test(groups = { "EDBBonus", "NoNaic", "Strongest" })
	public void TS68_EDBwBNoNaicStrongestBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		EDBwB edbWBonus = new EDBwB(driver);
		none.waitTimer();
		edbWBonus.setCaseRequiredFields();
		none.waitTimer();
		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 8");
		edbWBonus.setTaxStatus("Non-Qualified");
		edbWBonus.click_SP500_A();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextButton();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickAddOwner();
		edbWBonus.setJointFirstName("Janeth");
		edbWBonus.setJointLastName("Rivera");
		edbWBonus.setJointCurrentAge("40");
		edbWBonus.setJointGender("Female");
		edbWBonus.waitTimer();
		edbWBonus.clickCoOwner();
		edbWBonus.clickCoAnnuitant();
		edbWBonus.clickNextButton();

		edbWBonus.waitTimer();
		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("2");
		edbWBonus.setWithdraws_0_AmountOption("100");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_1_StartAge("2");
		edbWBonus.setWithdraws_1_StartStopAge("3");
		edbWBonus.setWithdraws_1_AmountOption("200");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_2_StartAge("3");
		edbWBonus.setWithdraws_2_StartStopAge("5");
		edbWBonus.setWithdraws_2_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_3_StartAge("8");
		edbWBonus.setWithdraws_3_StartStopAge("11");
		edbWBonus.setWithdraws_3_AmountOption("4000");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_4_StartAge("13");
		edbWBonus.setWithdraws_4_StartStopAge("15");
		edbWBonus.setWithdraws_4_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_5_StartAge("18");
		edbWBonus.setWithdraws_5_StartStopAge("19");
		edbWBonus.setWithdraws_5_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_6_StartAge("20");
		edbWBonus.setWithdraws_6_StartStopAge("21");
		edbWBonus.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edbWBonus.getMaxWithdrawalsText());
		edbWBonus.clickNextButton();

		edbWBonus.clickStrongest();
		edbWBonus.waitTimer();
		edbWBonus.clickBackcastedOutput();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS68_EDBwBNoNaicStrongestBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS69_EDBwBNoNaicLevelAssumedBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		EDBwB edbWBonus = new EDBwB(driver);
		none.waitTimer();
		edbWBonus.setCaseRequiredFields();
		none.waitTimer();
		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 10");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_EAFE_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextButton();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextButton();

		edbWBonus.waitTimer();
		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextButton();

		edbWBonus.clickLevelAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickOverrideFixed();
		edbWBonus.waitTimer();
		edbWBonus.setRateIllustration("2.25%");
		edbWBonus.waitTimer();
		edbWBonus.clickBackcastedOutput();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS69_EDBwBNoNaicLevelAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS70_EDBwBNoNaicVariableAssumedBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (noNaicStates[new Random()
		.nextInt(noNaicStates.length)]);
		none.setStateModal(randomState);
		none.waitTimer();
		EDBwB edbWBonus = new EDBwB(driver);
		none.waitTimer();
		edbWBonus.setCaseRequiredFields();
		none.waitTimer();
		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 9");
		edbWBonus.setTaxStatus("Qualified");
		edbWBonus.click_MOZAIC_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextButton();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickNextButton();

		edbWBonus.waitTimer();
		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("30");
		edbWBonus.setWithdraws_0_AmountOption("100");
		Assert.assertEquals("Add another Free Partial Withdrawal",
				edbWBonus.getAddMoreWithdrawalsText());
		edbWBonus.clickNextButton();

		edbWBonus.clickVariableAssumed();
		edbWBonus.waitTimer();
		edbWBonus.clickBackcastedOutput();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS70_EDBwBNoNaicVariableAssumedBackcasted -- "
				+ driver.getCurrentUrl());
	}		


	// ************************NO NAIC BACKCASTED******************************
	@Test
	public void TS71_NoneNaicBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		none.setCaseRequiredFields();
		none.setProduct("New Heights 8");
		none.setTaxStatus("Non-Qualified");
		none.click_SP500_A();
		none.setPurchasePayment("500000");
		none.clickNextButton();

		none.setProfileRequiredTab();
		none.clickNextButton();

		none.waitTimer();
		none.clickPreIncomeWithdraw();
		none.setMonthAnnualOption("Monthly");
		none.setPercentAmountOption("Percentage");
		none.waitTimer();
		none.setWithdraws_0_StartAge("1");
		none.setWithdraws_0_StartStopAge("5");
		none.setWithdraws_0_PercentOption("3");
		none.clickAddMoreWithrawal();
		none.setWithdraws_1_StartAge("6");
		none.setWithdraws_1_StartStopAge("7");
		none.setWithdraws_1_PercentOption("4");
		none.clickNextButton();

		none.waitTimer();
		none.clickBackcastedMethod();
		none.waitTimer();
		none.clickSave();
		none.waitTimer();
		none.clickPreviewReport();
		validatePreviewReport(none);
		none.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS71_NoneNaicBackcasted() -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS72_IncomeNaicBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		Income income = new Income(driver);
		income.waitTimer();

		income.setCaseRequiredFields();
		income.setProduct("New Heights 10");
		income.setTaxStatus("Non-Qualified");
		income.click_EAFE_A();
		income.clickSingle();
		income.clickPurchaseIllustration();
		income.setPurchasePayment("800000");
		income.clickNextButton();

		income.setProfileRequiredTab();
		income.clickNextButton();

		income.waitTimer();
		income.clickPreIncomeWithdraw();
		income.setMonthAnnualOption("Monthly");
		income.setPercentAmountOption("Percentage");
		income.waitTimer();
		income.setWithdraws_0_StartAge("1");
		income.setWithdraws_0_StartStopAge("3");
		income.setWithdraws_0_PercentOption("3");
		income.clickAddMoreWithrawal();
		income.setWithdraws_1_StartAge("4");
		income.setWithdraws_1_StartStopAge("5");
		income.setWithdraws_1_PercentOption("4");
		income.clickNextButton();

		income.waitTimer();
		income.clickBackcastedMethod();
		income.waitTimer();
		income.clickSave();
		income.waitTimer();
		income.clickPreviewReport();
		validatePreviewReport(none);
		income.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS72_IncomeNaicBackcasted --"
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS73_IncomeBonusNaicBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		IncomeWBonus incomeWbonus = new IncomeWBonus(driver);

		incomeWbonus.setCaseRequiredFields();

		incomeWbonus.setProduct("New Heights 10");
		incomeWbonus.setTaxStatus("Qualified");
		incomeWbonus.click_SP500_B();
		incomeWbonus.clickJoint();
		incomeWbonus.setPurchasePayment("1000000");
		incomeWbonus.clickNextButton();

		incomeWbonus.setProfileRequiredTab();
		incomeWbonus.setJointFirstName("Janeth");
		incomeWbonus.setJointLastName("Rivera");
		incomeWbonus.setJointCurrentAge("40");
		incomeWbonus.setJointGender("Female");
		incomeWbonus.clickNextButton();

		incomeWbonus.waitTimer();
		incomeWbonus.clickPreIncomeWithdraw();
		incomeWbonus.setMonthAnnualOption("annual");
		incomeWbonus.setPercentAmountOption("Amount");
		incomeWbonus.waitTimer();
		incomeWbonus.setWithdraws_0_StartAge("1");
		incomeWbonus.setWithdraws_0_StartStopAge("2");
		incomeWbonus.setWithdraws_0_AmountOption("100");
		incomeWbonus.clickAddMoreWithrawal();
		incomeWbonus.setWithdraws_1_StartAge("2");
		incomeWbonus.setWithdraws_1_StartStopAge("5");
		incomeWbonus.setWithdraws_1_AmountOption("500");
		incomeWbonus.clickNextButton();

		incomeWbonus.waitTimer();
		incomeWbonus.clickBackcastedMethod();
		incomeWbonus.waitTimer();
		incomeWbonus.clickSave();
		incomeWbonus.waitTimer();
		incomeWbonus.clickPreviewReport();
		validatePreviewReport(none);
		incomeWbonus.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS73_IncomeBonusNaicBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS74_EDBNaicBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDB edb = new EDB(driver);
		edb.setCaseRequiredFields();
		none.waitTimer();

		edb.waitTimer();
		edb.setProduct("New Heights 12");
		edb.setTaxStatus("Non-Qualified");
		edb.click_EAFE_A();
		edb.setPurchasePayment("750000");
		edb.clickNextButton();

		edb.setProfileRequiredTab();
		edb.waitTimer();
		edb.clickAddOwner();
		edb.setJointFirstName("Janeth");
		edb.setJointLastName("Rivera");
		edb.setJointCurrentAge("40");
		edb.setJointGender("Female");
		edb.waitTimer();
		edb.clickCoOwner();
		edb.clickCoAnnuitant();
		edb.clickNextButton();

		edb.waitTimer();
		edb.clickPreIncomeWithdraw();
		edb.setMonthAnnualOption("Monthly");
		edb.setPercentAmountOption("Percent");
		edb.waitTimer();
		edb.setWithdraws_0_StartAge("1");
		edb.setWithdraws_0_StartStopAge("2");
		edb.setWithdraws_0_PercentOption("7");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_1_StartAge("2");
		edb.setWithdraws_1_StartStopAge("3");
		edb.setWithdraws_1_PercentOption("7");
		edb.clickAddMoreWithrawal();
		edb.setWithdraws_2_StartAge("3");
		edb.setWithdraws_2_StartStopAge("5");
		edb.setWithdraws_2_PercentOption("7");
		edb.clickNextButton();

		edb.waitTimer();
		edb.clickBackcastedMethod();
		edb.waitTimer();
		edb.clickSave();
		edb.waitTimer();
		edb.clickPreviewReport();
		validatePreviewReport(none);
		edb.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS74_EDBNaicBackcasted -- "
				+ driver.getCurrentUrl());
	}

	@Test
	public void TS75_EDBBonuseNaicBackcasted() throws InterruptedException,
	IOException {

		None none = new None(driver);
		none.waitTimer();
		String randomState = (naicStates[new Random()
		.nextInt(naicStates.length)]);
		none.setStateModal(randomState);
		EDBwB edbWBonus = new EDBwB(driver);
		edbWBonus.setCaseRequiredFields();
		none.waitTimer();

		edbWBonus.waitTimer();
		edbWBonus.setProduct("New Heights 8");
		edbWBonus.setTaxStatus("Non-Qualified");
		edbWBonus.click_SP500_B();
		edbWBonus.setPurchasePayment("750000");
		edbWBonus.clickNextButton();

		edbWBonus.setProfileRequiredTab();
		edbWBonus.waitTimer();
		edbWBonus.clickAddOwner();
		edbWBonus.setJointFirstName("Janeth");
		edbWBonus.setJointLastName("Rivera");
		edbWBonus.setJointCurrentAge("40");
		edbWBonus.setJointGender("Female");
		edbWBonus.waitTimer();
		edbWBonus.clickCoOwner();
		edbWBonus.clickCoAnnuitant();
		edbWBonus.clickNextButton();

		edbWBonus.waitTimer();
		edbWBonus.clickPreIncomeWithdraw();
		edbWBonus.setMonthAnnualOption("Monthly");
		edbWBonus.setPercentAmountOption("Amount");
		edbWBonus.waitTimer();
		edbWBonus.setWithdraws_0_StartAge("1");
		edbWBonus.setWithdraws_0_StartStopAge("2");
		edbWBonus.setWithdraws_0_AmountOption("100");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_1_StartAge("2");
		edbWBonus.setWithdraws_1_StartStopAge("3");
		edbWBonus.setWithdraws_1_AmountOption("200");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_2_StartAge("3");
		edbWBonus.setWithdraws_2_StartStopAge("5");
		edbWBonus.setWithdraws_2_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_3_StartAge("8");
		edbWBonus.setWithdraws_3_StartStopAge("11");
		edbWBonus.setWithdraws_3_AmountOption("4000");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_4_StartAge("13");
		edbWBonus.setWithdraws_4_StartStopAge("15");
		edbWBonus.setWithdraws_4_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_5_StartAge("18");
		edbWBonus.setWithdraws_5_StartStopAge("19");
		edbWBonus.setWithdraws_5_AmountOption("300");
		edbWBonus.clickAddMoreWithrawal();
		edbWBonus.setWithdraws_6_StartAge("20");
		edbWBonus.setWithdraws_6_StartStopAge("21");
		edbWBonus.setWithdraws_6_AmountOption("300");
		Assert.assertEquals("Max 7 Free Partial Withdraw periods allowed",
				edbWBonus.getMaxWithdrawalsText());
		edbWBonus.clickNextButton();

		edbWBonus.waitTimer();
		edbWBonus.clickBackcastedMethod();
		edbWBonus.waitTimer();
		edbWBonus.clickSave();
		edbWBonus.waitTimer();
		edbWBonus.clickPreviewReport();
		validatePreviewReport(none);
		edbWBonus.clickPdfReport();
		validatePdfReport();
		System.out.println("PASSED -- TS75_EDBBonuseNaicBackcasted -- "
				+ driver.getCurrentUrl());
	}*/


	public void validatePreviewReport(None none) throws InterruptedException,
	IOException {
		//System.out.println("Checking Preview Report");
		changeWindow();
		none.waitTimer();
		Assert.assertEquals(none.getDownloadPdfLinkText(),"Download Complete Illustration PDF");

		if (none.isReportHeaderDisplayed()) {
			//System.out.println("REPORT HEADER DISPLAYED");
		} else {
			System.out.println("REPORT NOT LOADED CORRECTLY");
		}
		none.waitTimer();
		captureScreenReportBase();
		none.waitTimer();
		driver.close();
		driver.switchTo().window(TestScenarios.editWindow);
		none.waitTimer();
	}

	public void validatePdfReport() throws IOException, InterruptedException {
		Thread.sleep(2000);
		changeWindow();

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.urlContains("GeneratePdf"));

		URL url = new URL(driver.getCurrentUrl());		
		finishLoadTime = System.currentTimeMillis();
		/*try {
			BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
			PDFParser parser = new PDFParser(fileToParse);
			parser.parse();
			//save pdf text into output variable
			String output = new PDFTextStripper().getText(parser.getPDDocument());
			//System.out.println(output);
			parser.getPDDocument().close();
			Assert.assertTrue(output.contains("This report is not complete unless all pages are included"));
			Assert.assertTrue(output.contains("Fixed indexed annuities are contracts you buy"));
			Assert.assertTrue(output.contains("This illustration assumes that the Balanced Allocation"));
			
			captureScreenPdfReport(wait);
			Thread.sleep(2000);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			captureScreenPdfReport(wait);
		}*/
		captureScreenPdfReport(wait, startLoadTime);
		Thread.sleep(2000);
		driver.close();
		driver.switchTo().window(TestScenarios.editWindow);
	}

	public void changeWindow() throws InterruptedException {
		for (String winHandle : driver.getWindowHandles()) {
			if (!winHandle.equals(TestScenarios.editWindow)) {
				driver.switchTo().window(winHandle);
				Thread.sleep(2000);
				driver.switchTo().window(winHandle);
				winHandle = "";
			}
		}
	}

	public void captureScreenReportBase() throws IOException {

		 File source_file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	     FileUtils.copyFile(source_file, new File("C:\\Screenshots\\" + contScreenshots + ".png"));
	     contScreenshots++;
	}
	
	public void captureScreenPdfReport(WebDriverWait wait, long startLoadTime) throws IOException, InterruptedException {
		totalLoadTime = (finishLoadTime - startLoadTime)/1000;
        //System.out.println("The Page load in: " + totalLoadTime + " seconds");
		Thread.sleep(5000);
        File source_file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source_file, new File("C:\\Screenshots\\" + contScreenshots + ".png"));
        contScreenshots++;
       }
}
