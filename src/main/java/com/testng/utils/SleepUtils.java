package com.testng.utils;

import org.apache.log4j.Logger;

public class SleepUtils {

	static Logger log = Logger.getLogger(SleepUtils.class);
	
	/**
	 * Hard Sleep don not use Unless it is Necessary
	 * @param seconds
	 */
	public static void sleep(double seconds) {
		log.info(String.format("Sleeping for %s Seconds", seconds));
		try {
			Thread.sleep(Double.valueOf(seconds *1000).intValue());
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
