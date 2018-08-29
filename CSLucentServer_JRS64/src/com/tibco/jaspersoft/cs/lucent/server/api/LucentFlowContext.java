package com.tibco.jaspersoft.cs.lucent.server.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * $Id: LucentFlowContext.java 287 2018-08-29 09:09:08Z jwhang $
 */
public class LucentFlowContext implements Serializable {
	
	public final static long serialVersionUID = 0l;
	
	String testCategory = "testCategory_uninitialized";
	String testId = "testId_uninitialized";
	String transactionId = null;
	Map<String,String> propertyBag = new HashMap<String, String>();
	
	public LucentFlowContext(){
		//assign a new transaction id per generation of a Lucent Flow Context.
		this.transactionId = UUID.randomUUID().toString();
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

	public Map<String, String> getPropertyBag() {
		return propertyBag;
	}

	public void setPropertyBag(Map<String, String> propertyBag) {
		this.propertyBag = propertyBag;
	}
	
}



