package de.securityallies.taxii2.Taxii2Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

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
import de.securityallies.taxii2.Taxii2Client.json.JSONParser;

public class Taxii2Client {
	private static final String headerTaxii = "application/vnd.oasis.taxii+json";
	private static final String headerStix = "application/vnd.oasis.stix+json";

	public enum headerType {
		STIX, TAXII2
	}

	private enum function {
		COLLECTIONS, ALLOBJECTS, API_ROOTS, API_ROOT_INFORMATION
	}

	private String endpoint;
	
	public APIRoot getAPIRoots() {
		String responseBody = getResponseBodyForEndpoint(null, function.API_ROOTS);
		JSONParser parser = new JSONParser();		
		return parser.parseAPIRoot(responseBody);
	}
	
	public APIRootResource getAPIRootResource() {
		String responseBody = getResponseBodyForEndpoint(null, function.API_ROOT_INFORMATION);
		JSONParser parser = new JSONParser();		
		return parser.parseAPIRootResource(responseBody);
	}
	
	public List<Collection> getCollections(){
		String responseBody = getResponseBodyForEndpoint(null, function.COLLECTIONS);
		JSONParser parser = new JSONParser();
		return parser.parseCollections(responseBody);
	}
	
	

	private String getResponseBodyForEndpoint(String suffix, Taxii2Client.function function) {
		String functionSuffix = "";
		String header = "";
		switch (function) {
			case COLLECTIONS: {
				functionSuffix = "collections/";
				header = Taxii2Client.headerTaxii;
				break;
			}
			case API_ROOTS:{
				functionSuffix = "";
				header = Taxii2Client.headerTaxii;
				break;
			}
			case API_ROOT_INFORMATION:{
				functionSuffix = "";
				header = Taxii2Client.headerTaxii;
				break;
			}
			case ALLOBJECTS:{
				functionSuffix = "objects/";
				header = Taxii2Client.headerStix;
			}
		}
		if (suffix != null && !suffix.endsWith("/")) {
			suffix += "/";
		}
		if (endpoint == null) {
			return null;
		}
		try {
			URL endpoint = new URL(this.endpoint + (suffix!=null?suffix:"") +functionSuffix);
			HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
			con.setRequestProperty("Accept", header);
			con.setRequestProperty("Content-Type", header);
			con.setRequestMethod("GET");
			con.connect();			
			String responseBody = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
					.collect(Collectors.joining("\n"));
			con.disconnect();
			return responseBody;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getLocalizedMessage());
		}
		return null;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		if (!endpoint.endsWith("/")) {
			endpoint += "/";
		}
		this.endpoint = endpoint;
	}
	
	public String getObjects(String identifier) {
		String responseBody = getResponseBodyForEndpoint("collections/"+identifier, function.ALLOBJECTS);
		return responseBody;
	}
	
	public List<Identity> getIdentitiesFromObjects(String identifier){
		String responseBody = getObjects(identifier);
		JSONParser parser = new JSONParser();
		return parser.getIdentitiesFromBundle(new JSONObject(responseBody));
	}
	
	public List<MarkingDefinition> getMarkingDefinitionsFromObjects(String identifier){
		String responseBody = getObjects(identifier);
		JSONParser parser = new JSONParser();
		return parser.getMarkingDefinitionsFromBundle(new JSONObject(responseBody));
	}
	
	public List<Relationship> getRelationshipsFromObjects(String identifier){
		String responseBody = getObjects(identifier);
		JSONParser parser = new JSONParser();
		return parser.getRelationshipsFromBundle(new JSONObject(responseBody));
	}
	
	public List<AttackPattern> getAttackPatternsFromObjects(String identifier){
		String responseBody = getObjects(identifier);
		JSONParser parser = new JSONParser();
		return parser.getAttackPatternsFromBundle(new JSONObject(responseBody));
	}
	
	public List<CourseOfAction> getCoursesOfActionsFromObjects(String identifier){
		String responseBody = getObjects(identifier);
		JSONParser parser = new JSONParser();
		return parser.getCoursesOfActionFromBundle(new JSONObject(responseBody));
	}
	
	public List<IntrusionSet> getIntrusionSetsFromObjects(String identifier){
		String responseBody = getObjects(identifier);
		JSONParser parser = new JSONParser();
		return parser.getIntrusionSetsFromBundle(new JSONObject(responseBody));
	}
	
	public List<Malware> getMalwareFromObjects(String identifier){
		String responseBody = getObjects(identifier);
		JSONParser parser = new JSONParser();
		return parser.getMalwareFromBundle(new JSONObject(responseBody));
	}

}
