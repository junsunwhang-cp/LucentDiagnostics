package com.tibco.jaspersoft.cs.lucent.client.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.jaspersoft.cs.lucent.client.core.LoadTestController;
import com.tibco.jaspersoft.cs.lucent.client.store.ReportInfo;
import com.tibco.jaspersoft.cs.lucent.client.store.ServerInfo;

/*
 * $Id: LoadTestContainer.java 259 2018-04-17 23:54:35Z jwhang $
 */
public class LoadTestContainer {

	private String loadTestId = null;
	private ServerInfo serverInfo = null;
	private ConcurrentHashMap<String, TestReportJobInfo> testResults = new ConcurrentHashMap<String, TestReportJobInfo>(); 
	
	public LoadTestContainer(String loadTestId, ServerInfo serverInfo){
		this.loadTestId = loadTestId;
		this.serverInfo = serverInfo;
	}
	
	/*
	 * Generate JSON string to return as status.  Includes:
	 * isComplete: true/false.
	 * completed: total completed for progress bar.
	 * total: total number of reports requested originally.
	 */
	public String getTestStatus(String infoRequestType){
		StringBuffer statusMessage = new StringBuffer("{");
		HashMap<String, ReportTally> reportCounts = new HashMap<String, ReportTally>();
		
		int totalAssigned = 0;
		int totalCompleted = 0;
		
		for (String curJobKey: testResults.keySet()){
			totalAssigned ++;
			TestReportJobInfo curJobInfo = testResults.get(curJobKey);
			boolean reportState = curJobInfo.isCompleted();
			String reportUrl = curJobInfo.getUrl();
			 
			
			ReportTally tally = reportCounts.get(reportUrl);
			if (tally==null){
				tally = new ReportTally();
			}
			
			if (reportState==true){
				tally.setCompleted(tally.getCompleted()+1);
				totalCompleted ++;
			} else {
				tally.setPending(tally.getPending()+1);
			}
			
			reportCounts.put(reportUrl, tally);
		}

		//iterate through the information in report counts and compile status message
		//grouped by individual URLs.
		/*
		for (String talleyKey: reportCounts.keySet()){
			ReportTally tally = reportCounts.get(talleyKey);
			statusMessage.append(talleyKey + " : " + tally.getCompleted() + " / " + tally.totalQueued() + "\n");
		}
		*/
		if (totalCompleted==totalAssigned){ //processing is done.
			statusMessage.append("\"status\":\"" + LoadTestController.C_COMPLETED + "\",");
		} else {
			statusMessage.append("\"status\":\"" + infoRequestType + "\",");
		}
		
		statusMessage.append("\"totalcompleted\":\"" + String.valueOf(totalCompleted) + "\",");
		statusMessage.append("\"totalassigned\":\"" + String.valueOf(totalAssigned) + "\"");
		statusMessage.append("}");
		return statusMessage.toString();
	}
	
	public void startTestReports(){
		List<ReportInfo> riList = serverInfo.getDiagnosticsModule().getReports(); 
		for (ReportInfo ri: riList){
			//for each Report Info object, create a dedicated job.
			String serverPath = "http://" + serverInfo.getDomain() + ":" + serverInfo.getPort() + "/" + serverInfo.getPath();
			for (int t=0; t<ri.getIterations(); t++){
				String url = ri.getUrl();
				String reportExecUuid = UUID.randomUUID().toString();
				TestReportJobInfo reportJobInfo = new TestReportJobInfo(reportExecUuid, url);
				
				int thinkTimeMs = ri.getThinkTime() * 1000;
				//int iterations = ri.getIterations(); //jsw:legacy- each job only executes once now.
				int calculatedTimeOffset = t * thinkTimeMs;
				
				testResults.put(reportExecUuid, reportJobInfo);
				
				Timer testReportTimer = new Timer("testReportTimer");
				TimerTask testReportTask = new TestReportJob(url, reportExecUuid, reportJobInfo, serverPath,
						serverInfo.getUsername(), serverInfo.getPassword(), this.loadTestId);
				testReportTimer.scheduleAtFixedRate(testReportTask, calculatedTimeOffset, thinkTimeMs);
			}
		}
	}

	public ConcurrentHashMap<String, TestReportJobInfo> getTestResults() {
		return this.testResults;
	}
	
}




