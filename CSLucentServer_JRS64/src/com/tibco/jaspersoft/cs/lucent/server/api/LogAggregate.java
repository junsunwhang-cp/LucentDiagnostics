package com.tibco.jaspersoft.cs.lucent.server.api;

/*
 * $Id: LogAggregate.java 282 2018-05-23 03:13:55Z jwhang $
 */
public interface LogAggregate {

	public String getEventType();
	public long getCount();
	public long getAggregatedTimeNs();
	public void increment(long timeNs);
	
	public long getCreationTimeMs();
	public long getLastUpdateTimeMs();
	public String getXmlRepresentation();
}
