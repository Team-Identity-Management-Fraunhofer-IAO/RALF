package com.sql.data.provider;

import java.util.List;

import org.hibernate.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.authentication.Authority;
import com.sql.data.objects.persistence.authentication.Group;
import com.sql.data.objects.persistence.authentication.User;
import com.sql.data.objects.persistence.authentication.UserGroupAssociation;
import com.sql.hibernate.HibernateUserInstance;

public class UserGroupAuthorityProvider {
	private SessionFactory sessionFactory;
	
	private void instantiateSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateUserInstance.getSessionFactory();
		}
	}
	
	public void persistUser(User user) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
		session.close();
	}
	
	public void updateUser(User user) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(user);
		tx.commit();
		session.close();
	}
	
	public void deleteUser(User user) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(user);
		tx.commit();
		session.close();
	}
	
	public User getUser(String username) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		User user = session.find(User.class, username);
		session.close();
		return user;
	}
	
	public void persistUserGroupAssociation(UserGroupAssociation assoc) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(assoc);
		tx.commit();
		session.close();
	}
	
	public List<User> getAllUsers(){
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "SELECT * FROM users ORDER BY username";
		NativeQuery<User> query = session.createNativeQuery(sql, User.class);
		List<User> result = query.getResultList();
		session.close();
		return result.size()>0?result:null;
	}
	
	public List<UserGroupAssociation> getUserGroupAssociationsForUser(String username){
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "SELECT * FROM user_group_association WHERE username = :username";
		NativeQuery<UserGroupAssociation> query = session.createNativeQuery(sql, UserGroupAssociation.class);
		query.setParameter("username", username);
		List<UserGroupAssociation> result = query.getResultList();
		session.close();
		return result.size()>0?result:null;
	}
	
	public List<UserGroupAssociation> getUserGroupAssociationsForUserGroup(String groupname){
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "SELECT * FROM user_group_association WHERE groupname = :groupname";
		NativeQuery<UserGroupAssociation> query = session.createNativeQuery(sql, UserGroupAssociation.class);
		query.setParameter("groupname", groupname);
		List<UserGroupAssociation> result = query.getResultList();
		session.close();
		return result.size()>0?result:null;
	}
	
	public UserGroupAssociation getUserGroupAssociationForUserAndGroup(String username, String groupname) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "SELECT * FROM user_group_association WHERE groupname = :groupname AND username = :username";
		NativeQuery<UserGroupAssociation> query = session.createNativeQuery(sql, UserGroupAssociation.class);
		query.setParameter("groupname", groupname);
		query.setParameter("username", username);
		UserGroupAssociation result = query.getSingleResult();
		session.close();
		return result;
	}
	
	public void deleteUserGroupAssociation(UserGroupAssociation association) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(association);
		tx.commit();
		session.close();
	}
	
	public void peristAuthorityAssignment(Authority authority) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(authority);
		tx.commit();
		session.close();
	}
	
	public void deleteAuthority(Authority authority) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		session.delete(authority);
		session.close();
	}
	
	public Authority getGroupAuthorityForGroupname(String groupname) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Authority authority = session.find(Authority.class, groupname);
		session.close();
		return authority;
	}
	
	public List<Authority> getAllGroupAuthorities(){
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "SELECT * FROM authorities ORDER BY groupname";
		NativeQuery<Authority> query = session.createNativeQuery(sql, Authority.class);
		List<Authority> result = query.getResultList();
		session.close();
		return result.size()>0?result:null;
	}
	
	public void addNewGroupWithAuthority(Authority authority) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Group group = new Group();
		group.setGroupname(authority.getGroupname());
		Transaction tx = session.beginTransaction();
		session.save(group);
		session.save(authority);
		tx.commit();
		session.close();
	}
	
	public void deleteGroup(Group group) {
		Authority authority = getGroupAuthorityForGroupname(group.getGroupname());
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(authority);
		session.delete(group);
		tx.commit();
		session.close();
	}
	
	public Group getGroupByGroupname(String groupname) {
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		Group result = session.find(Group.class, groupname);
		session.close();
		return result;
	}
	
	public List<Group> getAllGroups(){
		instantiateSessionFactory();
		Session session = sessionFactory.openSession();
		String sql = "SELECT * FROM ralf_groups ORDER BY groupname";
		NativeQuery<Group> query = session.createNativeQuery(sql, Group.class);
		List<Group> result = query.getResultList();
		session.close();
		return result;
	}


}
