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

import com.sql.data.objects.persistence.attackpatterns.AttackPatternRelationship;
import com.sql.data.objects.persistence.attackpatterns.CAPECATTACKRelationship;
import com.sql.data.objects.persistence.attackpatterns.CAPECCWERelationship;
import com.sql.data.objects.persistence.attackpatterns.CAPECCore;
import com.sql.data.objects.persistence.facts.VulnerablePlatformsFact;
import com.sql.data.objects.persistence.metadata.Metadata;
import com.sql.data.objects.persistence.platforms.CPECore;
import com.sql.data.objects.persistence.platforms.ConfigurationNode;
import com.sql.data.objects.persistence.platforms.ContainsPlatform;
import com.sql.data.objects.persistence.platforms.PlatformCombination;
import com.sql.data.objects.persistence.vulnerabilites.references.Reference;
import com.sql.data.objects.persistence.vulnerabilites.references.ReferenceTag;
import com.sql.data.objects.persistence.vulnerabilities.CVECore;
import com.sql.data.objects.persistence.vulnerabilities.Description;
import com.sql.data.objects.persistence.weaknesses.CWECore;
import com.sql.data.objects.persistence.weaknesses.Problemtype;

/**
 *
 * @author kurowski
 */

/*
 * @ToDo: Remove Password
 */
public class HibernateStagingInstance {

	private static SessionFactory sessionFactory;
	private static Properties properties;
	
	public static void setProperties(Properties properties) {
		HibernateStagingInstance.properties = properties;
	}


	public static SessionFactory getSessionFactory () {
		if (sessionFactory == null) {
			try {				
				/*RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
				List<String> args = runtimeMxBean.getInputArguments();
				String key = "";
				for (String arg : args) {
					if (arg.contains("-Dralf.hibernate.dwh")) {
						key = arg.substring(21, arg.length());
					}
				}
				StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
				encryptor.setAlgorithm("PBEWITHHMACSHA256ANDAES_256");
				encryptor.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());
				encryptor.setPassword(key);
				HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();			
				registry.registerPBEStringEncryptor("configurationHibernateEncryptor", encryptor);
				*/Configuration cfg = new Configuration();
				if (properties == null) {
					System.err.println("Configuration is missing!");
					return null;
				}
				cfg.setProperties(properties);
				cfg.configure("/hibernate-staging.cfg.xml");
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
						.applySettings(cfg.getProperties());
				sessionFactory = cfg.buildSessionFactory(builder.build());
			} catch (RuntimeException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return sessionFactory;
	}

}
