package com.tibco.jaspersoft.cs.lucent.client.core;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.thoughtworks.xstream.XStream;

public class TestGzip {

	public static void main(String[] args) {
		TestGzip tg = new TestGzip();
		tg.testOutputZip();

	}
	
	public void testOutputZip(){
		String testString = null;
		XStream xs = new XStream();
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("first entry");
		testList.add("second entry");
		
		String sampleXml = xs.toXML(testList);
		System.out.println(sampleXml);
		String gzipFile = "D:\\CsWorkspace\\CsLucent\\testOutputs\\TestData.zip"; 
		
        try {
            //FileInputStream fis = new FileInputStream(file);
        	ByteArrayInputStream bais = new ByteArrayInputStream(sampleXml.getBytes(StandardCharsets.UTF_8));
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=bais.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		
	}

}
