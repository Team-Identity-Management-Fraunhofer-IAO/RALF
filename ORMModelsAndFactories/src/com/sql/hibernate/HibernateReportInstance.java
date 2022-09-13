/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql.hibernate;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEEncryptorRegistry;

import com.sql.data.objects.persistence.report.VulnerableComponent;
import com.sql.data.objects.persistence.platforms.CPECore;
import com.sql.data.objects.persistence.report.AggregatedVulnerabilityFact;
import com.sql.data.objects.persistence.report.AttackPattern;
import com.sql.data.objects.persistence.report.CombinationList;
import com.sql.data.objects.persistence.report.Combinations;
import com.sql.data.objects.persistence.report.ComponentVulnerability;
import com.sql.data.objects.persistence.report.NonExplicitCPEList;
import com.sql.data.objects.persistence.report.Report;
import com.sql.data.objects.persistence.report.schedule.AssessmentMetadata;
import com.sql.data.objects.persistence.report.schedule.Schedule;
import com.sql.data.objects.persistence.report.schedule.SchedulerFact;
import com.sql.data.objects.persistence.report.vulnerabilitymanagement.ManagedVulnerability;
import com.sql.data.objects.persistence.report.vulnerabilitymanagement.VulnerabilityManagementKnowledge;
import com.sql.data.objects.persistence.vulnerabilities.CVECore;
import com.sql.data.objects.persistence.vulnerabilities.Description;

/**
 *
 * @author kurowski
 */
public class HibernateReportInstance {

	private static SessionFactory sessionFactory;
	private static Properties properties;

	public static void setProperties(Properties properties) {
		HibernateReportInstance.properties = properties;
	}

	/*
	 * <property name="hibernate.connection.provider_class">com.sql.hibernate.EncryptedPasswordC3P0ConnectionProvider</property>
  	<property name="hibernate.connection.encryptor_registered_name">configurationHibernateClientReportEncryptor</property>
	 * 
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
				List<String> args = runtimeMxBean.getInputArguments();
				String key = "";
				for (String arg : args) {
					if (arg.contains("-Dralf.hibernate.client")) {
						key = arg.substring(24, arg.length());
					}
				}
				StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
				encryptor.setAlgorithm("PBEWITHHMACSHA256ANDAES_256");
				encryptor.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());
				encryptor.setPassword(key);
				HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
				registry.registerPBEStringEncryptor("configurationHibernateClientReportEncryptor", encryptor);
				Configuration cfg = new Configuration();		
				if (properties == null) {
					System.err.println("Configuration is missing!");
					return null;
				}
				cfg.setProperties(properties);
				cfg.configure("/hibernate-report.cfg.xml");
				cfg.addPackage("com.data").addAnnotatedClass(AggregatedVulnerabilityFact.class)
						.addAnnotatedClass(Report.class).addAnnotatedClass(CombinationList.class)
						.addAnnotatedClass(Combinations.class).addAnnotatedClass(CPECore.class)
						.addAnnotatedClass(Description.class).addAnnotatedClass(CVECore.class)
						.addAnnotatedClass(Schedule.class).addAnnotatedClass(SchedulerFact.class)
						.addAnnotatedClass(AttackPattern.class).addAnnotatedClass(ComponentVulnerability.class)
						.addAnnotatedClass(VulnerableComponent.class).addAnnotatedClass(NonExplicitCPEList.class)
						.addAnnotatedClass(AssessmentMetadata.class).addAnnotatedClass(ManagedVulnerability.class)
						.addAnnotatedClass(VulnerabilityManagementKnowledge.class);
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
						.applySettings(cfg.getProperties());
				sessionFactory = cfg.buildSessionFactory(builder.build());
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
		return sessionFactory;
	}

}
