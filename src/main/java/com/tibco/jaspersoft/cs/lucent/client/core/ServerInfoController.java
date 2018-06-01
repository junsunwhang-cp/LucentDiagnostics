package com.tibco.jaspersoft.cs.lucent.client.core;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thoughtworks.xstream.XStream;
import com.tibco.jaspersoft.cs.lucent.client.store.LucentException;
import com.tibco.jaspersoft.cs.lucent.client.store.ServerInfo;
import com.tibco.jaspersoft.cs.lucent.client.store.UserDataStore;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/*
 * $Id: ServerInfoController.java 254 2018-04-05 21:36:09Z jwhang $
 */

@Controller
@RequestMapping("/serverinfo")
public class ServerInfoController {

	//get the initial list of established server meta data to send to the client (if any exist).
	@RequestMapping(value="/meta", method=RequestMethod.GET)
    public @ResponseBody List<ServerInfo> getServerMeta(
    		) {
    	List<ServerInfo> serverInfoList = null;
		return CsLucentClientContext.getInstance().getServerList();
    }
	
	@RequestMapping(value="/addServerMeta", method=RequestMethod.GET)
    public @ResponseBody String addServerMeta(
    		@RequestParam(value="id", required=true) String id,
    		@RequestParam(value="hostname", required=true) String hostname,
    		@RequestParam(value="port", required=true) String port,
    		@RequestParam(value="path", required=true) String path,
    		@RequestParam(value="username", required=true) String username,
    		@RequestParam(value="label", required=true) String label,
    		@RequestParam(value="notes", required=true) String notes
    		) {
    	try{
    		CsLucentClientContext.getInstance().addServerInfo(id, hostname, port, path, 
    				username, label, notes);
    		return "success";
    	} catch (LucentException le){
    		return "failure: " + le.getMessage();
    	}    	
    }
	
	@RequestMapping(value="/deleteServerMeta", method=RequestMethod.GET)
    public @ResponseBody String deleteServerMeta(
    		@RequestParam(value="id", required=true) String id
    		) {
		try{
    		CsLucentClientContext.getInstance().removeServerInfo(id);
    		return "success";
    	} catch (LucentException le){
    		return "failure: " + le.getMessage();
    	}
    }
	
	//fetch a list of reports for the server given the connection credentials submitted from the client.
    @RequestMapping(value="/reportList", method=RequestMethod.GET)
    public @ResponseBody List<ResourceLookup> getServerInfo(
    		@RequestParam(value="id", required=true) String serverId,
    		@RequestParam(value="hostname", required=true) String hostname,
    		@RequestParam(value="port", required=true) String port,
    		@RequestParam(value="path", required=true) String path,
    		@RequestParam(value="username", required=true) String username,
    		@RequestParam(value="password", required=true) String password,
    		@RequestParam(value="label", required=true) String label,
    		@RequestParam(value="notes", required=true) String notes
    		) {
    	
    	try{
    		CsLucentClientContext.getInstance().addServerInfo(serverId, hostname, port, path, 
    				username, label, notes);
    	} catch (LucentException le){
    		le.printStackTrace(System.err); //FIXME: send to logger.
    	}    	
    	List<ResourceLookup> eventList = getServerReportList(hostname, port, path, username, password);
    	return eventList;
    }
    
    protected List getServerReportList(String hostname, String port, String path, String username, String password){
		HttpClient client = null;
		try {			
			//fetch the list of reports.
			client = HttpClientBuilder.create().build();
			String requestString = "http://" + hostname + ":" + port + "/" + path + "/rest_v2/resources?type=reportUnit&j_username=" + username + "&j_password=" + password;
			/*
			HttpGet request = new HttpGet(
					"http://localhost:8080/jasperserver-pro/rest_v2/resources?type=reportUnit&j_username=superuser&j_password=superuser");
			*/
			HttpGet request = new HttpGet(requestString);
			HttpResponse response = client.execute(request);
			InputStream is = response.getEntity().getContent();
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String theString = writer.toString();

			XStream xs = new XStream();
			xs.alias("resources", List.class);
			xs.alias("resourceLookup", ResourceLookup.class);
			Object obj = xs.fromXML(theString);
			return (List)obj;			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {

			}
		}
		return null;
    }
	
}
