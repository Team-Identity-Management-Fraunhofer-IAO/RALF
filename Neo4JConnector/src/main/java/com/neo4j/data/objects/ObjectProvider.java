package com.neo4j.data.objects;

import org.neo4j.ogm.session.Session;

import com.neo4j.data.binding.Neo4JSessionFactory;
import com.neo4j.data.objects.software.persistence.Component;
import com.neo4j.data.objects.software.persistence.SWStack;

public abstract class ObjectProvider<T> implements ObjectProviderService<T> {
	private static final int DEPTH_LIST = 0;
	
	private static final int DEPTH_ENTITY = 1;
	
	protected Session session = Neo4JSessionFactory.getInstance().getNeo4jSession();
	
	@Override
	public Iterable<T> findAll(){
		return session.loadAll(getEntityType(), DEPTH_LIST);
	}
	
	@Override
	public T find(Long id) {
		return session.load(getEntityType(), id, DEPTH_ENTITY);
	}
	
	@Override
	public void delete(T object) {
		session.delete(object);
	}
		
	@Override
	public void persist(T entity) {
		session.save(entity, DEPTH_ENTITY);
	} 
	
	abstract Class<T> getEntityType();
	
	@Override
	public void flushSessionCache() {
		session.clear();
	}
	

}
