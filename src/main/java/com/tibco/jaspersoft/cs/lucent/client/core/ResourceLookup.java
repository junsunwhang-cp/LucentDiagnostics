package com.tibco.jaspersoft.cs.lucent.client.core;

/*
 * $Id: ResourceLookup.java 226 2018-01-03 21:24:55Z jwhang $
 */
public class ResourceLookup {

	private String creationDate;
	private String description;
	private String label;
	private String permissionMask;
	private String updateDate;
	private String uri;
	private String version;
	private String resourceType;
	private boolean selectable=false;

	public ResourceLookup() {
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPermissionMask() {
		return permissionMask;
	}

	public void setPermissionMask(String permissionMask) {
		this.permissionMask = permissionMask;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	
}
