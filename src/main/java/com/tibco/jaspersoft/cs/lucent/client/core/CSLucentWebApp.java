package com.tibco.jaspersoft.cs.lucent.client.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/*
 * $Id: CSLucentWebApp.java 261 2018-04-20 17:15:46Z jwhang $
 */

@SpringBootApplication
@Configuration
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
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new ViewReportServlet(),"/viewreport/*");
		bean.setName("reportViewBean");
		bean.setLoadOnStartup(1);
		return bean;
	}
	
	@Bean
	public ServletRegistrationBean imageServletRegistrationBean(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new ImageServlet(),"/image");
		bean.setName("imageServletBean");
		bean.setLoadOnStartup(1);
		return bean;
	}
	
	@Bean
	public ServletRegistrationBean testDataServletRegistrationBean(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new RetrieveTestDataServlet(),"/retrieveTestData/*");
		bean.setName("retrieveTestDataBean");
		bean.setLoadOnStartup(1);
		return bean;
	}
}
