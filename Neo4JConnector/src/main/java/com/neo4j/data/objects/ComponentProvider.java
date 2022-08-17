package com.neo4j.data.objects;

import java.util.HashMap;
import java.util.Map;

import com.neo4j.data.objects.software.persistence.Component;
import com.neo4j.data.objects.software.persistence.Group;

public class ComponentProvider extends ObjectProvider<Component> {
	private static final int DEPTH_LIST = 1;
	private static final int DEPTH_ENTITY = 1;

	public ComponentProvider() {
		super();
	}

	@Override
	public Iterable<Component> findAll() {
		return super.session.loadAll(Component.class, DEPTH_LIST);
	}
	
	public Iterable<Component> findAllForSWStack(long SWStackId){
		String cypher = "MATCH (a:SWStack)-[r]->(m:SWComponent)	WHERE id(a)=$SWStackId OPTIONAL MATCH (m)-[x]->(n:SWComponent)	RETURN m,x,n ORDER BY n.section";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SWStackId", Long.valueOf(SWStackId));
		return session.query(Component.class, cypher, parameters);		
	}
	
	public Iterable<Group> getGroupsForComponentId(Long id){
		String cypher = "MATCH (a:Group)-[r:contains]->(s:SWStack)-[l:includes]->(c:SWComponent) WHERE id(c)=$id RETURN a";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		return session.query(Group.class, cypher, parameters);	
	}

	@Override
	public Component find(Long id) {
		return super.session.load(Component.class, id, DEPTH_ENTITY);
	}

	@Override
	public void delete(Component comp) {
		super.session.delete(comp);
	}
	
	public void deleteRelationship(Component originComp, Component targetComp) {
		originComp.getRequiredComponents().remove(targetComp);
	}
	
	public void createRelationship(Component originComp, Component targetComp) {
		originComp.getRequiredComponents().add(targetComp);
	}

	@Override
	public void persist(Component object) {
		super.session.save(object);
	}

	@Override
	Class<Component> getEntityType() {
		return Component.class;
	}
	
	@Override
	public void flushSessionCache() {
		super.flushSessionCache();
	}

}
