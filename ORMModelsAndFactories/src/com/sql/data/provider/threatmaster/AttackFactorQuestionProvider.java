package com.sql.data.provider.threatmaster;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.threatmaster.attackfactors.AttackMotivatingFactorQuestion;
import com.sql.data.objects.persistence.threatmaster.attackfactors.VulnerabilityEnablingFactorQuestion;
import com.sql.data.provider.PersistenceObjectProvider;
import com.sql.data.provider.PersistenceObjectProviderService;

public class AttackFactorQuestionProvider extends PersistenceObjectProvider<AttackMotivatingFactorQuestion> implements PersistenceObjectProviderService<AttackMotivatingFactorQuestion>{

	public List<AttackMotivatingFactorQuestion> getAttackMotivatingFactorQuestions(){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM c_attack_motivating_factor_question";
		NativeQuery<AttackMotivatingFactorQuestion> query = session.createNativeQuery(sql, AttackMotivatingFactorQuestion.class);
		List<AttackMotivatingFactorQuestion> result = query.list();
		session.close();
		return result;
	}
	
	public List<VulnerabilityEnablingFactorQuestion> getVulnerabilityEnablingFactorQuestions(){
		super.instantiateHibernateThreatMasterLoaderSessionFactory();
		Session session = super.getHibernateSessionFactory().openSession();
		String sql = "SELECT * FROM c_vulnerability_enabling_factor_question";
		NativeQuery<VulnerabilityEnablingFactorQuestion> query = session.createNativeQuery(sql, VulnerabilityEnablingFactorQuestion.class);
		List<VulnerabilityEnablingFactorQuestion> result = query.list();
		session.close();
		return result;
	}

	@Override
	public Class<AttackMotivatingFactorQuestion> getClassName() {
		return AttackMotivatingFactorQuestion.class;
	}
}
