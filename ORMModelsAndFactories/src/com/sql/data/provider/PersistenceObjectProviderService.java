package com.sql.data.provider;

public interface PersistenceObjectProviderService<T> {
	
	public void persist(T obj);
	public T find(int id);
	public void delete(T obj);

}
