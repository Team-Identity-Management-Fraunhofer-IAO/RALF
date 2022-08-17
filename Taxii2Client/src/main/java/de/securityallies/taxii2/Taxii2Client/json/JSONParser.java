package de.securityallies.taxii2.Taxii2Client.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import de.securityallies.taxii2.Taxii2Client.data.stix.AttackPattern;
import de.securityallies.taxii2.Taxii2Client.data.stix.CourseOfAction;
import de.securityallies.taxii2.Taxii2Client.data.stix.Identity;
import de.securityallies.taxii2.Taxii2Client.data.stix.IntrusionSet;
import de.securityallies.taxii2.Taxii2Client.data.stix.KillChainPhase;
import de.securityallies.taxii2.Taxii2Client.data.stix.Malware;
import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;
import de.securityallies.taxii2.Taxii2Client.data.stix.Reference;
import de.securityallies.taxii2.Taxii2Client.data.stix.Relationship;
import de.securityallies.taxii2.Taxii2Client.data.taxii.APIRoot;
import de.securityallies.taxii2.Taxii2Client.data.taxii.APIRootResource;
import de.securityallies.taxii2.Taxii2Client.data.taxii.Collection;

public class JSONParser {

	public APIRoot parseAPIRoot(String jsonString) {
		JSONObject APIRootObject = new JSONObject(jsonString);
		APIRoot apiRoot = new APIRoot();
		apiRoot.setTitle(APIRootObject.getString("title"));
		if (APIRootObject.has("description")) {
			apiRoot.setDescription(APIRootObject.getString("description"));
		}
		if (APIRootObject.has("contact")) {
			apiRoot.setContact(APIRootObject.getString("contact"));
		}
		if (APIRootObject.has("default")) {
			apiRoot.setDefaultRoot(APIRootObject.getString("default"));
		}
		if (APIRootObject.has("api_roots")) {
			JSONArray apiRootArray = APIRootObject.getJSONArray("api_roots");
			apiRoot.setRoots(new ArrayList<String>());
			for (int i = 0; i < apiRootArray.length(); i++) {
				String identifier = apiRootArray.getString(i);
				apiRoot.getRoots().add(identifier);
			}
		}
		return apiRoot;
	}

	public APIRootResource parseAPIRootResource(String jsonString) {
		JSONObject rootResourceObj = new JSONObject(jsonString);
		APIRootResource resource = new APIRootResource();
		resource.setTitle(rootResourceObj.getString("title"));
		if (rootResourceObj.has("description")) {
			resource.setDescription(rootResourceObj.getString("description"));
		}
		resource.setVersions(new ArrayList<String>());
		if (rootResourceObj.has("versions")) {
			JSONArray versions = rootResourceObj.getJSONArray("versions");
			for (int i = 0; i < versions.length(); i++) {
				resource.getVersions().add(versions.getString(i));
			}
		}
		resource.setMax_content_length(rootResourceObj.getInt("max_content_length"));
		return resource;
	}

	public List<Collection> parseCollections(String jsonString) {
		JSONObject rootObj = new JSONObject(jsonString);
		ArrayList<Collection> collections = new ArrayList<Collection>();
		JSONArray collectionObjects = rootObj.getJSONArray("collections");
		for (int i = 0; i < collectionObjects.length(); i++) {
			JSONObject collectionObj = collectionObjects.getJSONObject(i);
			Collection collection = new Collection();
			collection.setId(collectionObj.getString("id"));
			collection.setTitle(collectionObj.getString("title"));
			collection.setCan_read(collectionObj.getBoolean("can_read"));
			collection.setCan_write(collectionObj.getBoolean("can_write"));
			if (collectionObj.has("description")) {
				collection.setDescription(collectionObj.getString("description"));
			}
			if (collectionObj.has("alias")) {
				collection.setAlias(collectionObj.getString("alias"));
			}
			ArrayList<String> mediaTypes = new ArrayList<String>();
			collection.setMedia_types(mediaTypes);
			if (collectionObj.has("media_types")) {
				JSONArray mediaTypeObjs = collectionObj.getJSONArray("media_types");
				for (int j = 0; j < mediaTypeObjs.length(); j++) {
					collection.getMedia_types().add(mediaTypeObjs.getString(j));
				}
			}
			collections.add(collection);
		}
		return collections;
	}

	public List<Relationship> getRelationshipsFromBundle(JSONObject bundle) {
		JSONArray objects = bundle.getJSONArray("objects");
		List<Relationship> relationships = new ArrayList<Relationship>();
		for (int i = 0; i < objects.length(); i++) {
			JSONObject object = objects.getJSONObject(i);
			if (object.get("type").equals(Relationship.type)) {
				Relationship relationship = new Relationship();
				relationship.setId(object.getString("id"));
				relationship.setModified(object.getString("modified"));
				relationship.setCreated(object.getString("created"));
				relationship.setSource_ref(object.getString("source_ref"));
				relationship.setTarget_ref(object.getString("target_ref"));
				if (object.has("description")) {
					relationship.setDescription(object.getString("description"));
				}
				if (object.has("relationship_type")) {
					relationship.setRelationship_type(object.getString("relationship_type"));
				}
				if (object.has("object_marking_refs")) {
					JSONArray objectMarkingRefs = object.getJSONArray("object_marking_refs");
					relationship.setObject_marking_refs(new ArrayList<MarkingDefinition>());
					for (int j = 0; j < objectMarkingRefs.length(); j++) {
						MarkingDefinition markingDefinition = new MarkingDefinition();
						markingDefinition.setId(objectMarkingRefs.getString(j));
						relationship.getObject_marking_refs().add(markingDefinition);
					}
				}
				if (object.has("external_references")) {
					JSONArray references = object.getJSONArray("external_references");
					relationship.setExternal_references(new ArrayList<Reference>());
					for (int j = 0; j < references.length(); j++) {
						JSONObject ref = references.getJSONObject(j);
						Reference reference = new Reference();
						if (ref.has("external_id")) {
							reference.setExternal_id(ref.getString("external_id"));
						}
						reference.setSource_name(ref.getString("source_name"));
						if (ref.has("url")) {
							reference.setUrl(ref.getString("url"));
						}
						if (ref.has("description")) {
							reference.setDescription(ref.getString("description"));
						}
						relationship.getExternal_references().add(reference);
					}
				}
				relationships.add(relationship);
			}
		}
		return relationships;
	}

	public List<MarkingDefinition> getMarkingDefinitionsFromBundle(JSONObject bundle) {
		JSONArray objects = bundle.getJSONArray("objects");
		List<MarkingDefinition> markingDefinitions = new ArrayList<MarkingDefinition>();
		for (int i = 0; i < objects.length(); i++) {
			JSONObject object = objects.getJSONObject(i);
			if (object.get("type").equals(MarkingDefinition.type)) {
				MarkingDefinition markingDefinition = new MarkingDefinition();
				markingDefinition.setId(object.getString("id"));
				if (object.has("definition_type")) {
					markingDefinition.setDefinition_type(object.getString("definition_type"));
				}
				if (object.has("name")) {
					markingDefinition.setName(object.getString("name"));
				}
				markingDefinition.setCreated(object.getString("created"));
				if (object.has("modified")) {
					markingDefinition.setModified(object.getString("modified"));
				}
				if (object.has("created_by_ref")) {
					Identity identity = new Identity();
					identity.setId(object.getString("created_by_ref"));
					markingDefinition.setCreated_by_reF(identity);
				}
				if (object.has("definition")) {
					JSONObject definitions = object.getJSONObject("definition");
					markingDefinition.setDefinition(new HashMap<String, String>());
					markingDefinition.getDefinition().put(markingDefinition.getDefinition_type(),
							definitions.getString(markingDefinition.getDefinition_type()));
				}
				markingDefinitions.add(markingDefinition);
			}
		}
		return markingDefinitions;
	}

	public List<Identity> getIdentitiesFromBundle(JSONObject bundle) {
		JSONArray objects = bundle.getJSONArray("objects");
		List<Identity> identities = new ArrayList<Identity>();
		for (int i = 0; i < objects.length(); i++) {
			JSONObject object = objects.getJSONObject(i);
			if (object.get("type").equals(Identity.type)) {
				Identity identity = new Identity();
				identity.setId(object.getString("id"));
				identity.setCreated(object.getString("created"));
				identity.setModified(object.getString("modified"));
				identity.setName(object.getString("name"));

				if (object.has("object_marking_refs")) {
					JSONArray markingRefs = object.getJSONArray("object_marking_refs");
					for (int j = 0; j < markingRefs.length(); j++) {
						if (identity.getObject_marking_refs() == null) {
							identity.setObject_marking_refs(new ArrayList<MarkingDefinition>());
						}
						MarkingDefinition markingDefinition = new MarkingDefinition();
						markingDefinition.setId((String) markingRefs.get(j));
						if (!identity.getObject_marking_refs().contains(markingDefinition)) {
							identity.getObject_marking_refs().add(markingDefinition);
						}
					}
				}

				if (object.has("description")) {
					identity.setDescription(object.getString("description"));
				}
				if (object.has("contact_information")) {
					identity.setContact_information(object.getString("contact_information"));
				}
				if (object.has("roles")) {
					identity.setRoles(object.getString("roles"));
				}
				if (object.has("sectors")) {
					identity.setSectors(object.getString("sectors"));
				}
				identities.add(identity);
			}
		}
		return identities;
	}
	
	public List<AttackPattern> getAttackPatternsFromBundle(JSONObject bundle){
		JSONArray objects = bundle.getJSONArray("objects");
		List<AttackPattern> attackPatterns = new ArrayList<AttackPattern>();
		for (int i = 0; i < objects.length(); i++) {
			JSONObject object = objects.getJSONObject(i);
			if (object.get("type").equals(AttackPattern.type)) {
				AttackPattern attackPattern = new AttackPattern();
				if (object.has("object_marking_refs")) {
					JSONArray markingRefs = object.getJSONArray("object_marking_refs");
					for (int j = 0; j < markingRefs.length(); j++) {
						if (attackPattern.getObject_marking_refs() == null) {
							attackPattern.setObject_marking_refs(new ArrayList<MarkingDefinition>());
						}
						MarkingDefinition markingDefinition = new MarkingDefinition();
						markingDefinition.setId((String) markingRefs.get(j));
						if (!attackPattern.getObject_marking_refs().contains(markingDefinition)) {
							attackPattern.getObject_marking_refs().add(markingDefinition);
						}
					}
				}
				attackPattern.setName(object.getString("name"));
				attackPattern.setCreated(object.getString("created"));
				attackPattern.setModified(object.getString("modified"));
				attackPattern.setId(object.getString("id"));
				if (object.has("created_by_ref")) {
					attackPattern.setCreated_by_ref(object.getString("created_by_ref"));
				}
				if (object.has("description")) {
					attackPattern.setDescription(object.getString("description"));
				}
				if (object.has("external_references")) {
					JSONArray external_references = object.getJSONArray("external_references");
					for (int j = 0; j < external_references.length(); j++) {
						Reference reference = new Reference();
						JSONObject JSONRef = external_references.getJSONObject(j);
						if (JSONRef.has("url")) {
							reference.setUrl(JSONRef.getString("url"));
						}
						if (JSONRef.has("external_id")) {
							reference.setExternal_id(JSONRef.getString("external_id"));
						}
						if (JSONRef.has("source_name")) {
							reference.setSource_name(JSONRef.getString("source_name"));
						}
						if (JSONRef.has("description")) {
							reference.setDescription(JSONRef.getString("description"));
						}
						if (attackPattern.getExternal_references() == null) {
							List<Reference> external_refs = new ArrayList<Reference>();
							attackPattern.setExternal_references(external_refs);
						}
						attackPattern.getExternal_references().add(reference);
					}
				}
				if (object.has("kill_chain_phases")) {
					if (attackPattern.getKill_chain_phases() == null) {
						List<KillChainPhase> killChainPhases = new ArrayList<KillChainPhase>();
						attackPattern.setKill_chain_phases(killChainPhases);
					}
					JSONArray kcPhases = object.getJSONArray("kill_chain_phases");
					for (int j = 0; j < kcPhases.length(); j++) {
						JSONObject killChainPhaseJSON = kcPhases.getJSONObject(j);
						KillChainPhase killChainPhase = new KillChainPhase();
						killChainPhase.setKill_chain_name(killChainPhaseJSON.getString("kill_chain_name"));
						killChainPhase.setPhase_name(killChainPhaseJSON.getString("phase_name"));
						attackPattern.getKill_chain_phases().add(killChainPhase);
					}
				}
				if (object.has("x_mitre_data_sources")) {
					if (attackPattern.getX_mitre_data_sources() == null) {
						List<String> dataSources = new ArrayList<String>();
						attackPattern.setX_mitre_data_sources(dataSources);
					}
					JSONArray JSONDataSources = object.getJSONArray("x_mitre_data_sources");
					for (int j = 0; j < JSONDataSources.length();j++) {
						attackPattern.getX_mitre_data_sources().add(JSONDataSources.getString(j));
					}
				}
				if (object.has("x_mitre_version")) {
					attackPattern.setX_mitre_version(object.getString("x_mitre_version"));
				}
				if (object.has("x_mitre_permissions_required")) {
					if (attackPattern.getX_mitre_permissions_required() == null) {
						ArrayList<String> permissionsRequired = new ArrayList<String>();
						attackPattern.setX_mitre_permissions_required(permissionsRequired);
					}
					JSONArray permissions = object.getJSONArray("x_mitre_permissions_required");
					for (int j = 0; j < permissions.length(); j++) {
						attackPattern.getX_mitre_permissions_required().add(permissions.getString(j));
					}
				}
				if (object.has("x_mitre_defense_bypassed")) {
					if (attackPattern.getX_mitre_defense_bypassed() == null) {
						ArrayList<String> defenses_bypassed = new ArrayList<String>();
						attackPattern.setX_mitre_defense_bypassed(defenses_bypassed);
					}
					JSONArray defenses_bypassed = object.getJSONArray("x_mitre_defense_bypassed");
					for (int j = 0; j < defenses_bypassed.length();j++) {
						attackPattern.getX_mitre_defense_bypassed().add(defenses_bypassed.getString(j));
					}
				}
				if (object.has("x_mitre_platforms")) {
					if (attackPattern.getX_mitre_platforms() == null) {
						ArrayList<String> platforms = new ArrayList<String>();
						attackPattern.setX_mitre_platforms(platforms);
					}
					JSONArray platforms = object.getJSONArray("x_mitre_platforms");
					for (int j = 0; j < platforms.length(); j++) {
						attackPattern.getX_mitre_platforms().add(platforms.getString(j));
					}
				}
				if (object.has("x_mitre_is_subtechnique")) {
					attackPattern.setX_mitre_is_subtechnique(object.getBoolean("x_mitre_is_subtechnique"));
				}
				if (object.has("x_mitre_contributors")) {
					if (attackPattern.getX_mitre_contributors() == null) {
						ArrayList<String> contributors = new ArrayList<String>();
						attackPattern.setX_mitre_contributors(contributors);
					}
					JSONArray contributors = object.getJSONArray("x_mitre_contributors");
					for (int j = 0; j < contributors.length(); j++) {
						attackPattern.getX_mitre_contributors().add(contributors.getString(j));
					}
				}
				if (object.has("x_mitre_detection")) {
					attackPattern.setX_mitre_detection(object.getString("x_mitre_detection"));
				}
				attackPatterns.add(attackPattern);
			}
		}
		return attackPatterns;
	}
	
	public List<CourseOfAction> getCoursesOfActionFromBundle(JSONObject bundle){
		JSONArray objects = bundle.getJSONArray("objects");
		ArrayList<CourseOfAction> mitigations = new ArrayList<CourseOfAction>();
		for (int i = 0; i < objects.length(); i++) {
			JSONObject object = objects.getJSONObject(i);
			if (object.get("type").equals(CourseOfAction.type)) {
				CourseOfAction mitigation = new CourseOfAction();
				if (object.has("object_marking_refs")) {
					JSONArray markingRefs = object.getJSONArray("object_marking_refs");
					for (int j = 0; j < markingRefs.length(); j++) {
						if (mitigation.getObject_marking_refs() == null) {
							mitigation.setObject_marking_refs(new ArrayList<MarkingDefinition>());
						}
						MarkingDefinition markingDefinition = new MarkingDefinition();
						markingDefinition.setId((String) markingRefs.get(j));
						if (!mitigation.getObject_marking_refs().contains(markingDefinition)) {
							mitigation.getObject_marking_refs().add(markingDefinition);
						}
					}
				}
				mitigation.setName(object.getString("name"));
				if (object.has("description")) {
					mitigation.setDescription(object.getString("description"));
				}
				if (object.has("modified")) {
					mitigation.setModified(object.getString("modified"));
				}
				mitigation.setId(object.getString("id"));
				if (object.has("created")) {
					mitigation.setCreated(object.getString("created"));
				}
				if (object.has("created_by_ref")) {
					Identity refIdentity = new Identity();
					refIdentity.setId(object.getString("id"));
					mitigation.setCreated_by_ref(refIdentity);
				}
				if (object.has("x_mitre_version")) {
					mitigation.setX_mitre_version(object.getString("x_mitre_version"));
				}
				if (object.has("x_mitre_deprecated")) {
					mitigation.setX_mitre_deprecated(object.getBoolean("x_mitre_deprecated"));
				}
				mitigations.add(mitigation);
			}
		}
		return mitigations;
	}
	
	public List<IntrusionSet> getIntrusionSetsFromBundle(JSONObject bundle){
		JSONArray objects = bundle.getJSONArray("objects");
		ArrayList<IntrusionSet> attackers = new ArrayList<IntrusionSet>();
		for (int i = 0; i < objects.length(); i++) {
			JSONObject object = objects.getJSONObject(i);
			if (object.getString("type").equals(IntrusionSet.type)) {
				IntrusionSet attacker = new IntrusionSet();
				if (object.has("object_marking_refs")) {
					JSONArray markingRefs = object.getJSONArray("object_marking_refs");
					for (int j = 0; j < markingRefs.length(); j++) {
						if (attacker.getObject_marking_refs() == null) {
							attacker.setObject_marking_refs(new ArrayList<MarkingDefinition>());
						}
						MarkingDefinition markingDefinition = new MarkingDefinition();
						markingDefinition.setId((String) markingRefs.get(j));
						if (!attacker.getObject_marking_refs().contains(markingDefinition)) {
							attacker.getObject_marking_refs().add(markingDefinition);
						}
					}
				}
				attacker.setName(object.getString("name"));
				if (object.has("modified")) {
					attacker.setModified(object.getString("modified"));
				}
				if (object.has("created")) {
					attacker.setCreated(object.getString("created"));
				}
				if (object.has("created_by_ref")) {
					Identity identity = new Identity();
					identity.setId(object.getString("created_by_ref"));
					attacker.setCreated_by_ref(identity);
				}
				if (object.has("aliases")) {
					JSONArray JSONAliases = object.getJSONArray("aliases");
					for (int j = 0; j < JSONAliases.length(); j++) {
						if (attacker.getAliases() == null) {
							ArrayList<String> aliases = new ArrayList();
							attacker.setAliases(aliases);
						}
						attacker.getAliases().add(JSONAliases.getString(j));
					}
				}
				attacker.setId(object.getString("id"));
				if (object.has("description")) {
					attacker.setDescription("description");
				}
				if (object.has("external_references")) {
					JSONArray JSONReferences = object.getJSONArray("external_references");
					for (int j = 0; j < JSONReferences.length(); j++) {
						JSONObject JSONRef = JSONReferences.getJSONObject(j);
						if (attacker.getReferences() == null) {
							ArrayList<Reference> references = new ArrayList<Reference>();
							attacker.setReferences(references);
						}
						Reference reference = new Reference();
						if (JSONRef.has("url")) {
							reference.setUrl(JSONRef.getString("url"));
						}
						if (JSONRef.has("external_id")) {
							reference.setExternal_id(JSONRef.getString("external_id"));
						}
						if (JSONRef.has("description")) {
							reference.setDescription(JSONRef.getString("description"));
						}
						if (JSONRef.has("source_name")) {
							reference.setSource_name(JSONRef.getString("source_name"));
						}
						attacker.getReferences().add(reference);
					}
				}
				if (object.has("x_mitre_version")) {
					attacker.setX_mitre_version(object.getString("x_mitre_version"));
				}
				if (object.has("x_mitre_contributors")) {
					JSONArray JSONContributors = object.getJSONArray("x_mitre_contributors");
					for (int j = 0; j < JSONContributors.length(); j++) {
						if (attacker.getX_mitre_contributors() == null) {
							ArrayList<String> contributors = new ArrayList<String>();
							attacker.setX_mitre_contributors(contributors);
						}
						attacker.getX_mitre_contributors().add(JSONContributors.getString(j));
					}
				}
				attackers.add(attacker);
			}
		}
		return attackers;
	}
	
	public List<Malware> getMalwareFromBundle(JSONObject bundle){
		JSONArray objects = bundle.getJSONArray("objects");
		ArrayList<Malware> malwareList = new ArrayList<Malware>();
		for (int i = 0; i < objects.length(); i++) {
			JSONObject object = objects.getJSONObject(i);
			if (object.getString("type").equals(Malware.type)) {
				Malware malware = new Malware();
				if (object.has("object_marking_refs")) {
					JSONArray markingRefs = object.getJSONArray("object_marking_refs");
					for (int j = 0; j < markingRefs.length(); j++) {
						if (malware.getObject_marking_refs() == null) {
							malware.setObject_marking_refs(new ArrayList<MarkingDefinition>());
						}
						MarkingDefinition markingDefinition = new MarkingDefinition();
						markingDefinition.setId((String) markingRefs.get(j));
						if (!malware.getObject_marking_refs().contains(markingDefinition)) {
							malware.getObject_marking_refs().add(markingDefinition);
						}
					}
				}
				if (object.has("external_references")) {
					JSONArray JSONReferences = object.getJSONArray("external_references");
					for (int j = 0; j < JSONReferences.length(); j++) {
						JSONObject JSONRef = JSONReferences.getJSONObject(j);
						if (malware.getReferences() == null) {
							ArrayList<Reference> references = new ArrayList<Reference>();
							malware.setReferences(references);
						}
						Reference reference = new Reference();
						if (JSONRef.has("url")) {
							reference.setUrl(JSONRef.getString("url"));
						}
						if (JSONRef.has("external_id")) {
							reference.setExternal_id(JSONRef.getString("external_id"));
						}
						if (JSONRef.has("description")) {
							reference.setDescription(JSONRef.getString("description"));
						}
						if (JSONRef.has("source_name")) {
							reference.setSource_name(JSONRef.getString("source_name"));
						}
						malware.getReferences().add(reference);
					}
				}
				malware.setName(object.getString("name"));
				if (object.has("modified")) {
					malware.setModified(object.getString("modified"));
				}
				if (object.has("created")) {
					malware.setCreated(object.getString("created"));
				}
				malware.setId(object.getString("id"));
				if (object.has("description")) {
					malware.setDescription(object.getString("description"));
				}
				if (object.has("labels")) {
					if (malware.getLabels() == null) {
						ArrayList<String> labels = new ArrayList<String>();
						malware.setLabels(labels);
					}
					JSONArray labels = object.getJSONArray("labels");
					for (int j = 0; j < labels.length(); j++) {
						malware.getLabels().add(labels.getString(j));
					}
				}
				if (object.has("created_by_ref")) {
					Identity identity = new Identity();
					identity.setId(object.getString("created_by_ref"));
					malware.setCreated_by_ref(identity);
				}
				if (object.has("x_mitre_aliases")) {
					if (malware.getX_mitre_aliases() == null) {
						ArrayList<String> aliases = new ArrayList<String>();
						malware.setX_mitre_aliases(aliases);
					}
					JSONArray aliases = object.getJSONArray("x_mitre_aliases");
					for (int j = 0; j < aliases.length(); j++) {
						malware.getX_mitre_aliases().add(aliases.getString(j));
					}
				}
				if (object.has("x_mitre_version")) {
					malware.setX_mitre_version(object.getString("x_mitre_version"));
				}
				if (object.has("x_mitre_platforms")) {
					if (malware.getX_mitre_platforms()==null) {
						ArrayList<String> platforms = new ArrayList<String>();
						malware.setX_mitre_platforms(platforms);
					}
					JSONArray platforms = object.getJSONArray("x_mitre_platforms");
					for (int j = 0; j < platforms.length(); j++) {
						malware.getX_mitre_platforms().add(platforms.getString(j));
					}
				}
				malwareList.add(malware);
			}
		}
		return malwareList;
	}

}
