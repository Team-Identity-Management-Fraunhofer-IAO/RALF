package de.securityallies.taxii2.Taxii2Client;

import java.util.List;

import de.securityallies.taxii2.Taxii2Client.data.stix.AttackPattern;
import de.securityallies.taxii2.Taxii2Client.data.stix.CourseOfAction;
import de.securityallies.taxii2.Taxii2Client.data.stix.Identity;
import de.securityallies.taxii2.Taxii2Client.data.stix.IntrusionSet;
import de.securityallies.taxii2.Taxii2Client.data.stix.Malware;
import de.securityallies.taxii2.Taxii2Client.data.stix.MarkingDefinition;
import de.securityallies.taxii2.Taxii2Client.data.stix.Relationship;
import de.securityallies.taxii2.Taxii2Client.data.taxii.APIRoot;
import de.securityallies.taxii2.Taxii2Client.data.taxii.APIRootResource;
import de.securityallies.taxii2.Taxii2Client.data.taxii.Collection;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Taxii2Client client = new Taxii2Client();
        client.setEndpoint("https://cti-taxii.mitre.org/taxii/");
        APIRoot root = client.getAPIRoots();
        System.out.println(root.getTitle());
        System.out.println(root.getDescription());
        System.out.println(root.getDefaultRoot());
        for (String identifier : root.getRoots()) {
        	System.out.println(identifier);
        	client.setEndpoint(identifier);
        	APIRootResource resource = client.getAPIRootResource();
        	System.out.println(resource.getTitle());
        	System.out.println(resource.getDescription());
        	System.out.println(resource.getMax_content_length());
        	for (String version : resource.getVersions()) {
        		System.out.println(version);
        	}
        	List<Collection> collections = client.getCollections();
        	for (Collection collection : collections) {
        		System.out.println(collection.getTitle());
        		System.out.println(collection.getAlias());
        		System.out.println(collection.getId());
        		System.out.println(collection.getDescription());
        		for (String mediaType : collection.getMedia_types()) {
        			System.out.println(collection.getMedia_types());
        		}
        		List<Identity> identities = client.getIdentitiesFromObjects(collection.getId());
        		for (Identity identity : identities) {
        			System.out.println(identity.getId());
        			System.out.println(identity.getName());
        		}
        		List<MarkingDefinition> markingDefinitions = client.getMarkingDefinitionsFromObjects(collection.getId());
        		for (MarkingDefinition markingDefinition : markingDefinitions) {
        			System.out.println(markingDefinition.getId());
        			System.out.println(markingDefinition.getDefinition().get(markingDefinition.getDefinition_type()));
        		}
        		System.out.println("Parsing relationships...");
        		List<Relationship> relationships = client.getRelationshipsFromObjects(collection.getId());
        		for (Relationship relationship : relationships) {
        			System.out.println("Relationship "+relationship.getId()+" "+relationship.getRelationship_type()+" between "+relationship.getSource_ref()+" and "+relationship.getTarget_ref());
        		}
        		System.out.println("Parsing AttackPatterns...");
        		List<AttackPattern> attackPatterns = client.getAttackPatternsFromObjects(collection.getId());
        		for (AttackPattern attackPattern : attackPatterns) {
        			System.out.println("Attack Pattern "+attackPattern.getName());
        		}
        		System.out.println("Parsing Mitigations...");
        		List<CourseOfAction> coursesOfAction = client.getCoursesOfActionsFromObjects(collection.getId());
        		for (CourseOfAction mitigation : coursesOfAction) {
        			System.out.println("Mitigation "+mitigation.getName()+" deprecated:"+mitigation.isX_mitre_deprecated());
        		}
        		System.out.println("Parsing Attackers...");
        		List<IntrusionSet> intrusionSets = client.getIntrusionSetsFromObjects(collection.getId());
        		for (IntrusionSet attacker : intrusionSets) {
        			System.out.println("Attacker "+attacker.getName());
        		}
        		System.out.println("Parsing Malware...");
        		List<Malware> malwareList = client.getMalwareFromObjects(collection.getId());
        		for (Malware malware : malwareList) {
        			System.out.println("Malware "+malware.getName());
        		}
        	}
        }
        //System.out.println(client.getObjects("95ecc380-afe9-11e4-9b6c-751b66dd541e"));
    }
}
