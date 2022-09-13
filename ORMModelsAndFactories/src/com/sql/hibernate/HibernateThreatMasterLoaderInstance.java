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

import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactor;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorQuestion;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestion;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternPlatform;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternPrerequisite;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.AttackPatternSuccessProbability;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.CourseOfActionAttackPattern;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.IntrusionSetAttackPattern;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.MalwareAttackPattern;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.ThreatCollection;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.VulnerabilityEnablingFactor;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.XMitrePlatform;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.IntrusionSet;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.Malware;
import com.sql.data.objects.persistence.threatmaster.datawarehouse.singleobjects.Reference;
import com.sql.data.objects.persistence.threatmaster.organizations.Organization;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCapabilityKiller;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrder;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrderList;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskPairwiseComparison;
import com.sql.data.objects.persistence.threatmaster.risks.CapabilityKiller;
import com.sql.data.objects.persistence.threatmaster.risks.SuccessProbability;
import com.sql.data.objects.persistence.threatmaster.services.Service;
import com.sql.data.objects.persistence.threatmaster.services.ServiceBusinessRisk;



/**
 *
 * @author kurowski
 */
public class HibernateThreatMasterLoaderInstance {

	private static SessionFactory sessionFactory;
	private static Properties properties;

	public static void setProperties(Properties properties) {
		HibernateThreatMasterLoaderInstance.properties = properties;
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
				String key = " ";
				for (String arg : args) {
					if (arg.contains("-Dralf.hibernate.threatmaster.loader.client")) {
						key = arg.substring(44, arg.length());
					}
				}
				StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
				encryptor.setAlgorithm("PBEWITHHMACSHA256ANDAES_256");
				encryptor.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());
				encryptor.setPassword(key);
				HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
				registry.registerPBEStringEncryptor("configurationHibernateThreatMasterEncryptor", encryptor);
				Configuration cfg = new Configuration();		
				if (properties == null) {
					System.err.println("Configuration is missing!");
					return null;
				}
				cfg.setProperties(properties);
				cfg.configure("/hibernate-threatmaster-loader.cfg.xml");
				cfg.addPackage("com.sql.data.objects.persistence.threatmaster.datawarehouse")
				.addAnnotatedClass(AttackPatternPlatform.class)
				.addAnnotatedClass(CourseOfActionAttackPattern.class)
				.addAnnotatedClass(IntrusionSetAttackPattern.class)
				.addAnnotatedClass(MalwareAttackPattern.class)
				.addAnnotatedClass(AttackPatternSuccessProbability.class)
				.addAnnotatedClass(VulnerabilityEnablingFactor.class)
				.addAnnotatedClass(ThreatCollection.class)
				.addAnnotatedClass(AttackPatternPrerequisite.class)
				.addAnnotatedClass(AttackMotivatingFactor.class)
				.addAnnotatedClass(AttackMotivatingFactorQuestion.class)
				.addAnnotatedClass(VulnerabilityEnablingFactor.class)
				.addAnnotatedClass(VulnerabilityEnablingFactorQuestion.class)
				.addAnnotatedClass(XMitrePlatform.class)
				.addAnnotatedClass(SuccessProbability.class)
				.addAnnotatedClass(IntrusionSet.class)
				.addAnnotatedClass(Reference.class)
				.addAnnotatedClass(Malware.class);
				
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
