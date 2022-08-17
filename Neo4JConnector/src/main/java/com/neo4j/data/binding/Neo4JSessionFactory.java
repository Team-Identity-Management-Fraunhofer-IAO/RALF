/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neo4j.data.binding;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

/**
 *
 * @author kurowski
 */
public class Neo4JSessionFactory {
    
	private static final Configuration configuration = new Configuration.Builder()
		    .uri("http://localhost:11020")
		    .credentials("neo4j", "bZ4dIYRhvvfmHuFj2dvW")
		    .build();
	//private static final Configuration configuration = new Configuration.Builder()
	//		.uri("bolt://ralf:ralf@localhost")
	//		.connectionPoolSize(150)
	//		.build();
	

    private final static SessionFactory sessionFactory = new SessionFactory(configuration, "com.neo4j.data.objects.software");
    
    private static Neo4JSessionFactory factory = new Neo4JSessionFactory();

    public static Neo4JSessionFactory getInstance() {
        return factory;
    }

    // prevent external instantiation
    private Neo4JSessionFactory() {
    }

    public Session getNeo4jSession() {
        return sessionFactory.openSession();
    }
    
}
