package com.tibco.jaspersoft.cs.lucent.client.core;

import java.util.concurrent.ConcurrentHashMap;

import com.tibco.jaspersoft.cs.lucent.client.test.LoadTestContainer;

/*
 * $Id: InMemoryDataStore.java 288 2018-09-19 23:27:26Z jwhang $
 */
public class InMemoryDataStore {

	//public final static String C_LOADTESTID = "LoadTestId";
	
	public final static String C_LOADTESTID = "lucent_test_id";
	
	private static ConcurrentHashMap<String, LoadTestContainer> runningLoadTests = new ConcurrentHashMap<String, LoadTestContainer>();

	public static ConcurrentHashMap<String, LoadTestContainer> getRunningLoadTests() {
		return runningLoadTests;
	}

	/*
	public static void setRunningLoadTests(ConcurrentHashMap<String, LoadTestContainer> runningLoadTests) {
		InMemoryDataStore.runningLoadTests = runningLoadTests;
	}
	*/

}
