package com.tibco.jaspersoft.cs.lucent.server.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.tibco.jaspersoft.cs.lucent.server.api.LogAggregate;
import com.tibco.jaspersoft.cs.lucent.server.api.LogEntry;

/*
 * $Id: DataStore.java 281 2018-05-21 18:41:55Z jwhang $
 */
public interface DataStore {

	public void writeLogEntry(LogEntry entry);
	public Map<String, LogAggregate> readLogEntries(String testId);
	public String summarizeLogEntries(String testId);
	public void deleteLogEntries(String testId);
	
}


