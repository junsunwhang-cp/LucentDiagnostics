package com.tibco.jaspersoft.cs.lucent.client.store;

import java.util.List;

/*
 * $Id: DataPersistence.java 253 2018-04-03 19:23:50Z jwhang $
 */
public interface DataPersistence {

	public void addServerInfo(String serverId, String hostname, String port, String path, 
			String username, String label, String notes) throws LucentException;
	public void removeServerInfo(String serverId) throws LucentException;
	public List<ServerInfo> getServerList();
	public UserDataStore getInMemoryDataStore();
}
