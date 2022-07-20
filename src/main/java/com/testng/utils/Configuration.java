package com.testng.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class Configuration {

	static Logger log = Logger.getLogger(Configuration.class);
	private static Configuration config;
	private Properties prop;
	private File configFile;

	public static final String BROWSER = "browser.name";
	public static final String BASEURLKEY = "base.url";
	public static final String PAGELOADTIMEOUTKEY = "pageLoadTimeOut";
	public static final String IMPLICITYWAITKEY = "implicityWait";
	public static final String EXPLICITYWAITKEY = "explicityWait";

	private static final String APP_PKG = "app.pkg";
	private static final String APP_ACTIVITY = "application.activity";

	private static String CLOUD_BROWSER = "cloud.browser";
	private static String MOBILE_BROWSER = "mobile.browser";
	private static String SERVER_URL = "server.url";
	private static String CLOUDSERVER_URL = "cloudServer.url";
	private static String CLOUDSERVER_BrowserVersion = "cloudBrowser.version";
	private static String ENABLE_VNC = "cloud.enableVNC";
	private static String ENABLE_VIDEO = "cloud.enableVideo";
	private static String DATA_FILE = "data.fileName";
	private static String LUMPSUM_OUTPUTDATA_FILE = "data.Output.lumpsum";
	private static String ADDPURCHASE_OUTPUTDATA_FILE = "data.Output.additionalPurchase";
	private static String REDEMPTION_OUTPUTDATA_FILE = "data.Output.redemption";
	private static String SIP_OUTPUTDATA_FILE = "data.Output.SIP";
	private static String STP_OUTPUTDATA_FILE = "data.Output.STP";
	private static String SWP_OUTPUTDATA_FILE = "data.Output.SWP";
	private static String SW_OUTPUTDATA_FILE = "data.Output.SW";
	private static String RISKPROFILE_OUTPUTDATA_FILE = "data.Output.RiskProfile";

	public synchronized static Configuration getInstance() {
		if (config == null) {
			config = new Configuration();
		}
		return config;
	}

	private Configuration() {
		this(new File("config.properties"));
	}

	private Configuration(File configFile) {
		this.configFile = configFile;
		initalizeProperty();
	}

	private synchronized void initalizeProperty() {
		log.info("Initalization COnfiguration Property : " + configFile.getAbsolutePath());
		prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(configFile);
		} catch (FileNotFoundException e) {
			log.fatal("Configuration File Not FOund");
			e.printStackTrace();
			log.fatal(e);
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			log.fatal(e);
			log.fatal("Failed To Load Configuration File , Check File Syntax");
			e.printStackTrace();
		}

	}
	
	private String getProperty(String key) {
		String value = System.getProperty(key);
		if(value == null) {
			value=prop.getProperty(key);
		}
		return value.trim();
	}

	public String getenableVNC() {
		return getProperty(ENABLE_VNC);
	}

	public void setenableVNC(String browserVersion) {
		prop.setProperty(ENABLE_VNC, browserVersion);
	}

	public String getenableVideo() {
		return getProperty(ENABLE_VIDEO);
	}

	public String getDataFile() {
		return getProperty(DATA_FILE);
	}

	public String getLumpsumFileName() {
		return getProperty(LUMPSUM_OUTPUTDATA_FILE);
	}

	public String getAddPurchaseFileName() {
		return getProperty(ADDPURCHASE_OUTPUTDATA_FILE);
	}

	public String getRedemptionFileName() {
		return getProperty(REDEMPTION_OUTPUTDATA_FILE);
	}

	public String getSIPFileName() {
		return getProperty(SIP_OUTPUTDATA_FILE);
	}

	public String getSTPFileName() {
		return getProperty(STP_OUTPUTDATA_FILE);
	}

	public String getSWPFileName() {
		return getProperty(SWP_OUTPUTDATA_FILE);
	}

	public String getSWFileName() {
		return getProperty(SW_OUTPUTDATA_FILE);
	}

	public String getRiskFileName() {
		return getProperty(RISKPROFILE_OUTPUTDATA_FILE);
	}

	public void setenableVideo(String browserVersion) {
		prop.setProperty(ENABLE_VIDEO, browserVersion);
	}
	
	
	public String getCloudServerbrowserV() {
		return getProperty(CLOUDSERVER_BrowserVersion);
	}
	
	public void setCloudServerbrowserV(String browserVersion) {
		prop.setProperty(CLOUDSERVER_BrowserVersion, browserVersion);
	}
	public String getcloudServerURL() {
		return getProperty(CLOUDSERVER_URL);
	}
	public void setcloudServerURL(String cURL) {
		prop.setProperty(CLOUDSERVER_URL, cURL);
	}
	public String getserverURL() {
		return getProperty(SERVER_URL);
	}
	public void setserverURL(String sURL) {
		prop.setProperty(SERVER_URL, sURL);
	}
	public String getMobileBrowser() {
		return getProperty(MOBILE_BROWSER);
	}
	public void setMobileBrowser(String browser) {
		prop.setProperty(MOBILE_BROWSER, browser);
	}
	public String getCloudBrowser() {
		return getProperty(CLOUD_BROWSER);
	}
	public void setCloudBrowser(String browser) {
		prop.setProperty(CLOUD_BROWSER, browser);
	}
	public String getBrowser() {
		return getProperty(BROWSER);
	}
	public void setBrowser(String browser) {
		prop.setProperty(BROWSER, browser);
	}
	
	
//	String filevalue = System.getProperty("user.dir")+"./DataFoleder/NPS.xlsx";
	public String getBaseURL() {
//		String filevalue = System.getProperty("user.dir")+"./DataFoleder/NPS.xlsx";
//		String [][] data = ExcelUtils.getExcelData(file,"EmployeeDetails",1,9);
//		prop.setProperty(BASEURLKEY, data [0][8]);
		return getProperty(BASEURLKEY);
	}

	public int getPageLoadTimeOut() {
		try {
			return Integer.valueOf(getProperty(PAGELOADTIMEOUTKEY));
		} catch (Exception e) {
			log.fatal("Excepetion Occured while reading pageLoadTimeOut Value.Setting default Value : 60 Seconds");
			log.fatal(e);
			return 60;
		}
	}
	
	public int getImplicityWait() {
		try {
			return Integer.valueOf(getProperty(IMPLICITYWAITKEY));
		} catch (Exception e) {
			log.fatal("Excepetion Occured while reading ImplicityWait Value.Setting default Value : 10 Seconds");
			log.fatal(e);
			return 10;
		}
	}
	
	public int getExplicityWait() {
		try {
			return Integer.valueOf(getProperty(EXPLICITYWAITKEY));
		} catch (Exception e) {
			log.fatal("Excepetion Occured while reading ExplicitWait Value.Setting default Value : 60 Seconds");
			log.fatal(e);
			return 60;
		}
	}
	
	
	public Properties getEnvProperties() {
		Properties prop = new Properties();
		prop.setProperty(BASEURLKEY, getBaseURL());
		prop.setProperty("browser Version", getCloudServerbrowserV());
		prop.setProperty("browser", getCloudBrowser());
		prop.setProperty("PageLoadTimeOut", String.valueOf(getPageLoadTimeOut()) + TimeUnit.SECONDS);
		prop.setProperty("ImplicityWait", String.valueOf(getImplicityWait()) + TimeUnit.SECONDS);
		prop.setProperty("ExplicityWait", String.valueOf(getExplicityWait()) + TimeUnit.SECONDS);
		return prop;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
