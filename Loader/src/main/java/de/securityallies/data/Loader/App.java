package de.securityallies.data.Loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;

import com.sql.hibernate.HibernateThreatMasterLoaderInstance;

import de.securityallies.taxii2.Taxii2Client.Taxii2Client;
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
    	Properties threatMasterProperties = new Properties();
    	try {
			threatMasterProperties.load(new FileInputStream(new File("src/main/java/hibernate-threatMaster.properties")));
			HibernateThreatMasterLoaderInstance.setProperties(threatMasterProperties);
			SessionFactory sessionFactory = HibernateThreatMasterLoaderInstance.getSessionFactory();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
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
        		
        		/*
        		List<MarkingDefinition> markingDefinitions = client.getMarkingDefinitionsFromObjects(collection.getId());
        		MarkingDefinitionLoader markingDefinitionLoader = new MarkingDefinitionLoader();
        		markingDefinitionLoader.loadMarkingDefinitionsFromList(markingDefinitions, collection.getId(), timestamp);
        		
        		List<Identity> identities = client.getIdentitiesFromObjects(collection.getId());
        		IdentityLoader identityLoader = new IdentityLoader();
        		identityLoader.loadIdentities(identities, collection.getId(), timestamp);
        		
        		System.out.println("Parsing AttackPatterns...");
        		List<AttackPattern> attackPatterns = client.getAttackPatternsFromObjects(collection.getId());
        		AttackPatternLoader attackPatternLoader = new AttackPatternLoader();
        		attackPatternLoader.loadAttackPatterns(attackPatterns, collection.getId(), timestamp);
        		*/
        		System.out.println("Parsing Attackers...");
        		List<IntrusionSet> intrusionSets = client.getIntrusionSetsFromObjects(collection.getId());
        		IntrusionSetLoader intrusionSetLoader = new IntrusionSetLoader();
        		intrusionSetLoader.loadIntrusionSets(intrusionSets, collection.getId(), timestamp);
        		
        		/*System.out.println("Parsing Malware...");
        		List<Malware> malwareList = client.getMalwareFromObjects(collection.getId());
        		MalwareLoader malwareLoader = new MalwareLoader();
        		malwareLoader.loadMalware(malwareList, collection.getId(), timestamp);
        		*/
        		/*System.out.println("Parsing Mitigations...");
        		List<CourseOfAction> coursesOfAction = client.getCoursesOfActionsFromObjects(collection.getId());
        		CourseOfActionLoader courseOfActionLoader = new CourseOfActionLoader();
        		courseOfActionLoader.loadCoursesOfAction(coursesOfAction, collection.getId(), timestamp);
        		*/
        		System.out.println("Parsing relationships...");
        		List<Relationship> relationships = client.getRelationshipsFromObjects(collection.getId());
        		RelationshipLoader relationshipLoader = new RelationshipLoader();
        		relationshipLoader.LoadRelationships(relationships, collection.getId(), timestamp);
        		
        		
        		
        		
        		
        		
        		
        	}
        }
    }
}
