package de.securityallies.taxii2.Taxii2Client.data.stix;

import java.util.List;

public class Bundle {
	private final static String type = "bundle";
	private String id;
	private String spec_version;
	
	private List<Identity> identities;
	private List<AttackPattern> attackPatterns;
	private List<CourseOfAction> courseOfActions;

}
