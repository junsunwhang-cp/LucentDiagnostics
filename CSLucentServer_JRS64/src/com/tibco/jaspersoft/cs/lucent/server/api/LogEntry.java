package com.tibco.jaspersoft.cs.lucent.server.api;

import java.io.Serializable;

/*
 * $Id: LogEntry.java 273 2018-05-04 06:25:18Z jwhang $
 */
public interface LogEntry extends Serializable {

	public EntryCategory getEntryCategory();
	public void setEntryCategory(EntryCategory entryCategory);
	
	public long getStartTimeMs();
	public void setStartTimeMs(long startTimeMs);
	public long getStartTimeSeconds();
	
	public long getElapsedTimeNs();
	public void setElapsedTimeNs(long elapsedTimeNs);
	
}
