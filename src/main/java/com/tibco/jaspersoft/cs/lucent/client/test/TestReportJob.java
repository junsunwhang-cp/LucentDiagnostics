package com.tibco.jaspersoft.cs.lucent.client.test;

import java.util.TimerTask;

/*
 * $Id: TestReportJob.java 241 2018-01-30 06:57:07Z jwhang $
 */
public class TestReportJob extends TimerTask {

	private String url = null;
	private String uuid = null;
	private TestReportJobInfo reportJobInfo = null;
	String serverPath = null;
	String username = null;
	String password = null;
	String loadTestId = null;
	
	public TestReportJob(String url, String uuid, TestReportJobInfo reportJobInfo, String serverPath, String username, 
			String password, String loadTestId){
		this.url = url;
		this.uuid = uuid;
		this.reportJobInfo = reportJobInfo;
		this.serverPath = serverPath;
		this.username = username;
		this.password = password;
		this.loadTestId = loadTestId;
	}
	
	public void run(){
		reportJobInfo.setStartTime(System.currentTimeMillis()); //log when this test was started.
		ExecGeneratePdf egp = new ExecGeneratePdf();
		String tempPath = System.getProperty("java.io.tmpdir");
		
		long elapsedTime = egp.writePdf(this.serverPath, this.url, this.username, this.password, tempPath, this.loadTestId);
		
		reportJobInfo.flagExecutionComplete(elapsedTime);
		cancel(); //only run this job once.
	}
	
}
