package com.tibco.jaspersoft.cs.lucent.client.store;

import java.util.ArrayList;
import java.util.List;

/*
 * $Id: DiagnosticsInfo.java 259 2018-04-17 23:54:35Z jwhang $
 */
public class DiagnosticsInfo {

	private List<ReportInfo> reports = new ArrayList<ReportInfo>();
	private boolean expanded = false;

	public List<ReportInfo> getReports() {
		return reports;
	}

	public void setReports(List<ReportInfo> reports) {
		this.reports = reports;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}




