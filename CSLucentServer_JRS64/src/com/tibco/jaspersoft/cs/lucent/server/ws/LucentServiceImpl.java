package com.tibco.jaspersoft.cs.lucent.server.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.tibco.jaspersoft.cs.lucent.server.api.LogAggregate;
import com.tibco.jaspersoft.cs.lucent.server.api.LogEntry;
import com.tibco.jaspersoft.cs.lucent.server.api.LucentGlobalContext;
import com.tibco.jaspersoft.cs.lucent.server.logging.LogEntryImpl;
import com.tibco.jaspersoft.cs.lucent.server.logging.LogTestResults;

/*
 * $Id: LucentServiceImpl.java 285 2018-05-23 23:26:51Z jwhang $
 */
public class LucentServiceImpl implements LucentService, BeanFactoryAware {

	private BeanFactory beanFactory;
	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	public String getMetrics(HttpServletRequest request,  Map<String,String[]> parameterMap) throws RemoteException {
		StringBuffer sb = new StringBuffer();
		//Queue<LogEntry> entryQueue = LucentGlobalContext.getInstance().getEntryQueue();
		String testId = null; 
		Object testIdObj = parameterMap.get(LucentService.TEST_ID);
		if ((testIdObj!=null)&&(testIdObj instanceof String[])){
			testId = String.valueOf( ((String[])testIdObj)[0] );
			Map<String, LogAggregate> entryList = LucentGlobalContext.getInstance().readLogEntries(testId);	
			
			
			sb.append("<Metrics testId=\"" + testId + "\">\n");
			Set<String> entryKeys = entryList.keySet();
			Iterator<String> keyIt = entryKeys.iterator();
			while (keyIt.hasNext()){
				LogAggregate nextAgg = entryList.get(keyIt.next());
				sb.append(nextAgg.getXmlRepresentation());
			}
			sb.append("</Metrics>\n");
			
			/*
			//removed from 6.4.3 version of JRS.
			XStream xs = new XStream();
			xs.alias("LogAggregate", LogAggregate.class);
			sb.append(xs.toXML(entryList));
			return sb.toString();
			*/
			
			return sb.toString();
		} else {
			return "Error: invalid/missing test id.";
		}
	}
	
	/*
	public String getMetrics(HttpServletRequest request,  Map<String,String[]> parameterMap) throws RemoteException {
		StringBuffer sb = new StringBuffer();
		//Queue<LogEntry> entryQueue = LucentGlobalContext.getInstance().getEntryQueue();
		
		List<LogEntry> entryList = LucentGlobalContext.getInstance().readAllEntries();
		//set raw data.
		LogTestResults ltr = new LogTestResults();
		ltr.setRawEntries(new ArrayList(entryQueue));
		
		//get summary of data:
		Iterator<LogEntry> iq = entryQueue.iterator();
		long sumNext = 0;
		long sumGetField = 0;
		long sumInit = 0;
		while (iq.hasNext()){
			LogEntry curEntry = iq.next();
			//sb.append(curEntry.getType() + " " + String.valueOf(curEntry.getElapsedTime()) + "\n");
			
			if (curEntry.getType()== LogEntryImpl.EventType.retrieveField){
				sumGetField += curEntry.getElapsedTime();
			}
			if (curEntry.getType()==LogEntryImpl.EventType.nextRow){
				sumNext += curEntry.getElapsedTime();
			}
			if (curEntry.getType()==LogEntryImpl.EventType.initialize){
				sumInit += curEntry.getElapsedTime();
			}
		}
		//sb.append("<hr>next:" + String.valueOf(sumNext) + " , getField:" + String.valueOf(sumGetField) + " , intialize: " + String.valueOf(sumInit) + "</hr>");
		
		XStream xs = new XStream();
		xs.alias("LogEntry", LogEntry.class);
		sb.append(xs.toXML(ltr));
		
		return sb.toString();
	}
	*/

}





