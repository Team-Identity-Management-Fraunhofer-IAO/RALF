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

import com.sql.data.objects.persistence.threatmaster.assessment.ControlReport;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControl;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportControlExcludedThreats;
import com.sql.data.objects.persistence.threatmaster.assessment.ControlReportPlatform;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorWeights;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingQuestionnaireContent;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestionnaireContent;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorWeights;
import com.sql.data.objects.persistence.threatmaster.organizations.Organization;
import com.sql.data.objects.persistence.threatmaster.organizations.RiskCategory;
import com.sql.data.objects.persistence.threatmaster.organizations.RiskCategoryBundle;
import com.sql.data.objects.persistence.threatmaster.organizations.RiskPhi;
import com.sql.data.objects.persistence.threatmaster.riskassessment.AttackMotivatingQuestionResponse;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReport;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportCapabilityKillerSuccessProbability;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreat;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatControl;
import com.sql.data.objects.persistence.threatmaster.riskassessment.RiskReportThreatControlPivotedThreat;
import com.sql.data.objects.persistence.threatmaster.riskassessment.VulnerabilityEnablingQuestionResponse;
import com.sql.data.objects.persistence.threatmaster.riskassessment.VulnerabilityEnablingQuestionnaireResponse;
import com.sql.data.objects.persistence.threatmaster.risks.AttackMotivatingQuestionnaireResponse;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRisk;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCapabilityKiller;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrder;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskCustomOrderList;
import com.sql.data.objects.persistence.threatmaster.risks.BusinessRiskPairwiseComparison;
import com.sql.data.objects.persistence.threatmaster.risks.CapabilityKiller;
import com.sql.data.objects.persistence.threatmaster.services.Service;
import com.sql.data.objects.persistence.threatmaster.services.ServiceBusinessRisk;
import com.sql.data.objects.persistence.threatmaster.services.ServiceExistenceProbability;



/**
 *
 * @author kurowski
 */
public class HibernateThreatMasterInstance {

	private static SessionFactory sessionFactory;
	private static Properties properties;

	public static void setProperties(Properties properties) {
		HibernateThreatMasterInstance.properties = properties;
	}

	/*
	 * <property name="hibernate.connection.provider_class">com.sql.hibernate.EncryptedPasswordC3P0ConnectionProvider</property>
  	<property name="hibernate.connection.encryptor_registered_name">configurationHibernateClientReportEncryptor</property>
	 * 
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			System.out.println("Initiializing Threat Master Instance!");
			try {
				RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
				List<String> args = runtimeMxBean.getInputArguments();
				String key = " ";
				for (String arg : args) {
					if (arg.contains("-Dralf.hibernate.threatmaster.client")) {
						key = arg.substring(37, arg.length());
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
				cfg.configure("/hibernate-threatmaster.cfg.xml");
				cfg.addPackage("com.sql.data.objects.persistence.threatmaster").
				addAnnotatedClass(Organization.class).
				addAnnotatedClass(Service.class).
				addAnnotatedClass(ServiceExistenceProbability.class).
				addAnnotatedClass(BusinessRisk.class).
				addAnnotatedClass(CapabilityKiller.class).
				addAnnotatedClass(BusinessRiskCapabilityKiller.class).
				addAnnotatedClass(ServiceBusinessRisk.class).
				addAnnotatedClass(BusinessRiskCustomOrder.class).
				addAnnotatedClass(BusinessRiskCustomOrderList.class).
				addAnnotatedClass(BusinessRiskPairwiseComparison.class).
				addAnnotatedClass(ControlReport.class).
				addAnnotatedClass(ControlReportControl.class).
				addAnnotatedClass(ControlReportControlExcludedThreats.class).
				addAnnotatedClass(RiskReport.class).
				addAnnotatedClass(RiskReportThreatControl.class).
				addAnnotatedClass(RiskReportThreat.class).
				addAnnotatedClass(RiskReportThreatControlPivotedThreat.class).
				addAnnotatedClass(AttackMotivatingQuestionnaire.class).
				addAnnotatedClass(AttackMotivatingQuestionnaireContent.class).
				addAnnotatedClass(VulnerabilityEnablingFactorQuestionnaire.class).
				addAnnotatedClass(VulnerabilityEnablingFactorQuestionnaireContent.class).
				addAnnotatedClass(AttackMotivatingFactorWeights.class).
				addAnnotatedClass(VulnerabilityEnablingFactorWeights.class).
				addAnnotatedClass(VulnerabilityEnablingQuestionnaireResponse.class).
				addAnnotatedClass(AttackMotivatingQuestionnaireResponse.class).
				addAnnotatedClass(VulnerabilityEnablingQuestionResponse.class).
				addAnnotatedClass(AttackMotivatingQuestionResponse.class).
				addAnnotatedClass(RiskReportCapabilityKillerSuccessProbability.class).
				addAnnotatedClass(RiskCategory.class).
				addAnnotatedClass(RiskPhi.class).
				addAnnotatedClass(RiskCategoryBundle.class).
				addAnnotatedClass(ControlReportPlatform.class);
				
				
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
						.applySettings(cfg.getProperties());
				sessionFactory = cfg.buildSessionFactory(builder.build());
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return sessionFactory;
	}

}
