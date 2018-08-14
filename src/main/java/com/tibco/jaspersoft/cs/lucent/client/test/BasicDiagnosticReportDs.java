package com.tibco.jaspersoft.cs.lucent.client.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.data.IndexedDataSource;
import net.sf.jasperreports.engine.fill.SortedDataSource;

/*
 * $Id: BasicDiagnosticReportDs.java 248 2018-02-08 23:58:34Z jwhang $
 */
public class BasicDiagnosticReportDs implements JRRewindableDataSource, IndexedDataSource {

	ConcurrentHashMap<String, TestReportJobInfo> testResults;
	List<TestReportJobInfo> testResultsList;
	
	//sort test results.
	
	
	//Iterator<String> trIt;
	Iterator<TestReportJobInfo> trIt;
	TestReportJobInfo trJobInfo = null; //effectivey a row cursor.
	int recordIndex = 0;
	//SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
	
	public BasicDiagnosticReportDs(ConcurrentHashMap<String, TestReportJobInfo> testResults){
		this.testResults = testResults;
		this.testResultsList = new ArrayList<TestReportJobInfo>(this.testResults.values());
		Collections.sort(testResultsList, new TestReportJobInfo());
		//this.trIt = this.testResults.keySet().iterator();
		this.trIt = this.testResultsList.iterator();
		
		/*
		if (trIt.hasNext()){ //populate the first object.
			this.trJobInfo = testResults.get(trIt.next());
		}
		*/
	}
	
	public boolean next() throws JRException {		
		if (this.trIt.hasNext()){
			//this.trJobInfo = testResults.get(trIt.next());
			this.trJobInfo = trIt.next();
			recordIndex++;
			return true;
		} else {
			return false;
		}
	}
	
	public Object getFieldValue(JRField jrField) throws JRException {
		if (this.trJobInfo!=null){
			String fieldName = jrField.getName();
			switch (fieldName) {
				case "reportUrl":
					return this.trJobInfo.getUrl();					
				case "timeMs":
					return new Long(this.trJobInfo.getElapsedTime());
				case "startTime":
					return new Date(this.trJobInfo.getStartTime());
			}	
		}
		return null;
	}
	
	public JRDataSource getPieChartDataSource(){
		return new BdrPieChartDs(this.testResults);
	}

	public int getRecordIndex() {
		return this.recordIndex;
	}

	public void moveFirst() throws JRException {
		this.recordIndex = 0;
		//this.trIt = this.testResults.keySet().iterator(); //assign a new iterator.
		this.trIt = this.testResultsList.iterator(); //assign a new iterator.
		next(); //move to the first row.
	}
	
	
}




