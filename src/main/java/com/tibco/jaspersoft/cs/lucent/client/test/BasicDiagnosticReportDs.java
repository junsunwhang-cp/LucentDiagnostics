package com.tibco.jaspersoft.cs.lucent.client.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/*
 * $Id: BasicDiagnosticReportDs.java 248 2018-02-08 23:58:34Z jwhang $
 */
public class BasicDiagnosticReportDs implements JRDataSource {

	ConcurrentHashMap<String, TestReportJobInfo> testResults;
	Iterator<String> trIt;
	TestReportJobInfo trJobInfo = null; //effectivey a row cursor.
	//SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
	
	public BasicDiagnosticReportDs(ConcurrentHashMap<String, TestReportJobInfo> testResults){
		this.testResults = testResults;
		trIt = this.testResults.keySet().iterator();
		/*
		if (trIt.hasNext()){ //populate the first object.
			this.trJobInfo = testResults.get(trIt.next());
		}
		*/
	}
	
	public boolean next() throws JRException {		
		if (trIt.hasNext()){
			this.trJobInfo = testResults.get(trIt.next());
			return true;
		} else {
			return false;
		}
	}
	
	public Object getFieldValue(JRField jrField) throws JRException {
		if (trJobInfo!=null){
			String fieldName = jrField.getName();
			switch (fieldName) {
				case "reportUrl":
					return trJobInfo.getUrl();					
				case "timeMs":
					return new Long(trJobInfo.getElapsedTime());
				case "startTime":
					return new Date(trJobInfo.getStartTime());
			}	
		}
		return null;
	}
	
	public JRDataSource getPieChartDataSource(){
		return new BdrPieChartDs(this.testResults);
	}
	
}




