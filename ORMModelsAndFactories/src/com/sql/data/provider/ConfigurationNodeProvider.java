package com.sql.data.provider;

import com.sql.data.objects.persistence.platforms.ConfigurationNode;

public class ConfigurationNodeProvider extends PersistenceObjectProvider<ConfigurationNode> implements PersistenceObjectProviderService<ConfigurationNode>{
	
	@Override
	public void delete(ConfigurationNode obj) {
		super.delete(obj);
	}
	
	@Override
	public ConfigurationNode find(int id) {
		return super.find(id);
	}
	
	@Override
	public void persist(ConfigurationNode obj) {
		super.persist(obj);
	}
	
	@Override
	public Class<ConfigurationNode> getClassName() {		
		return ConfigurationNode.class;
	}

}
