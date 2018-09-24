package com.tibco.jaspersoft.cs.lucent.client.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.thoughtworks.xstream.XStream;

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

/*
 * RetrieveTestDataServlet
 * $Id: RetrieveTestDataServlet.java 288 2018-09-19 23:27:26Z jwhang $
 */
public class RetrieveTestDataServlet extends HttpServlet {

	public String C_TEST_DATA_KEY = "cs_testDataKey";
	public String C_TEST_DATA_SERVER_PATH = "cs_serverPath";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"TestData.gz\";");
		
		Object keyObj = request.getParameter(C_TEST_DATA_KEY);
		Object serverPathObj = request.getParameter(C_TEST_DATA_SERVER_PATH);
		
		if ((keyObj!=null)&&(serverPathObj!=null)){ //return specified test data.
			String dataKey = String.valueOf(keyObj);
			String serverPath = String.valueOf(serverPathObj);
			System.out.println("Retrieve data for key: " + dataKey);
			HttpClient client = HttpClientBuilder.create().build();
			
			//test only.start.
			String requestString = "http://" + serverPath + "/rest_v2/lucentService/" + 
			"getInfo?lucent_test_id=" + dataKey + "&j_username=superuser&j_password=superuser";
			//test only.end.
			
			HttpGet requestSocket = new HttpGet(requestString);
			HttpResponse responseSocket = client.execute(requestSocket);
			//in
			InputStream contentStream = responseSocket.getEntity().getContent();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			byte[] buffer = new byte[1024];
			int len = contentStream.read(buffer);
			while (len != -1) {
			    baos.write(buffer, 0, len);
			    len = contentStream.read(buffer);
			}
			baos.flush();
			baos.close();
			
			String outXml = baos.toString();
			//jsw.simple test.start		
//			XStream xs = new XStream();
//			ArrayList<String> testList = new ArrayList<String>();
//			testList.add("first entry");
//			testList.add("second entry");
//			String sampleXml = xs.toXML(testList);
			//jsw.simple test end.
			
			
			//check to see if the test data has already been retrieved.
			
			//if test data has not been retrieved, pull using web services from jasper server.
			
			//flatten data and send as a zipped XML file.
			ServletOutputStream outputStream = response.getOutputStream();
			try {
	        	ByteArrayInputStream bais = new ByteArrayInputStream(outXml.getBytes(StandardCharsets.UTF_8));
	            GZIPOutputStream gzipOS = new GZIPOutputStream(outputStream);
	            byte[] zipBuffer = new byte[1024];
	            int zipLen;
	            while((zipLen=bais.read(zipBuffer)) != -1){
	                gzipOS.write(zipBuffer, 0, zipLen);
	            }
	            //close resources
	            gzipOS.close();
	            bais.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			outputStream.flush();
			outputStream.close();
		}
		
	}
}





