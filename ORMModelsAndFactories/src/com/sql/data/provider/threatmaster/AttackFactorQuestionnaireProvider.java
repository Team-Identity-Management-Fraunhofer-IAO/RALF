package com.sql.data.provider.threatmaster;

import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestionnaireContent;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorWeights;
import com.sql.data.objects.persistence.threatmaster.riskassessment.AttackMotivatingQuestionResponse;
import com.sql.data.objects.persistence.threatmaster.riskassessment.VulnerabilityEnablingQuestionResponse;
import com.sql.data.objects.persistence.threatmaster.riskassessment.VulnerabilityEnablingQuestionnaireResponse;
import com.sql.data.objects.persistence.threatmaster.risks.AttackMotivatingQuestionnaireResponse;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorQuestion;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorWeights;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingQuestionnaire;
import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingQuestionnaireContent;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestion;

public class AttackFactorQuestionnaireProvider extends PersistenceObjectProvider<AttackMotivatingQuestionnaire>
		implements PersistenceObjectProviderService<AttackMotivatingQuestionnaire> {

	@Override
	public void persist(AttackMotivatingQuestionnaire obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.persist(obj);
	}

	public void persist(AttackMotivatingQuestionnaireContent obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public void persist(VulnerabilityEnablingFactorQuestionnaire obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public void persist(VulnerabilityEnablingFactorQuestionnaireContent obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public void persist(AttackMotivatingFactorWeights obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public void persist(VulnerabilityEnablingFactorWeights obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public void persist(VulnerabilityEnablingQuestionnaireResponse obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public void persist(VulnerabilityEnablingQuestionResponse obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public void persist(AttackMotivatingQuestionnaireResponse obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public void persist(AttackMotivatingQuestionResponse obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(obj);
		tx.commit();
		session.close();
	}

	public AttackMotivatingQuestionnaire getLastAttackMotivatingQuestionnaire(int organization_id, int service_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM attack_motivating_questionnaire WHERE organization_id=:organization_id AND service_id=:service_id ORDER BY loadTimestamp DESC LIMIT 1";
		NativeQuery<AttackMotivatingQuestionnaire> query = session.createNativeQuery(sql,
				AttackMotivatingQuestionnaire.class);
		query.setParameter("organization_id", organization_id);
		query.setParameter("service_id", service_id);
		AttackMotivatingQuestionnaire result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {
			sql = "SELECT * FROM attack_motivating_questionnaire WHERE organization_id=:organization_id AND service_id=-1 ORDER BY loadTimestamp DESC LIMIT 1";
			query = session.createNativeQuery(sql,
					AttackMotivatingQuestionnaire.class);
			query.setParameter("organization_id", organization_id);
			try {
				result = query.getSingleResult();
			} catch (NoResultException ex2) {
				
			}
		}
		session.close();
		return result;
	}

	public VulnerabilityEnablingFactorQuestionnaire getLastVulnerabilityEnablingQuestionnaire(int organization_id,
			int service_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM vulnerability_enabling_questionnaire WHERE organization_id=:organization_id AND service_id=:service_id ORDER BY loadTimestamp DESC LIMIT 1";
		NativeQuery<VulnerabilityEnablingFactorQuestionnaire> query = session.createNativeQuery(sql,
				VulnerabilityEnablingFactorQuestionnaire.class);
		query.setParameter("organization_id", organization_id);
		query.setParameter("service_id", service_id);
		VulnerabilityEnablingFactorQuestionnaire result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {
			sql = "SELECT * FROM vulnerability_enabling_questionnaire WHERE organization_id=:organization_id AND service_id=-1 ORDER BY loadTimestamp DESC LIMIT 1";
			query = session.createNativeQuery(sql,
					VulnerabilityEnablingFactorQuestionnaire.class);
			query.setParameter("organization_id", organization_id);
			try {
				result = query.getSingleResult();
			} catch (NoResultException ex2) {
				
			}
		}
		session.close();
		return result;
	}

	public AttackMotivatingFactorWeights getAttackMotivatingFactorWeight(int attack_motivating_questionniare_id,
			int factor_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM attack_motivating_factor_weights WHERE attack_motivating_questionnaire_id=:attack_motivating_questionnaire_id AND factor_id=:factor_id";
		NativeQuery<AttackMotivatingFactorWeights> query = session.createNativeQuery(sql,
				AttackMotivatingFactorWeights.class);
		query.setParameter("attack_motivating_questionnaire_id", attack_motivating_questionniare_id);
		query.setParameter("factor_id", factor_id);
		AttackMotivatingFactorWeights result = null;
		try {
			result = query.getSingleResult();
		}catch (NoResultException ex) {
			
		}
		session.close();
		return result;
	}

	public VulnerabilityEnablingFactorWeights getVulnerabilityEnablingFactorWeight(
			int vulnerability_enabling_questionnaire_id, int factor_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM vulnerability_enabling_factor_weights WHERE vulnerability_enabling_factor_questionnaire_id=:vulnerability_enabling_factor_questionnaire_id AND factor_id=:factor_id";
		NativeQuery<VulnerabilityEnablingFactorWeights> query = session.createNativeQuery(sql,
				VulnerabilityEnablingFactorWeights.class);
		query.setParameter("vulnerability_enabling_factor_questionnaire_id", vulnerability_enabling_questionnaire_id);
		query.setParameter("factor_id", factor_id);
		VulnerabilityEnablingFactorWeights result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {
			
		}
		session.close();
		return result;
	}

	public AttackMotivatingQuestionnaireResponse getLastAttackMotivatingQuestionnaireResponse(int organization_id,
			int service_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM attack_motivating_questionnaire_response WHERE organization_id=:organization_id AND service_id=:service_id ORDER BY loadTimestamp DESC LIMIT 1";
		NativeQuery<AttackMotivatingQuestionnaireResponse> query = session.createNativeQuery(sql,
				AttackMotivatingQuestionnaireResponse.class);
		query.setParameter("service_id", service_id);
		query.setParameter("organization_id", organization_id);
		AttackMotivatingQuestionnaireResponse result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		}
		session.close();
		return result;
	}

	public List<AttackMotivatingQuestionResponse> getResponsesForAttackMotivatingQuestionnaireResponse(
			int attack_motivating_questionnaire_response_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM attack_motivating_question_response WHERE attack_motivating_questionnaire_response_id = :attack_motivating_questionnaire_response_id";
		NativeQuery<AttackMotivatingQuestionResponse> query = session.createNativeQuery(sql,
				AttackMotivatingQuestionResponse.class);
		query.setParameter("attack_motivating_questionnaire_response_id", attack_motivating_questionnaire_response_id);
		List<AttackMotivatingQuestionResponse> responses = query.list();
		session.close();
		return responses;
	}

	public VulnerabilityEnablingQuestionnaireResponse getLastVulnerabilityEnablingQuestionnaireResponse(
			int organization_id, int service_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM vulnerability_enabling_questionnaire_response WHERE service_id=:service_id AND organization_id=:organization_id ORDER BY loadTimestamp DESC LIMIT 1";
		NativeQuery<VulnerabilityEnablingQuestionnaireResponse> query = session.createNativeQuery(sql,
				VulnerabilityEnablingQuestionnaireResponse.class);
		query.setParameter("service_id", service_id);
		query.setParameter("organization_id", organization_id);
		VulnerabilityEnablingQuestionnaireResponse response = null;
		try {
			response = query.getSingleResult();
		} catch (NoResultException ex) {

		}
		session.close();
		return response;
	}

	public List<VulnerabilityEnablingQuestionResponse> getResponsesForVulnerabilityEnablingQuestionnaireResponse(
			int vulnerability_enabling_questionnaire_response_id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM vulnerability_enabling_question_response WHERE vulnerability_enabling_questionnaire_response_id = :vulnerability_enabling_questionnaire_response_id";
		NativeQuery<VulnerabilityEnablingQuestionResponse> query = session.createNativeQuery(sql,
				VulnerabilityEnablingQuestionResponse.class);
		query.setParameter("vulnerability_enabling_questionnaire_response_id",
				vulnerability_enabling_questionnaire_response_id);
		List<VulnerabilityEnablingQuestionResponse> responses = query.list();
		session.close();
		return responses;
	}
	
	public List<Integer> getAttackMotivatingFactorIDsForQuestionnaireResponse(int attack_motivating_questionnaire_response_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT factor_id FROM attack_motivating_question_response WHERE response AND attack_motivating_questionnaire_response_id=:attack_motivating_questionnaire_response_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("attack_motivating_questionnaire_response_id", attack_motivating_questionnaire_response_id);
		List<Integer> factor_ids = query.list();
		session.close();
		return factor_ids;
	}
	
	public List<Integer> getVulnerabilityEnablingFactorIDsForQuestionnaireResponse(int vulnerability_enabling_questionnaire_response_id){
		super.instantiateHibernateThreatMasterSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT factor_id FROM vulnerability_enabling_question_response WHERE response AND vulnerability_enabling_questionnaire_response_id=:vulnerability_enabling_questionnaire_response_id";
		NativeQuery<Integer> query = session.createNativeQuery(sql);
		query.setParameter("vulnerability_enabling_questionnaire_response_id", vulnerability_enabling_questionnaire_response_id);
		List<Integer> factor_ids = query.list();
		session.close();
		return factor_ids;
	}

	@Override
	public AttackMotivatingQuestionnaire find(int id) {
		super.instantiateHibernateThreatMasterSessionFactory();
		return super.find(id);
	}

	@Override
	public void delete(AttackMotivatingQuestionnaire obj) {
		super.instantiateHibernateThreatMasterSessionFactory();
		super.delete(obj);
	}

	@Override
	public Class<AttackMotivatingQuestionnaire> getClassName() {
		return AttackMotivatingQuestionnaire.class;
	}

}
