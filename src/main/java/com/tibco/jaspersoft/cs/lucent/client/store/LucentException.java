package com.tibco.jaspersoft.cs.lucent.client.store;

public class LucentException  extends Exception {

	public final static long serialVersionUID = 0l;
	
	private String message = "";
	
	public LucentException(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
