package com.tibco.jaspersoft.cs.lucent.client.core;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tibco.jaspersoft.cs.lucent.client.store.ServerInfo;
import com.tibco.jaspersoft.cs.lucent.client.test.LoadTestContainer;

/*
 * $Id: LoadTestController.java 241 2018-01-30 06:57:07Z jwhang $ 
 */

@Controller
@RequestMapping("/loadtest")
public class LoadTestController {
	
	public final static String C_START = "START";
	public final static String C_STATUS = "STATUS";
	public final static String C_COMPLETED = "COMPLETED";
	
	@RequestMapping(value="/start",method=RequestMethod.POST)
	public @ResponseBody String loadTest(
				@RequestHeader("Content-Type") String contentType,
				@RequestHeader(InMemoryDataStore.C_LOADTESTID) String loadTestId,
				@RequestBody ServerInfo serverInfo
			){
		
		//proto.start //FIXME: implemenet client side generated test container IDs.
		System.out.println("load Test ID: " + loadTestId);
		//proto.end
		
		LoadTestContainer ltc = new LoadTestContainer(loadTestId, serverInfo);
		InMemoryDataStore.getRunningLoadTests().put(loadTestId, ltc);
		ltc.startTestReports();	
		
		//defunct- no need to wait for iterations as the jobs will keep running and get polled for status.
		/*
		//wait for all iterations to complete.
		while (tally.isExecutionComplete()==false){
			try{
				Thread.sleep(1000);
			} catch (Exception e){ //TODO: refine error trapping.
				e.printStackTrace(System.err);
			}
		}
		responseString = "{\"result\":\"" + tally.generateSummary() + "\"}";
		*/
		//String returnMessage = "{\"status\":\"" + StringEscapeUtils.escapeJson(ltc.getTestStatus()) + "\"}";
		
		String returnMessage = ltc.getTestStatus(C_START);
		System.out.println("initial response to browser:\n" + returnMessage);
		
		return returnMessage;
	}
	
	@RequestMapping(value="/status",method=RequestMethod.POST)
	public @ResponseBody String status(
			@RequestHeader("Content-Type") String contentType,
			@RequestHeader(InMemoryDataStore.C_LOADTESTID) String loadTestId,
			@RequestBody ServerInfo serverInfo
			){
		LoadTestContainer relevantContainer = InMemoryDataStore.getRunningLoadTests().get(loadTestId);
		if (relevantContainer!=null){
			return relevantContainer.getTestStatus(C_STATUS);
		} else {
			return "{\"status\":\"" + "404.NOT_FOUND" + "\"}";
		}
	}
}





