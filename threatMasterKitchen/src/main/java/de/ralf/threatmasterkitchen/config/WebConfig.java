package de.ralf.threatmasterkitchen.config;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@EnableWebMvc
@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan("cvmDemo.controller")
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private Environment env;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login.html");
		registry.addViewController("/index.html");

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("styles/**").addResourceLocations("/WEB-INF/view/styles/");
		registry.addResourceHandler("img/**").addResourceLocations("/WEB-INF/view/img/");
		registry.addResourceHandler("img_auth/**").addResourceLocations("/WEB-INF/view/img_auth/");
		registry.addResourceHandler("js_auth/**").addResourceLocations("/WEB-INF/view/js_auth/");
		registry.addResourceHandler("styles_auth/**").addResourceLocations("/WEB-INF/view/styles_auth/");		
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();

		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/view/");
		bean.setSuffix(".jsp");

		return bean;
	}
	
	@Bean
	public DataSource getDataSource() {
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		List<String> args = runtimeMxBean.getInputArguments();
		String key = " ";
		for (String arg : args) {
			if (arg.contains("-Dralf.spring.users")) {
				key = arg.substring(20, arg.length());
			}
		}
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(key);
		encryptor.setAlgorithm("PBEWITHHMACSHA256ANDAES_256");
		encryptor.setIvGenerator(new RandomIvGenerator());			
		
		Properties props = new Properties();
		props.setProperty("mysql.admin.password", env.getProperty("mysql.admin.password"));
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("mysql.driver"));
		dataSource.setUrl(env.getProperty("mysql.admin.jdbcUrl"));
		dataSource.setUsername(env.getProperty("mysql.admin.username"));
		dataSource.setPassword(props.getProperty("mysql.admin.password"));
		key = null;
		return dataSource;
	}
	

}
