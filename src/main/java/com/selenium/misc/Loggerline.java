package com.selenium.misc;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Loggerline {
	
	public static void initilizeLogger() {
		ConsoleAppender console = new ConsoleAppender(); //create Appender
		// Configure the Appender
		// String PATTERN = "%d[%p|%c|%C{1}]%m%n";
		String PATTERN = "%d %5p [%c{1}]%m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		//add appender to any Logger (here is root)
		
		Logger.getRootLogger().addAppender(console);
		
		FileAppender fa = new FileAppender();
		fa.setName("FileLogger");
		fa.setFile("Log.log");
		fa.setLayout(new PatternLayout("%d %5p [%c{1}]%m%n"));
		fa.setThreshold(Level.INFO);
		fa.setAppend(true);
		fa.activateOptions();
		
		//add appender to any Logger (here is root)
		Logger.getRootLogger().addAppender(fa);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
