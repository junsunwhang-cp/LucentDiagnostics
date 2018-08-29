package com.tibco.jaspersoft.cs.lucent.server.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.jaspersoft.cs.lucent.server.api.LogAggregate;
import com.tibco.jaspersoft.cs.lucent.server.api.LogEntry;
import com.tibco.jaspersoft.cs.lucent.server.api.LucentGlobalContext;
import com.tibco.jaspersoft.cs.lucent.server.logging.BasicLogAggImpl;

import java.util.concurrent.ConcurrentLinkedDeque;

/*
 * $Id: DataStoreImpl.java 287 2018-08-29 09:09:08Z jwhang $
 */
public class DataStoreImpl implements DataStore {

	//total list of aggregates are keyed by the test Id, then by a list of log aggregates.
	private Map<String, Map<String, LogAggregate>> recordEntries = new ConcurrentHashMap<String, Map<String, LogAggregate>>();
	private Map<String, Map<String, LogAggregate>> entriesByTransactionId = new ConcurrentHashMap<String, Map<String, LogAggregate>>();
	private Map<String, HashSet<String>> testsToTransactionsSet = new ConcurrentHashMap<String, HashSet<String>>();
	private Map<String, Map<String,String>> transactionToPropertyBag = new ConcurrentHashMap<String, Map<String,String>>();
	private long maxLifeSpan = 60 * 60 * 1000; //1 hour
	private final int maxInterval = 100;
	private int intervalCounter = 0; //used to reduce frequency of checks as writing log entries may be a high volume set of operations.
	
	public void writeLogEntry(LogEntry entry) {
		//TODO:
		//at some interval, check to see if any stale entries exist and automatically remove them.  //TODO: scaffold code, implement long term solution.
		
		//check if any other entries of this type exist, if not create a new map and add an entry to the creation timestamp list.
		String testId = String.valueOf(LucentGlobalContext.getInstance().getFlowContext().getTestId());
		Map<String, LogAggregate> entries = recordEntries.get(testId);
		//if an entry list does exist for this test ID, add an additional log sample into the mix.
		if (entries==null){ //create a new entry.
			entries = new ConcurrentHashMap<String,LogAggregate>();
			recordEntries.put(testId, entries);
		}
		
		//get entries by transaction Id.
		String transactionId = String.valueOf(LucentGlobalContext.getInstance().getFlowContext().getTransactionId());
		Map<String, LogAggregate> transactionEntries = entriesByTransactionId.get(transactionId);
		if (transactionEntries == null){
			transactionEntries = new ConcurrentHashMap<String, LogAggregate>();
			entriesByTransactionId.put(transactionId, transactionEntries);
		} 
		
		//populate test to transaction map.
		HashSet relTransactionSet = testsToTransactionsSet.get(testId);
		if (relTransactionSet !=null){ //already registered.
			relTransactionSet.add(transactionId);
		} else { //create a new hash set.
			relTransactionSet = new HashSet<String>();
			relTransactionSet.add(transactionId);
			testsToTransactionsSet.put(testId, relTransactionSet);
		}
		
		String catLabel = entry.getEntryCategory().getLabel();
		
		//global entries.
		LogAggregate catEntry = entries.get(catLabel);
		if (catEntry==null){
			catEntry = new BasicLogAggImpl(catLabel, entry.getStartTimeMs(), entry.getElapsedTimeNs());
		} else {
			catEntry.increment(entry.getElapsedTimeNs());
		}
		entries.put(catLabel, catEntry);
		
		//transaction level entries.
		LogAggregate catTransEntry = transactionEntries.get(catLabel);
		if (catTransEntry==null){
			catTransEntry = new BasicLogAggImpl(catLabel, entry.getStartTimeMs(), entry.getElapsedTimeNs());
		} else {
			catTransEntry.increment(entry.getElapsedTimeNs());
		}
		transactionEntries.put(catLabel, catTransEntry);
	}
	
	public Set<String> getTransactionsforTestId(String testId){
		return testsToTransactionsSet.get(testId);
	}
	
	public Map<String, LogAggregate> readLogEntriesByTransaction(String transactionId){
		Map<String, LogAggregate> entriesByTrans = entriesByTransactionId.get(transactionId);
		return entriesByTrans;
	}
	
	public void recordPropertyBagChange(){
		String transactionId = LucentGlobalContext.getInstance().getFlowContext().getTransactionId();
		transactionToPropertyBag.put(transactionId, LucentGlobalContext.getInstance().getFlowContext().getPropertyBag());
	}
	
	public Map<String,String> getPropertyBagForTransactionId(String transactionId){
		return transactionToPropertyBag.get(transactionId);
	}

	public Map<String, LogAggregate> readLogEntries(String testId) {
		Map<String, LogAggregate> entries = recordEntries.get(testId);
		if (entries!=null){
			return entries;
		} else { //this should never happen; log an exception.
			System.err.println("Missing log entry collection.");
		}
		return null;
	}
	
	//TODO: may need to remove this as all returned results should be aggregated values, not individual.
	public String summarizeLogEntries(String testId){
		return null;
	}

	//tests should retrieve results in relatively short order to minimize heap usage
	//and delete the information on the server.
	public void deleteLogEntries(String testId) {
		
	}
	
}




