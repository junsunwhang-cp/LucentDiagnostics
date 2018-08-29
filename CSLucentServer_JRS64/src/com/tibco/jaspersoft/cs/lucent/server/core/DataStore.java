package com.tibco.jaspersoft.cs.lucent.server.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.jaspersoft.cs.lucent.server.api.LogAggregate;
import com.tibco.jaspersoft.cs.lucent.server.api.LogEntry;

/*
 * $Id: DataStore.java 287 2018-08-29 09:09:08Z jwhang $
 */
public interface DataStore {

	public void writeLogEntry(LogEntry entry);
	public void recordPropertyBagChange();
	public Map<String,String> getPropertyBagForTransactionId(String transactionId);
	
	public Set<String> getTransactionsforTestId(String testId);
	
	public Map<String, LogAggregate> readLogEntriesByTransaction(String transactionId);
	public Map<String, LogAggregate> readLogEntries(String testId);
	public String summarizeLogEntries(String testId);
	public void deleteLogEntries(String testId);
	
}


