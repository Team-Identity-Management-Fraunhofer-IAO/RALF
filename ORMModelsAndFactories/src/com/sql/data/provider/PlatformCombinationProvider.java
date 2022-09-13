package com.sql.data.provider;

import com.sql.data.objects.persistence.platforms.ConfigurationNode;
import com.sql.data.objects.persistence.platforms.PlatformCombination;

public class PlatformCombinationProvider extends PersistenceObjectProvider<PlatformCombination>{

	@Override
	public void delete(PlatformCombination obj) {
		super.delete(obj);
	}
	
	@Override
	public PlatformCombination find(int id) {
		return super.find(id);
	}
	
	@Override
	public void persist(PlatformCombination obj) {
		super.persist(obj);
	}
	
	@Override
	public Class<PlatformCombination> getClassName() {		
		return PlatformCombination.class;
	}
}
