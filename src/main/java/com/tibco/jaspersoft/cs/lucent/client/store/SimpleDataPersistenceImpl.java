package com.tibco.jaspersoft.cs.lucent.client.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

/*
 * $Id: SimpleDataPersistenceImpl.java 286 2018-08-21 18:13:32Z jwhang $
 */
//uses a simple file to save meta information about servers (excluding the password)
//TODO: replace with a scalable data source to hold report result/metrics information.

public class SimpleDataPersistenceImpl implements DataPersistence {
	
	private String tempDir = System.getProperty("java.io.tmpdir");
	private String sepChar = System.getProperty("file.separator");
	private UserDataStore inMemDataStore = null;
	XStream xs = new XStream();
	
	public final static String C_TEMP_FILE_NAME = "csLucentClientMeta.xml";
	
	public void addServerInfo(String serverId, String hostname, String port, String path, 
			String username, String label, String notes) throws LucentException {
		//TODO: check for deltas to see if a save action to a persistant state is really required.
		
		//there should always be an in memory, up to date version of the state.
		UserDataStore uds = this.inMemDataStore;
		if (uds==null){ //generate a new version.
			
			//throw new LucentException("In memory data store not initialized.");
		} else {
			List<ServerInfo> memServerList = uds.getServerInfoList();
			//iterate through to find relevant server if any.
			ServerInfo targetSi = null;
			for (ServerInfo si: memServerList){
				if (si.getId().equalsIgnoreCase(serverId)){
					targetSi = si;
				}
			}
			if (targetSi == null){
				targetSi = new ServerInfo();
				memServerList.add(targetSi);
			}		
			targetSi.setId(serverId);
			targetSi.setDomain(hostname);
			targetSi.setPort(port);
			targetSi.setPath(path);
			targetSi.setUsername(username);
			targetSi.setLabel(label);
			targetSi.setNotes(notes);
			
			saveServerInfo(uds);
		}
	}

	public void removeServerInfo(String serverId) throws LucentException {
		//there should always be an in memory, up to date version of the state.
		UserDataStore uds = this.inMemDataStore;
		if (uds==null){
			throw new LucentException("In memory data store not initialized.");
		}
		List<ServerInfo> memServerList = uds.getServerInfoList();
		//iterate through to find relevant server if any.
		for (ServerInfo si: memServerList){
			if (si.getId().equalsIgnoreCase(serverId)){
				memServerList.remove(si);
			}
		}
		saveServerInfo(uds);
	}
	
	protected void saveServerInfo(UserDataStore dataToBeSaved) throws LucentException {
		try{
			FileWriter fw = new FileWriter(tempDir + sepChar + C_TEMP_FILE_NAME);
			this.xs.toXML(dataToBeSaved, fw);
		} catch (IOException ioe){
			ioe.printStackTrace(System.err); //FIXME:send to logger.
		}		
	}

	//this will be invoked at the start of a browser client instantiation.
	public List<ServerInfo> getServerList() {
		return getInMemoryDataStore().getServerInfoList();
	}
	
	public UserDataStore getInMemoryDataStore(){
		//check to see if an in-memory version of the data store exists already.
		if (this.inMemDataStore!=null){
			return this.inMemDataStore;
		} else { //if no in-memory version, attempt to read from file system.
			String filePath = tempDir + C_TEMP_FILE_NAME;
			System.out.println("default directory for meta data save file: " + filePath);
			
			File dataStoreFile = new File(filePath);
			Object readObj = null;
			try{
				readObj = this.xs.fromXML(dataStoreFile);
				if (readObj instanceof UserDataStore){
					this.inMemDataStore = ((UserDataStore)readObj); 
				}
			} catch (StreamException se){
				this.inMemDataStore = new UserDataStore();
			}
			return this.inMemDataStore;
		}
		
	}
	
	public String inMemoryDataStoreToString(){
		return this.xs.toXML(this.inMemDataStore);
	}
	
}






