package com.tibco.jaspersoft.cs.lucent.client.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

import com.tibco.jaspersoft.cs.lucent.client.test.BasicDiagnosticReportDs;
import com.tibco.jaspersoft.cs.lucent.client.test.LoadTestContainer;
import com.tibco.jaspersoft.cs.lucent.client.test.TestReportJobInfo;

import net.sf.jasperreports.engine.xml.JRXmlLoader;

/*
 * $Id: ViewReportServlet.java 288 2018-09-19 23:27:26Z jwhang $
 */
public class ViewReportServlet extends HttpServlet {
	
	private static final String C_DOMAIN = "domain";
	private static final String C_PORT = "port";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoadTestContainer targetContainer = null;
		ServletContext context = this.getServletConfig().getServletContext();
		
		//start.evaluate parameters.
		String paramTestId = "";
		String paramDomain = "";
		String paramPort = "";
		
		Object testIdObj = request.getParameter(InMemoryDataStore.C_LOADTESTID);
		if (testIdObj !=null){
			targetContainer = InMemoryDataStore.getRunningLoadTests().get(String.valueOf(testIdObj));
			paramTestId = String.valueOf(testIdObj);
			System.out.println("/viewReport, loadTestId: " + paramTestId);
		}
		Object paramDomainObj = request.getParameter(C_DOMAIN);
		if (paramDomainObj!=null){
			paramDomain = String.valueOf(paramDomainObj);
		}
		Object paramPortObj = request.getParameter(C_PORT); 
		if (paramPortObj!=null){
			paramPort = String.valueOf(paramPortObj);
		}
		//end.evaluateParameters.
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//check if valid results were obtained.
		if (targetContainer != null) { // return report output.
			//generate 2x datasources for the report.
			JRDataSource mainDS = null;		
			ConcurrentHashMap<String, TestReportJobInfo> testResults = targetContainer.getTestResults();
			
			for (String curJobKey: testResults.keySet()){
				TestReportJobInfo curJobInfo = testResults.get(curJobKey);
			}
			
			//get a handle to the report template within the resources package.
			//render the report and send the output to the servlet output stream.
			try{
				InputStream resourceInputStream = this.getClass().getResourceAsStream("/com/tibco/jaspersoft/cs/lucent/client/test/resources/BasicDiagnosticReport.jrxml");  
				JasperDesign jDesign = JRXmlLoader.load(resourceInputStream);
				JasperReport jReport = JasperCompileManager.compileReport(jDesign);
				HashMap<String,Object> parameters = new HashMap<String,Object>();
				//populate data set.
				//TODO: search persistent data store as well once that is generated.
				LoadTestContainer ltc = InMemoryDataStore.getRunningLoadTests().get(String.valueOf(testIdObj));
				mainDS = new BasicDiagnosticReportDs(ltc.getTestResults());
				JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameters, mainDS);
				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jPrint);
				//ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				HtmlExporter exporter = new HtmlExporter();
				exporter.setExporterInput(new SimpleExporterInput(jPrint));
				//SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(outputStream);
				SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(out);
				output.setImageHandler(new WebHtmlResourceHandler("image?image={0}"));
				exporter.setExporterOutput(output);				
				exporter.exportReport();
				//outputStream.close();
				
				if ((paramTestId.length()>0)||(paramPort.length()>0)){
					out.write("<a href=\"retrieveTestData?cs_testDataKey=" + paramTestId + "&cs_serverPath=" + paramDomain + ":" + paramPort + "/jasperserver-pro_lucent\">Retrieve fine grained data</a>");
				} else {
					out.write("Missing target URL information.");
				}
			} catch (JRException jre){
				jre.printStackTrace();
			}
		} else { //report error.
			try {
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Error</title>");
				out.println("</head>");

				out.println("<pre>");
				out.println("Information for related test not found.");
				out.println("</pre>");

				out.println("</body>");
				out.println("</html>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
}
