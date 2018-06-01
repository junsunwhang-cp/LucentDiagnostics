package com.tibco.jaspersoft.cs.lucent.client.core;

import java.io.InputStream;

import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ViewReportServletTest {

	public static void main(String [] args){
		ViewReportServletTest vrst = new ViewReportServletTest();
		vrst.doTest();
	}
	
	public void doTest(){
		try{
			InputStream resourceInputStream = this.getClass().getResourceAsStream("/com/tibco/jaspersoft/cs/lucent/client/test/resources/BasicDiagnosticReport.jrxml");  
			JasperDesign jDesign = JRXmlLoader.load(resourceInputStream);
			System.out.println(jDesign.toString());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}



