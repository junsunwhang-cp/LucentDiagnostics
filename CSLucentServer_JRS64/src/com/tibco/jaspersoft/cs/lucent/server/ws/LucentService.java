package com.tibco.jaspersoft.cs.lucent.server.ws;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jaspersoft.jasperserver.remote.exception.RemoteException;

/*
 * $Id: LucentService.java 273 2018-05-04 06:25:18Z jwhang $
 */
public interface LucentService {

	public final static String TEST_ID = "lucent_test_id";
	
	String getMetrics(HttpServletRequest request, Map<String,String[]> parameterMap) throws RemoteException;
	
}





