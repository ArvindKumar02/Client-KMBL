package com.selenium.init;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.reflect.InitializerSignature;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.mongodb.MapReduceCommand.OutputType;

import freemarker.template.Configuration;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import net.bytebuddy.asm.Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName;

/**
 * Class to Step Browser Configuration and Actions
 */

public class Browser {

	static Logger log = Logger.getLogger(Browser.class);

	ExtentReports extent;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	protected WebDriver driver;
	protected WebDriverWait waitDriver, waitspDriver;
	protected Actions actionDriver;
	protected Wait<WebDriver> fluentwait;
	protected String parentHandle;
	protected Browsers browser;
	protected JavascriptExecutor jsDriver;
	private int dynamicWait = 60;
	private String downloadFolderPath;
	static WebDriverWait wait;

	public Browser() {
		this(null);
	}

	public Browser(WebDriver driver) {
		this.driver = driver;
		if (driver != null)
			initialize();

	}

	public synchronized void launchBrowser(String browser) throws Exception {
		launchBrowser(getBrowserFromString(browser));
	}

	@Step("Open Browser : {0}")
	private Browsers getBrowserFromString(String browser) {

		if (browser.equalsIgnoreCase("firefox")) {
			return Browsers.FireFox;

		} else if (browser.equalsIgnoreCase("Safari")) {
			return Browsers.Safari;
		} else if (browser.equalsIgnoreCase("ie")) {
			return Browsers.IE;
		} else if (browser.equalsIgnoreCase("edge")) {
			return Browsers.Edge;
		} else if (browser.equalsIgnoreCase("cloud")) {
			return Browsers.Cloud;
		} else if (browser.equalsIgnoreCase("chrome")) {
			return Browsers.Chrome;
		}
		throw new Exception("Browser Not Found..!!!!!");
	}

	public Browsers getBrowser() {
		return browser;
	}

	/**
	 * Launch Browser
	 * 
	 * @param browser
	 * @throws MalformedURLException
	 */
	@SuppressWarnings("deprecation")
	public synchronized void launchBrowser(Browsers browser) {

		String osArch = System.getProperty("os.arch");
		String osName = System.getProperty("os.name");
		log.info("Browser : " + browser);
		log.info("Os Arch : " + osArch);
		log.info("Os Name : " + osName);

		if (osName.contains("Windows")) {
			DesiredCapabilities dr = null;
			if (browser == Browsers.Chrome && osName.contains("Windows")) {
				System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");

				// Google Options
				// Options.addArguments("--headless");

				dr = DesiredCapabilities.chrome();
				dr.setCapability("SessionTimeout", "5m");
				dr.setBrowserName("chrome");
				dr.setPlatform(Platform.WINDOWS);
				dr.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(options);
			} else if (browser == Browsers.FireFox) {
				if (osArch.endsWith("64")) {
					System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver_64.exe");
				} else {
					System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver_32.exe");
				}
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);
				driver = new FirefoxDriver(capabilities);
//				maximize();
			} else if (browser == Browsers.IE) {
				if (osArch.endsWith("64")) {
					System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");
				} else {
					System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");
				}
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//				capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
				driver = new InternetExplorerDriver(capabilities);
			} else if (browser == Browsers.Edge) {
				System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver_64.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.edge();
				driver = new EdgeDriver(capabilities);
			} else if (browser == Browsers.Cloud) {
				try {
					DesiredCapabilities capabilities = new DesiredCapabilities();
					capabilities.setCapability("Version", Configuration.getInstance().getCloudServerBrowserV());
					capabilities.setCapability("browserName", Configuration.getInstance().getCloudBrowser());
//					capabilities.setCapability("ScreenResolution", "1000x600x24");
					if (Configuration.getInstance().getenableVNC().equals("true")) {
//						capabilities.setCapability("platform", Configuration.getInstance().getPlatform());
					}
					capabilities.setCapability("SessionTimeout", "5m");
					if (Configuration.getInstance().getenablevideo().equals("true")) {
						capabilities.setCapability("enableVideo", true);
						capabilities.setCapability("VideoName", "TestSuite.mp4");
					}

					log.info("Cloud Device Config: " + capabilities);
					RemoteWebDriver driver_r = new RemoteWebDriver(new URL(Configuration.getInstance().getCloudServerUrl()), capabilities);

					driver_r.setFileDetector((new LocalFileDetector()));

					driver = driver_r;
					maximize();
					initialize();

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

		}
		Initialize();
	}
	
	private void Initialize() {
		actionDriver = new Actions(driver);
		waitDriver = new WebDriverWait(driver, dynamicWait);//by default
		waitspDriver = new WebDriverWait(driver, dynamicWait);
		//Dynamic Timeout
		// is 60 Seconds
		
		parentHandle = driver.getWindowHandle();
		jsDriver = (JavascriptExecutor) this.driver;
		fluentwait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(40))
				.pollingEvery(Duration.ofSeconds(5).ignoring(NoSuchElementException.class);
	}
	
	/**
	 * Set Implicit Wait
	 * 
	 * @param seconds
	 */

	public void setImplicitTimeout() {
		log.info("Setting Implicity Wait : "+ seconds + " Seconds");
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	
	public void getWindows() {
		parentHandle = driver.getWindowHandle();
		log.info(parentHandle);
	}
	
	/**
	 * Set Explicit Wait
	 * 
	 * @param seconds
	 */

	public void setExplicitTimeout() {
		log.info("Setting Explicity Wait : "+ seconds + " Seconds");
		dynamicWait = seconds;
		waitDriver = new WebDriverWait(driver, seconds);
		waitspDriver = new WebDriverWait(driver, 60);
	}
	
	/**
	 * Set Page Load Time out
	 * 
	 * @param seconds
	 */
	
	public void setPageLoadTimeOut() {
		log.info("Setting Page Load TimeOut : "+ seconds + " Seconds");
		driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
	}
	
	@Step("Closing Browser")
	public void closeBrowser() {
		log.info("Closing Browser");
		
		if (driver != null) {
			driver.quit();
		}
	}
	
	@Step("Load URL : {0}")
	public void openURL(String URL) {
//		log.info("Opening URL :"+ URL);
		driver.get(URL);
	}
	
	/**
	 * Return Current Driver Instance
	 * 
	 * @return
	 */
	
	public WebDriver getDriver() {
		return driver;
	}
	
	@Step("Action : Refresh Page")
	public void refresh() {
		log.info("Refreshing Page....!!");
		driver.navigate().refresh();
	}
	
	@Step("Action : Navigate to Back")
	public void back() {
		log.info("Navigating To Back");
		driver.navigate().back();
	}
	
	/**
	 * Attach image whenever method called
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	
	public byte[] takeScreenShot() {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}
	
	@Attachment(value = "Reason - {0} ", type = "image/png")
	public byte[] takeScreenShot(String description) {
		return takeScreenShot();
	}
	
	public String getScreenshot() throws IOException {
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String date = new SimpleDateFormat("_dd.MM.YY_HH.mm z").format(new Date());
		String path = System.getProperty("user.dir")+"/Screenshots/"+name+""+date+".png");
		FileUtils.copyFile(src, new File(path));
		return path;
	}
	
	public String getScreenshotAsBase64()throws IOException {
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String date = new SimpleDateFormat("_dd.MM.YY_HH.mm z").format(new Date());
		String path = System.getProperty("user.dir")+name+""+date+".png");
		FileUtils.copyFile(src, new File(path));
		byte[] ImageBytes = IOUtils.toByteArray(new FileInputStream(path));
		return Base64.getEncoder().encodeToString(ImageBytes);
	}
	
	public String getScreenshotAsRobot() throws IOException , AWTException {
		BufferedImage image = new Robot().createScreenCapture(new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		String date = new SimpleDateFormat("_dd.MM.YY_HH.mm z").format(new Date());
		String path = System.getProperty("user.dir")+"/Screenshots/"+name+""+date+".png");
		ImageIO.write(image, new File(path+name+""+date+".png"));
		System.out.println(path);
		byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(path));
		return Base64.getEncoder().encodeToString(imageBytes);
	}
	
//	public String getScreenshotAsBaseAsRobot() throws IOException ,HeadlessException, AWTException {
//		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		BufferedImage image = new Robot().createScreenCapture(new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//		String date = new SimpleDateFormat("_dd.MM.YY_HH.mm z").format(new Date());
//		String path = System.getProperty("user.dir")+"/Screenshots/"+name+""+date+".png");
//		ImageIO.write(image, new File(path+name+""+date+".png"));
//		FileUtils.copyFile(src, new File(path));
//		byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(path));
//		return Base64.getEncoder().encodeToString(imageBytes);
//		
//	}
	
	public Actions getActionDriver() {
		return actionDriver;
	}
	
	public WebDriverWait getWebDriverWait() {
		return waitDriver;
	}
	
	public WebDriverWait getWebDriverspWait() {
		return waitspDriver;
	}
	
	public JavascriptExecutor getJSDriver() {
		return jsDriver;
	}
	
	public Wait<WebDriver> getFluentwait(){
		return fluentwait;
	}
	
	public void switchToChildWindow() {
		Set<String> newWindow = driver.getWindowHandles();
		 for(String handle : newWindow) {
			 log.info("Window :"+handle);
			 if (!handle.equals(parentHandle)) {
				 log.info("Window :"+handle);
				 driver.switchTo().window(handle);
				 log.info("Switch Done");
				 break;
				
			}
		 }
	}
	public void switchToParentWindow() {
		driver.switchTo().window(parentHandle);
		 log.info("Switch To Parent Window");
	}
	
	public void closeChildWindow() {
		driver.close();
		driver.switchTo().window(parentHandle);
	}
	public void maximize() {
		log.info("Maximize Window");
	}
	
	public String getDownloadFolderPath() {
		return downloadFolderPath;
	}
	
	public void setDownloadFolderpath(String downloadFolderPath) {
		this.downloadFolderPath = downloadFolderPath;
	}
	
	public int getExplicitWait() {
		return dynamicWait;
	}
	

}
