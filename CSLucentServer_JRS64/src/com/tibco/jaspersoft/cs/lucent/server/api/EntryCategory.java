package com.tibco.jaspersoft.cs.lucent.server.api;

import java.util.List;

/*
 * $Id: EntryCategory.java 204 2017-08-23 23:51:52Z jwhang $
 */
public interface EntryCategory {

	public static final String EC_UNDEFINED = "ec.undefined";
	public static final String EC_ReportUnitSetup = "ec.reportUnitSetup";
	public static final String EC_DataRetrieval = "ec.dataRetrieval";
	public static final String EC_ReportExport = "ec.reportExport";
	public static final String EC_ReportFill = "ec.reportFill";
	public static final String EC_ReportFill_Initialize = "ec.reportFill.initialize";
	public static final String EC_ReportFill_RetrieveField = "ec.reportFill.retrieveField";
	public static final String EC_ReportFill_NextRow = "ec.reportFill.nextRow";
	
	public String getId();
	public String getLabel();
	public EntryCategory getParent();
	public boolean isTopLevel();
	public List<EntryCategory> getChildren();
	public boolean hasChildren();
	
}



