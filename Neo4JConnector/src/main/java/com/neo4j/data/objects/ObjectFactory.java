package com.neo4j.data.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neo4j.data.objects.software.persistence.Component;
import com.neo4j.data.objects.software.persistence.Group;
import com.neo4j.data.objects.software.persistence.SWStack;


public class ObjectFactory {
	private ComponentProvider componentProvider;
	private SWStackProvider swStackProvider;
	
	public ObjectFactory() {
		
	}
	
	private void instantiateSWStackProvider() {
		if (swStackProvider == null) {
			swStackProvider = new SWStackProvider();
		}
	}
	
	private void instantiateComponentProvider() {
		if (componentProvider == null) {
			componentProvider = new ComponentProvider();
		}
	}
	
	@Deprecated
	public SWStack createNewSWStack(String name, String groupList) {
		instantiateSWStackProvider();
		SWStack swStack = new SWStack();
		swStack.setName(name);
		swStackProvider.persist(swStack);
		String[] groups = groupList.split(",");
		for (String group : groups) {
			swStackProvider.createGroupWithSWStack(name, group);
		}
		return swStack;
	}
	
	public SWStack createNewSWStack(String name, List<String> groupList) {
		instantiateSWStackProvider();
		SWStack swStack = new SWStack();
		swStack.setName(name);
		swStackProvider.persist(swStack);
		for (String group : groupList) {
			swStackProvider.createGroupWithSWStack(name, group);
		}
		return swStack;
	}
	
	@Deprecated
	public SWStack updateSWStack(long id, String name, String groupList) {
		instantiateSWStackProvider();
		SWStack swStack = swStackProvider.find(id);
		swStack.setName(name);
		swStackProvider.persist(swStack);
		String[] groups = groupList.split(",");
		List<String> existingGroups = new ArrayList<String>();
		for (Group stackGroup : swStack.getGroups()) {
			boolean groupInNewList = false;
			for (String group : groups) {
				if (group.equals(stackGroup.getGroupname())) {
					existingGroups.add(group);
					groupInNewList = true;
					break;
				}
			}
			if (!groupInNewList) {
				swStackProvider.removeStackFromGroup(name, stackGroup.getGroupname());
			}
		}
		for (String group : groups) {
			if (!existingGroups.contains(group)) {
				swStackProvider.createGroupWithSWStack(name, group);
			}
		}
		return swStack;
	}
	
	public SWStack updateSWStackRoles(Long id, List<String> groupList) {
		instantiateSWStackProvider();
		SWStack swStack = swStackProvider.find(id);
		swStackProvider.persist(swStack);		
		List<String> existingGroups = new ArrayList<String>();
		for (Group stackGroup : swStack.getGroups()) {
			boolean groupInNewList = false;
			for (String group : groupList) {
				if (group.equals(stackGroup.getGroupname())) {
					existingGroups.add(group);
					groupInNewList = true;
					break;
				}
			}
			if (!groupInNewList) {
				swStackProvider.removeStackFromGroup(swStack.getName(), stackGroup.getGroupname());
			}
		}
		for (String group : groupList) {
			if (!existingGroups.contains(group)) {
				swStackProvider.createGroupWithSWStack(swStack.getName(), group);
			}
		}
		return swStack;
	}
	
	public Iterable<SWStack> getAllSWStacks(){
		instantiateSWStackProvider();
		swStackProvider.flushSessionCache();
		return swStackProvider.findAll();
	}
	
	public Iterable<Component> getComponentsForSWStack(long id){
		instantiateComponentProvider();
		componentProvider.flushSessionCache();
		return componentProvider.findAllForSWStack(id);		
	}
	
	public Iterable<Group> getGroupsForSWStack(long id){
		instantiateSWStackProvider();
		Iterable<Group> groups = swStackProvider.findGroupsForSWStackId(id);
		return groups;
	}
	
	public void createIncludedComponentRelationship(long id, long componentId) {
		instantiateSWStackProvider();
		instantiateComponentProvider();
		SWStack stack = swStackProvider.find(id);
		swStackProvider.createRelationship(stack, componentProvider.find(componentId));
	}
	
	public void deleteIncludedComponentRelationship(long id, long componentId) {
		instantiateSWStackProvider();
		instantiateComponentProvider();
		SWStack stack = swStackProvider.find(id);
		swStackProvider.deleteRelationship(stack, componentProvider.find(componentId));
	}
	
	public void deleteRelationship(long originId, long relatedId) {
		instantiateComponentProvider();
		Component originComponent = componentProvider.find(originId);
		componentProvider.deleteRelationship(originComponent, componentProvider.find(relatedId));
		componentProvider.persist(originComponent);
	}
	
	public void createRelationship(long originId, long relatedId) {
		instantiateComponentProvider();
		Component originComponent = componentProvider.find(originId);
		componentProvider.createRelationship(originComponent, componentProvider.find(relatedId));
		componentProvider.persist(originComponent);
	}
	
	public void deleteComponent(long id) {
		instantiateComponentProvider();
		componentProvider.delete(componentProvider.find(id));
	}
	
	public void updateComponent(long id, long cpeID, String part, String vendor, String product, String version, String update, String edition, String language, String sw_edition, String target_sw, String target_hw, String other, String versionCmp, boolean isCodebase) {
		instantiateComponentProvider();
		Component myComponent = componentProvider.find(id);
		myComponent.setId(id);
		myComponent.setCpeID(cpeID);
		myComponent.setPart(part);
		myComponent.setVendor(vendor);
		myComponent.setProduct(product);
		myComponent.setVersion(version);
		myComponent.setUpdate(update);
		myComponent.setEdition(edition);
		myComponent.setLanguage(language);
		myComponent.setSw_edition(sw_edition);
		myComponent.setTarget_sw(target_sw);
		myComponent.setTarget_hw(target_hw);
		myComponent.setOther(other);
		myComponent.setVersionCmp(versionCmp);
		myComponent.setCodebase(isCodebase);
		componentProvider.persist(myComponent);
	}
	
	public void updateComponent(Component comp) {
		instantiateComponentProvider();
		componentProvider.persist(comp);
	}
	
	public Iterable<Component> getAllComponents() {
		instantiateComponentProvider();
		return componentProvider.findAll();
	}
	
	public Component createNewComponent(long swStackId, long cpeID, String part, String vendor, String product, String version, String update, String edition, String language, String sw_edition, String target_sw, String target_hw, String other, String versionCmp, boolean isCodebase){	
		instantiateSWStackProvider();
		SWStack stack = swStackProvider.find(swStackId);
		if (stack != null) {
			instantiateComponentProvider();
			Component myComponent = new Component();
			myComponent.setCpeID(cpeID);
			myComponent.setPart(part);
			myComponent.setVendor(vendor);
			myComponent.setProduct(product);
			myComponent.setVersion(version);
			myComponent.setUpdate(update);
			myComponent.setEdition(edition);
			myComponent.setLanguage(language);
			myComponent.setSw_edition(sw_edition);
			myComponent.setTarget_sw(target_sw);
			myComponent.setTarget_hw(target_hw);
			myComponent.setOther(other);
			myComponent.setVersionCmp(versionCmp);
			myComponent.setCodebase(isCodebase);
			stack.getSWStackComponents().add(myComponent);
			componentProvider.persist(myComponent);
			swStackProvider.persist(stack);
			return myComponent;
		}
		return null;
	}
}
