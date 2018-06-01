package com.tibco.jaspersoft.cs.lucent.client.test;

/*
 * $Id: TestReportJobInfo.java 241 2018-01-30 06:57:07Z jwhang $
 * Class to hold status of running report job and persist past the report job thread's termination.
 */
public class TestReportJobInfo {
	
	String uuid = "";
	String url = "";
	long startTime = 0;
	long elapsedTime = 0;
	boolean completed = false;
	
	public TestReportJobInfo(String uuid, String url){
		this.uuid = uuid;
		this.url = url;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public boolean isCompleted() {
		return this.completed;
	}

	public synchronized void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public synchronized void flagExecutionComplete(long elapsedTime){
		this.elapsedTime = elapsedTime;
		this.completed = true;
	}
}



