package com.sql.data.objects.persistence;

public interface PersistentObjectInterface<T> {
	
	public int getIdentifier();
	
	public boolean overwriteWithTransientObject(T transientObject);
	
	public Class<T> getImplementingClass();

}
