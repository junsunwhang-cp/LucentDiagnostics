package com.tibco.jaspersoft.cs.lucent.server.api;

/*
 * $Id: LucentFlowContext.java 266 2018-04-26 20:14:31Z jwhang $
 */
public class LucentFlowContext {
	String testCategory = null;
	String testId = null;
	String transactionId = null;
	
	public LucentFlowContext(){
		
	}
	
	public LucentFlowContext(String testCategory, String testId){
		this.testCategory = testCategory;
		this.testId = testId;
	}
	
	public String getTestCategory() {
		return testCategory;
	}

	public void setTestCategory(String testCategory) {
		this.testCategory = testCategory;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
}



