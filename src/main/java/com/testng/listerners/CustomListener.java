package com.testng.listerners;

import org.apache.log4j.Logger;

import io.qameta.allure.Story;

public class CustomListener implements IInvokedMethodListener{
	
	static Logger log =Logger.getLogger(CustomListener.class);
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult){
		Story scenerioTitle = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Story.class);
		
		if (scenerioTitle != null) {
			log.info("Scenerio Started: "+scenerioTitle.value());
		}
	}
	
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		log.info("After "+method.getTestMethod().getMethodName());
	}

}
