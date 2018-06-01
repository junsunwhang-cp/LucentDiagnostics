package com.tibco.jaspersoft.cs.lucent.client.store;

/*
 * $Id: ReportInfo.java 252 2018-04-02 23:09:28Z jwhang $
 */
public class ReportInfo {

	private boolean selected = true;
	private int iterations = 1;
	private int thinkTime = 60;
	private String name = null;
	private String url = null;

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

}
