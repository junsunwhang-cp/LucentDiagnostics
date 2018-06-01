package com.tibco.jaspersoft.cs.lucent.server.logging;

import java.util.List;

import com.tibco.jaspersoft.cs.lucent.server.api.LogEntry;

public class LogTestResults {

	List<LogEntry> rawEntries = null;
	List<LogEntry> summaryEntries = null;

	public LogTestResults(){
		
	}
	
	public List<LogEntry> getRawEntries() {
		return rawEntries;
	}

	public void setRawEntries(List<LogEntry> rawEntries) {
		this.rawEntries = rawEntries;
	}

	public List<LogEntry> getSummaryEntries() {
		return summaryEntries;
	}

	public void setSummaryEntries(List<LogEntry> summaryEntries) {
		this.summaryEntries = summaryEntries;
	}
	
}
