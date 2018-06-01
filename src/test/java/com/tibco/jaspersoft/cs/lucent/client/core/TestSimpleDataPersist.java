package com.tibco.jaspersoft.cs.lucent.client.core;

import java.util.List;

import com.tibco.jaspersoft.cs.lucent.client.store.LucentException;
import com.tibco.jaspersoft.cs.lucent.client.store.ServerInfo;
import com.tibco.jaspersoft.cs.lucent.client.store.SimpleDataPersistenceImpl;
import com.tibco.jaspersoft.cs.lucent.client.store.UserDataStore;

public class TestSimpleDataPersist {

	public static void main(String[] args) {
		TestSimpleDataPersist tsdp = new TestSimpleDataPersist();
		tsdp.test();
	}
	
	public void test(){
		SimpleDataPersistenceImpl sd = new SimpleDataPersistenceImpl();
		UserDataStore uds = sd.getInMemoryDataStore();
		List<ServerInfo> siList = uds.getServerInfoList();
		
		ServerInfo si1 = new ServerInfo();
		si1.setId("id1");
		si1.setDomain("localhost");
		si1.setPort("8080");
		si1.setPath("/jasperserver-pro");
		si1.setUsername("superuser");
		si1.setLabel("server 1 label");
		si1.setNotes("notes for server 1.");
		
		ServerInfo si2 = new ServerInfo();
		si2.setId("id2");
		si2.setDomain("localhost");
		si2.setPort("8081");
		si2.setPath("/jasperserver-pro");
		si2.setUsername("superuser");
		si2.setLabel("server 2 label");
		si2.setNotes("notes for server 2.");
		
		siList.add(si1);
		siList.add(si2);
		
		try{
			sd.addServerInfo("id3", "localhost", "8081", "japserserver-pro", "testUser", "testLabel", "server 3 notes.");
		} catch (LucentException le){
			le.printStackTrace();
		}
		
		System.out.println(sd.inMemoryDataStoreToString());
	}

}





