package com.sql.hibernate;

import org.hibernate.Session;

public class DatabaseConnectionTest {

	public static void main(String[] args) {
		Session session = HibernateInstance.getSessionFactory().openSession();
		session.close();

	}

}
