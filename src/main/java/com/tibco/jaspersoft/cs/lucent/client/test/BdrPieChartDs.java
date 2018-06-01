package com.tibco.jaspersoft.cs.lucent.client.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/*
 * $Id: BdrPieChartDs.java 248 2018-02-08 23:58:34Z jwhang $
 */
public class BdrPieChartDs implements JRDataSource {

	ConcurrentHashMap<String, TestReportJobInfo> testResults;
	Iterator<String> aggIt;
	String curKey = null;
	//TestReportJobInfo trJobInfo = null;
	Map<String, Long> totalTimes = new HashMap<String, Long>();
	Map<String, Long> contributionCount = new HashMap<String, Long>();
	Map<String, Long> averageTimes = new HashMap<String, Long>();
	
	public BdrPieChartDs(ConcurrentHashMap<String, TestReportJobInfo> testResults){
		this.testResults = testResults;
		//Iterator<String> trIt = this.testResults.keySet().iterator();	
		//iterate through raw results to create a new list of averaged times per url.
		for (String curTr: testResults.keySet()){
			TestReportJobInfo curJobInfo = testResults.get(curTr);
			String url = curJobInfo.getUrl();
			long elapsedTime = curJobInfo.getElapsedTime();
			Long storedTime = totalTimes.get(url);
			
			if (storedTime == null){
				//add a new entry.
				totalTimes.put(url, new Long(elapsedTime));
			} else {
				totalTimes.put(url,  new Long(storedTime.longValue() + elapsedTime));
			}
			addCount(url);
		}
		//populate average times
		for (String curAvg: totalTimes.keySet()){
			Long etPerUrl = totalTimes.get(curAvg);
			Long cCount = contributionCount.get(curAvg);
			if ((etPerUrl!=null)&&(cCount!=null)){
				averageTimes.put(curAvg, new Long(etPerUrl.longValue()/cCount.longValue()) );
			}
		}
		//set dataset "cursor"
		aggIt = averageTimes.keySet().iterator();
	}
	
	protected void addCount(String url){
		Long curVal = contributionCount.get(url);
		if (curVal == null){
			contributionCount.put(url, new Long(1));
		} else {
			contributionCount.put(url,  new Long(curVal.longValue()+1));
		}
	}
	
	public boolean next() throws JRException {
		if (aggIt.hasNext()){
			//this.trJobInfo = testResults.get(aggIt.next());
			curKey = aggIt.next();
			return true;
		} else {
			return false;
		}
	}
	
	public Object getFieldValue(JRField jrField) throws JRException {
		if (curKey!=null){
			String fieldName = jrField.getName();
			switch (fieldName) {
				case "reportUrl":
					return curKey;					
				case "averageMs":
					return averageTimes.get(curKey);
			}	
		}
		return null;
	}
}




