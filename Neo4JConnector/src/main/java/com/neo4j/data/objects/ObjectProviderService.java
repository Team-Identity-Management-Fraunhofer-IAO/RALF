package com.neo4j.data.objects;

public interface ObjectProviderService<T> {
	Iterable<T> findAll();
	T find (Long id);
	void delete(T object);
	void persist(T object);
	void flushSessionCache();
}
