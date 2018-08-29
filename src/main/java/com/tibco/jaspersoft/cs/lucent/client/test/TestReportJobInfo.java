package com.tibco.jaspersoft.cs.lucent.client.test;

import java.util.Comparator;

/*
 * $Id: TestReportJobInfo.java 286 2018-08-21 18:13:32Z jwhang $
 * Class to hold status of running report job and persist past the report job thread's termination.
 */
public class TestReportJobInfo implements Comparator<TestReportJobInfo> {
	
	String uuid = "";
	String url = "";
	long startTime = 0;
	long elapsedTime = 0;
	boolean completed = false;
	
	public TestReportJobInfo(){
		
	}
	
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

	public int compare(TestReportJobInfo ji1, TestReportJobInfo ji2) {
		
		int retCode =  ji1.getUrl().compareTo(ji2.getUrl());
		if (retCode == 0){ //URL is the same, return based on time stamp.
			int timeCode = (int)(ji1.getStartTime() - ji2.getStartTime());
			return timeCode;
		} else {
			return retCode;
		}
	}
}



