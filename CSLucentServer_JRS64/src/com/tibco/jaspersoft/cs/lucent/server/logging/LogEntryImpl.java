package com.tibco.jaspersoft.cs.lucent.server.logging;

import java.util.Date;

import com.tibco.jaspersoft.cs.lucent.server.api.EntryCategory;
import com.tibco.jaspersoft.cs.lucent.server.api.LogEntry;
import com.tibco.jaspersoft.cs.lucent.server.core.LucentConfiguration;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/*
 * $Id: LogEntryImpl.java 273 2018-05-04 06:25:18Z jwhang $
 */
public class LogEntryImpl implements LogEntry {

	private final static long serialVersionUID = 1l;
	
	private EntryCategory entryCategory;
	private Object value;
	private long startTimeMs;
	private long elapsedTimeNs;
		
	public static void main(String [] args){
		long curNs = System.nanoTime();
		long curMs = TimeUnit.NANOSECONDS.toMillis(curNs);
		long curSec = TimeUnit.NANOSECONDS.toSeconds(curNs);
		System.out.println(curNs + "," + curMs + "," + curSec);
		System.out.println(new Date(curMs));
		long oMs = System.currentTimeMillis();
		System.out.println(oMs);
		System.out.println(new Date(oMs));
	}
	
	public LogEntryImpl(){
	}
	
	public LogEntryImpl(String entryCategoryId, long startTimeMs, long elapsedTimeNs, Object value){
		this.startTimeMs = startTimeMs;
		this.elapsedTimeNs = elapsedTimeNs;
		this.value = value;
		this.entryCategory = getEntryCategoryById(entryCategoryId);
	}
	
	public EntryCategory getEntryCategory() {
		return entryCategory;
	}

	public void setEntryCategory(EntryCategory entryCategory) {
		this.entryCategory = entryCategory;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public long getStartTimeMs() {
		return startTimeMs;
	}

	public void setStartTimeMs(long startTimeMs) {
		this.startTimeMs = startTimeMs;
	}

	public long getElapsedTimeNs() {
		return elapsedTimeNs;
	}

	public void setElapsedTimeNs(long elapsedTimeNs) {
		this.elapsedTimeNs = elapsedTimeNs;
	}

	protected EntryCategory getEntryCategoryById(String id){
		EntryCategory entryCat = LucentConfiguration.getCategories().get(id);
		if (entryCat!=null){
			return entryCat;
		} else { //return an undefined category.
			return EntryCategoryImpl.getUndefinedCategory();
		}
	}
	
	public long getStartTimeSeconds(){
		return TimeUnit.MILLISECONDS.toSeconds(this.startTimeMs);
	}
}









