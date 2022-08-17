package de.ralf.threatmasterkitchen;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.sql.hibernate.HibernateInstance;
import com.sql.hibernate.HibernateReportInstance;
import com.sql.hibernate.HibernateThreatMasterInstance;
import com.sql.hibernate.HibernateThreatMasterLoaderInstance;
import com.sql.hibernate.HibernateTransformationInstance;
import com.sql.hibernate.HibernateUserInstance;

public class MainWebAppInitializer implements WebApplicationInitializer{
	
	public static String context = "";

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		servletContext.addListener(new SessionListener());
		root.scan("de.ralf.threatmasterkitchen");
		servletContext.addListener(new ContextLoaderListener(root));		
		
		ServletRegistration.Dynamic appServlet = servletContext.addServlet("threatMasterKitchen", new DispatcherServlet(new GenericWebApplicationContext()));
		appServlet.setLoadOnStartup(1);
		appServlet.addMapping("/");
		
		servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
        .addMappingForUrlPatterns(null, false, "/*");
		
		/* Multipart Configuration */
		
		MultipartConfigElement multipartConfig = new MultipartConfigElement("/");
		appServlet.setMultipartConfig(multipartConfig);
		
		FilterRegistration.Dynamic multipartFilter = servletContext.addFilter("multipartFilter", MultipartFilter.class);
		multipartFilter.addMappingForUrlPatterns(null, true, "/*");
		
		/* End of Multipart Configuration */
		
		//Properties dwhProperties = new Properties();
		//Properties reportProperties = new Properties();
		Properties userProperties = new Properties();
		//Properties transformProperties = new Properties();
		Properties deploymentProperties = new Properties();
		Properties threatMasterProperties = new Properties();
		Properties threatMasterDWHProperties = new Properties();
		
		try {
			//dwhProperties.load(new FileInputStream(new ClassPathResource("hibernate-dwh.properties").getFile()));
			//reportProperties.load(new FileInputStream(new ClassPathResource("hibernate-report.properties").getFile()));
			userProperties.load(new FileInputStream(new ClassPathResource("hibernate-user.properties").getFile()));
			//transformProperties.load(new FileInputStream(new ClassPathResource("hibernate-transform.properties").getFile()));			
			//HibernateInstance.setProperties(dwhProperties);
			//HibernateReportInstance.setProperties(reportProperties);
			HibernateUserInstance.setProperties(userProperties);
			//HibernateTransformationInstance.setProperties(transformProperties);
			threatMasterProperties.load(new FileInputStream(new ClassPathResource("hibernate-threatMaster.properties").getFile()));
			HibernateThreatMasterInstance.setProperties(threatMasterProperties);
			deploymentProperties.load(new FileInputStream(new ClassPathResource("ralf.properties").getFile()));
			MainWebAppInitializer.context = (String) deploymentProperties.get("deployment.context");
			threatMasterDWHProperties.load(new FileInputStream(new ClassPathResource("hibernate-threatMaster-DWH.properties").getFile()));
			HibernateThreatMasterLoaderInstance.setProperties(threatMasterDWHProperties);
			deploymentProperties = null;
			threatMasterProperties = null;
			userProperties = null;
			threatMasterDWHProperties = null;
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}
