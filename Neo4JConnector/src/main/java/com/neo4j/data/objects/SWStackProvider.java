package com.neo4j.data.objects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.neo4j.ogm.cypher.query.SortOrder;

import com.neo4j.data.objects.software.persistence.Component;
import com.neo4j.data.objects.software.persistence.Group;
import com.neo4j.data.objects.software.persistence.SWStack;
import com.neo4j.data.objects.software.persistence.Subnet;

public class SWStackProvider extends ObjectProvider<SWStack>{

	private static final int DEPTH_LIST = 0;
	private static final int DEPTH_ENTITY = 1;

	public SWStackProvider() {
		super();
	}
	
	public Iterable<Subnet> findAllSubnets(){
		String cypher = "MATCH (a:Subnet) RETURN a";
		return super.session.query(Subnet.class, cypher, new HashMap<String, Object>());
	}
	
	public Iterable<Subnet> findSubnetsForSWStack(Long id){
		String cypher = "MATCH (b:SWStack)-[r:associatedSubnet]->(a:Subnet) WHERE id(b)=$id RETURN a";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		return super.session.query(Subnet.class, cypher, parameters);
	}
	
	public Iterable<SWStack> findSWStacksForSubnet(String subnet){
		String cypher = "MATCH (a:SWStack)-[r:associatedSubnet]->(b:Subnet) WHERE b.ipRange=$ipRange RETURN a";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ipRange", subnet);
		return super.session.query(SWStack.class, cypher, parameters);
	}
	
	public Iterable<SWStack> findConnectedSWStack(Long id){
		String cypher = "MATCH (a:SWStack)-[r:connectedTo]->(b:SWStack) WHERE id(a)=$id RETURN b";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		return super.session.query(SWStack.class, cypher, parameters);
	}
	
	public SWStack findSWStackForStackname(String stackname) {
		String cypher = "MATCH (s:SWStack) WHERE s.name=$stackname RETURN s";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stackname", stackname);
		return super.session.queryForObject(SWStack.class, cypher, parameters);
	}

	public Iterable<SWStack> findAllForGroupname(String groupname){
		String cypher = "MATCH (a:Group)-[r:contains]->(b:SWStack) WHERE a.groupname=$groupname RETURN b";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("groupname", groupname);
		return super.session.query(SWStack.class, cypher, parameters);		
	}
	
	public Iterable<Group> findGroupsForSWStackId(Long id){
		String cypher = "MATCH (a:Group)-[r:contains]->(s:SWStack) WHERE id(s)=$id RETURN a";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		return super.session.query(Group.class, cypher, parameters);
	}
	
	public Iterable<Group> getAllGroups(){
		String cypher = "MATCH (a:Group) RETURN a";
		return super.session.query(Group.class, cypher, new HashMap<String, Object>());
	}
	
	public boolean checkIfGroupnameExists(String groupname) {
		String cypher = "MATCH (a:Group) WHERE a.name=$groupname RETURN a";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("groupname", groupname);
		Group result = super.session.queryForObject(Group.class, cypher, parameters);
		if (result == null) {
			return false;
		}
		return true;
	}
	
	public Long getIdForGroupname(String groupname) {
		String cypher = "MATCH (a:Group) WHERE a.groupname=$groupname RETURN id(a)";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("groupname", groupname);
		Long id = super.session.queryForObject(Long.class, cypher, parameters);
		return id;
	}
	
	public Group getGroupForId(Long id) {
		return super.session.load(Group.class, id, DEPTH_ENTITY);
	}
	
	public Group getGroupForGroupname(String groupname) {
		String cypher = "MATCH (a:Group) WHERE a.groupname=$groupname RETURN a";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("groupname", groupname);
		return super.session.queryForObject(Group.class, cypher, parameters);
	}
	
	public void createGroupWithSWStack(Long stackID, String groupname) {
		Group group = getGroupForGroupname(groupname);
		SWStack stack = find(stackID);
		if (group != null) {
			group.setGroupname(groupname);
			group.getSWStacks().add(stack);
			super.session.save(group);
		}else {
			group = new Group();	
			group.setGroupname(groupname);
			group.getSWStacks().add(stack);
			super.session.save(group);
		}
	}
	
	public void createGroupWithSWStack(String stackName, String groupname) {
		Group group = getGroupForGroupname(groupname);
		SWStack stack = findSWStackForStackname(stackName);
		if (group != null) {
			group.setGroupname(groupname);
			group.getSWStacks().add(stack);
			super.session.save(group);
		}else {
			group = new Group();	
			group.setGroupname(groupname);
			group.getSWStacks().add(stack);
			super.session.save(group);
		}
	}
	
	public void removeStackFromGroup(Long stackID, String groupname) {
		Group group = getGroupForId(getIdForGroupname(groupname));
		SWStack stack = find(stackID);
		if (group != null && stack != null) {
			Iterator<SWStack> it = group.getSWStacks().iterator();
			SWStack curr = null;
			while (it.hasNext()) {
				curr = it.next();
				if (curr.getId().equals(stackID)) {
					break;
				}
			}
			if (curr != null) {
				group.getSWStacks().remove(curr);
				super.session.save(group);
			}
		}
	}
	
	@Deprecated
	public void removeStackFromGroup(String stackName, String groupname) {
		Group group = getGroupForId(getIdForGroupname(groupname));
		SWStack stack = findSWStackForStackname(stackName);
		if (group != null && stack != null) {
			Iterator<SWStack> it = group.getSWStacks().iterator();
			SWStack curr = null;
			while (it.hasNext()) {
				curr = it.next();
				if (curr.getName().equals(stackName)) {
					break;
				}
			}
			if (curr != null) {
				group.getSWStacks().remove(curr);
				super.session.save(group);
			}
		}
	}
	
	@Override
	public Iterable<SWStack> findAll() {
		return super.session.loadAll(SWStack.class, new SortOrder().add(SortOrder.Direction.ASC, "name"), DEPTH_LIST);
	}

	@Override
	public SWStack find(Long id) {
		return super.session.load(SWStack.class, id, DEPTH_ENTITY);
	}

	@Override
	public void delete(SWStack comp) {
		super.session.delete(comp);
	}
	
	public void deleteRelationship(SWStack stack, Component includedComp) {
		stack.getSWStackComponents().remove(includedComp);
	}
	
	public void createRelationship(SWStack stack, Component includedComp) {
		stack.getSWStackComponents().add(includedComp);
	}

	@Override
	public void persist(SWStack object) {
		super.session.save(object);
	}

	@Override
	Class<SWStack> getEntityType() {
		return SWStack.class;
	}
	
	@Override
	public void flushSessionCache() {
		super.flushSessionCache();
	}

}
