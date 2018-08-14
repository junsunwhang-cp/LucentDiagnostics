package com.tibco.jaspersoft.cs.lucent.client.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;

import com.tibco.jaspersoft.cs.lucent.client.core.InMemoryDataStore;

/*
 * $Id: ExecGeneratePdf.java 239 2018-01-16 20:15:57Z jwhang $
 */
public class ExecGeneratePdf {

	public static void main(String [] args){
		/*
		System.out.println("starting ExecGeneratePdf.");
		ExecGeneratePdf egp = new ExecGeneratePdf();
		///organizations/organization_1/adhoc/topics/AllAccounts
		long timeElapsed = egp.writePdf("http://localhost:8080/jasperserver-pro","/organizations/organization_1/adhoc/topics/AllAccounts","superuser","superuser",
				"D:\\CsWorkspace\\CsLucent\\testOutputs\\");
		System.out.println("completed test of ExecGeneratePdf.  report required: " + String.valueOf(timeElapsed));
		*/
	}
	
	public long writePdf(String serverPath, String reportUri, String username, String password, 
			String outputPath, String testId){
		long timeElapsed = System.currentTimeMillis();
		try{
			// test only.start.
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
			String currentStartTime = sdf.format(d);
			// test only.end.
			
			HttpClient client = HttpClientBuilder.create().build();
			String requestString = serverPath + "/rest_v2/reports" + reportUri + ".pdf?j_username=" + username + 
					"&j_password=" + password + "&" + InMemoryDataStore.C_LOADTESTID + "=" + testId ;
			
			//jsw.test only.
			System.out.println(currentStartTime + ": " + requestString);
			
			HttpGet request = new HttpGet(requestString);
			HttpResponse response = client.execute(request);
			//in
			InputStream contentStream = response.getEntity().getContent();
			//out
			String pdfFileName = System.currentTimeMillis() + ".pdf";
			System.out.println("outputing report PDF to: " + outputPath + pdfFileName);
			
			File testOut = new File(outputPath + pdfFileName);
			FileOutputStream fos = new FileOutputStream(testOut);
			
			byte[] buffer = new byte[1024];
			int len = contentStream.read(buffer);
			while (len != -1) {
			    fos.write(buffer, 0, len);
			    len = contentStream.read(buffer);
			}
		
			fos.flush();
			fos.close();
			return (System.currentTimeMillis() - timeElapsed);
		} catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}
}




