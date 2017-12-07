package com.tibco.jaspersoft.cs.lucent.client.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/*
 * $Id: CSLucentWebApp.java 222 2017-11-01 21:14:04Z jwhang $
 */

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.tibco.jaspersoft.cs.lucent.client")
public class CSLucentWebApp extends SpringBootServletInitializer {

	public static void main(String [] args){
		//<start-class>com.tibco.jaspersoft.cs.lucent.client.core.CSLucentWebApp</start-class>
		SpringApplication.run(CSLucentWebApp.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CSLucentWebApp.class);
	}
}
