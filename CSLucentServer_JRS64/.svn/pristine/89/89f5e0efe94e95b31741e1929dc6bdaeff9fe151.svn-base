package com.tibco.jaspersoft.cs.lucent.server.logging;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tibco.jaspersoft.cs.lucent.server.api.EntryCategory;
import com.tibco.jaspersoft.cs.lucent.server.core.LucentConfiguration;

/*
 * $Id$
 */
public class EntryCategoryImpl implements EntryCategory {

	private static final Log log = LogFactory.getLog(EntryCategoryImpl.class);
	
	//for internal use only.
	protected static final String EC_Root = "ec.root"; 
	private static EntryCategory rootCategory = new EntryCategoryImpl(EC_Root, "Root");
	private static EntryCategory undefinedCategory = new EntryCategoryImpl(EC_UNDEFINED, "Undefined");
	
	private String id = null;
	private String label = null;
	EntryCategory parent = null;
	List<EntryCategory> children = null;
	
	public static EntryCategory getRoot(){
		return rootCategory;
	}
	
	public static EntryCategory getUndefinedCategory(){
		return undefinedCategory;
	}
	
	public EntryCategoryImpl(String id, String label){ //create a category at the top level.
		this.id = id;
		this.label = label;
		this.parent = EntryCategoryImpl.getRoot();
	}
	
	public EntryCategoryImpl(String id, String label, String parentId){
		this.id = id;
		this.label = label;
		EntryCategory parentCategory = LucentConfiguration.getCategories().get(parentId);
		if (parentCategory!=null) {
			this.parent = parentCategory;
		} else {
			log.error("Invalid parent category used in construction of subcategory " + id + "," + label);
		}
	}
	
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

	public EntryCategory getParent() {
		return parent;
	}

	public void setParent(EntryCategory parent) {
		this.parent = parent;
	}

	public boolean isTopLevel() {
		//calculate this based on if the current parent is the EC root.
		if ((this.parent!=null)&&(this.parent.getId().equals(EC_Root) )){
			return true;
		}
		return false;
	}

	public List<EntryCategory> getChildren() {
		return null;
	}

	public boolean hasChildren() {
		if ((children!=null)&&(children.size()>0)){
			return true;
		}
		return false;
	}
	
}








