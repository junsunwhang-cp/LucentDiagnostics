package com.tibco.jaspersoft.cs.lucent.client.store;

/*
 * $Id: ServerInfo.java 254 2018-04-05 21:36:09Z jwhang $
 */
public class ServerInfo {

	private String id = null;
	private String label = null;
	private String domain = null;
	private String port = null;
	private String path = null;
	private String username = null;
	private String password = null; // for possible future usage.
	private String notes = null;
	private DiagnosticsInfo diagnosticsModule = new DiagnosticsInfo();
	private boolean expanded = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DiagnosticsInfo getDiagnosticsModule() {
		return diagnosticsModule;
	}

	public void setDiagnosticsModule(DiagnosticsInfo diagnosticsModule) {
		this.diagnosticsModule = diagnosticsModule;
	}

}
