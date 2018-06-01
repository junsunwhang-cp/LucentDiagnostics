package com.tibco.jaspersoft.cs.lucent.client.core;

import java.util.List;

import com.tibco.jaspersoft.cs.lucent.client.store.DataPersistence;
import com.tibco.jaspersoft.cs.lucent.client.store.LucentException;
import com.tibco.jaspersoft.cs.lucent.client.store.ServerInfo;
import com.tibco.jaspersoft.cs.lucent.client.store.SimpleDataPersistenceImpl;
import com.tibco.jaspersoft.cs.lucent.client.store.UserDataStore;

/*
 * $Id: CsLucentClientContext.java 253 2018-04-03 19:23:50Z jwhang $
 */

public class CsLucentClientContext implements DataPersistence {

	private static CsLucentClientContext csLucentClientContext = null;
	private DataPersistence dataPersistence = new SimpleDataPersistenceImpl();
	
	private CsLucentClientContext(){
		
	}
	
	public synchronized static CsLucentClientContext getInstance(){
		if (csLucentClientContext==null){
			csLucentClientContext = new CsLucentClientContext();
		}
		return csLucentClientContext;		
	}

	public void addServerInfo(String id, String hostname, String port, String path, 
			String username, String label, String notes) throws LucentException {
		this.dataPersistence.addServerInfo(id, hostname, port, path, username, label, notes);
	} 

	public void removeServerInfo(String id) throws LucentException {
		this.dataPersistence.removeServerInfo(id);
	}

	public List<ServerInfo> getServerList() {
		return this.dataPersistence.getServerList(); 
	}

	public UserDataStore getInMemoryDataStore() {
		return this.dataPersistence.getInMemoryDataStore();
	}	
	
}
