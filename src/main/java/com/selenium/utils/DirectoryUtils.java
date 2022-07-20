package com.selenium.utils;

import java.io.File;

public class DirectoryUtils {

	public static String getUserDir() {
		return System.getProperty("user.dir");
	}
	
	public static String getTargetDir() {
		return getUserDir() + File.separator +"target";
	}
	
	public static String getScreenShotDir() {
		return getUserDir() + File.separator +"ScreenShot";
	}
	
	public static String getTestDataDir() {
		return getUserDir() + File.separator +"DataFolder";
	}
	
	public static String getResultDir() {
		return getUserDir() + File.separator +"Result";
	}
	
	public static String getReportsDir() {
		return getUserDir() + File.separator +"Reports Final";
	}
	
	public static String getAllureReportDir() {
		return getUserDir() + File.separator +"allure-results";
	}
	
	public static String getImgFolderDir() {
		return getUserDir() + File.separator +"DataFolder";
	}

}
