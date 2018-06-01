package com.tibco.jaspersoft.cs.lucent.client.store;

import java.util.ArrayList;
import java.util.List;

/*
 * $Id: UserDataStore.java 253 2018-04-03 19:23:50Z jwhang $
 */
//possible future use for multi-user support.
public class UserDataStore {

	public final static String C_DEFAULT_LUCENT_USER = "default";
	private String lucentUserName = null;
	private List<ServerInfo> serverInfoList = new ArrayList<ServerInfo>();
	
	public UserDataStore(){
		this.lucentUserName = C_DEFAULT_LUCENT_USER;
	}
	
	public UserDataStore(String lucentUserName){
		this.lucentUserName = lucentUserName;
	}
	
	public String getLucentUserName() {
		return lucentUserName;
	}
	public void setLucentUserName(String lucentUserName) {
		this.lucentUserName = lucentUserName;
	}
	
	public List<ServerInfo> getServerInfoList() {
		return serverInfoList;
	}
	public void setServerInfoList(List<ServerInfo> serverInfoList) {
		this.serverInfoList = serverInfoList;
	}
}



