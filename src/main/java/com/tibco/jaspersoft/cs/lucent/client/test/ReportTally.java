package com.tibco.jaspersoft.cs.lucent.client.test;

/*
 * $Id: ReportTally.java 252 2018-04-02 23:09:28Z jwhang $
 */
public class ReportTally {

	private int pending = 0;
	private int completed = 0;
	
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
	
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	
	public int totalQueued() {
		return pending + completed;
	}
}



