package com.tibco.jaspersoft.cs.lucent.client.store;

/*
 * $Id: ReportInfo.java 286 2018-08-21 18:13:32Z jwhang $
 */
public class ReportInfo {

	private boolean selected = true;
	private int iterations = 1;
	private int thinkTime = 60;
	private String name = null;
	private String url = null;
	private String reportParameters = null;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public int getThinkTime() {
		return thinkTime;
	}

	public void setThinkTime(int thinkTime) {
		this.thinkTime = thinkTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReportParameters() {
		return reportParameters;
	}

	public void setReportParameters(String reportParameters) {
		this.reportParameters = reportParameters;
	}

}
