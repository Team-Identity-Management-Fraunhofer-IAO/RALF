package com.sql.data.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.sql.data.objects.persistence.vulnerabilities.CVECore;
import com.sql.data.objects.aggregations.AggregatedReference;
import com.sql.data.objects.persistence.vulnerabilites.references.Reference;
import com.sql.data.objects.persistence.vulnerabilites.references.ReferenceTag;

public class ReferenceProvider extends PersistenceObjectProvider<Reference> implements PersistenceObjectProviderService<Reference>{

	private List<ReferenceType> referenceTypes;
	public enum ReferenceType {ADVISORY, EXPLOIT, PATCH};
	
	public ReferenceProvider() {
		referenceTypes = new ArrayList<ReferenceType>();
	}
	
	@Override
	public Reference find(int id) {
		return super.find(id);
	}

	@Override
	public Class<Reference> getClassName() {
		return Reference.class;
	}
	
	public boolean containsExploit() {
		return referenceTypes.contains(ReferenceType.EXPLOIT);
	}
	
	public boolean containsPatch() {
		return referenceTypes.contains(ReferenceType.PATCH);
	}
	
	public boolean containsAdvisory() {
		return referenceTypes.contains(ReferenceType.ADVISORY);
	}
	
	public List<AggregatedReference> getAggregatedReferenceForCVEYearAndCVEID(int cveYear, int cveID){
		super.instantiateHibernateSessionFactory();
		Session session = super.hibernateSessionFactory.openSession();
		String sql = "SELECT * FROM reference WHERE cveYear = :cveYear AND cveID = :cveID";
		NativeQuery<Reference> refQuery = session.createNativeQuery(sql, Reference.class);
		refQuery.setParameter("cveYear",cveYear);
		refQuery.setParameter("cveID", cveID);
		List<AggregatedReference> result = new ArrayList();
		try {
			List<Reference> references = refQuery.list();
			for (Reference reference : references) {
				AggregatedReference aggReference = new AggregatedReference();
				aggReference.setReference(reference);
				aggReference.setReferenceTags(new ArrayList<ReferenceTag>());
				sql = "SELECT * FROM reference_tag WHERE refID = :refID";
				NativeQuery<ReferenceTag> tagQuery = session.createNativeQuery(sql, ReferenceTag.class);
				tagQuery.setParameter("refID", reference.getRefID());
				List<ReferenceTag> tags = tagQuery.list();
				for (ReferenceTag tag : tags) {
					if (tag.getTag().equals("Third Party Advisory") || tag.getTag().equals("Vendor Advisory")) {
						referenceTypes.add(ReferenceType.ADVISORY);
					}else if (tag.getTag().equals("Patch")) {
						referenceTypes.add(ReferenceType.PATCH);
					}else if (tag.getTag().equals("Exploit")) {
						referenceTypes.add(ReferenceType.EXPLOIT);
					}
					aggReference.getReferenceTags().add(tag);
				}
				result.add(aggReference);
			}
			session.close();
			return result;
		}catch (NoResultException ex) {
			session.close();
			return result;
		}
	}
	
	public List<AggregatedReference> getReferencesForIdentifier(String identifier){
		CVECore cve = new CVECore();
		cve.parseIdentifierString(identifier);
		return getAggregatedReferenceForCVEYearAndCVEID(cve.getCveYear(), cve.getCveID());
	}
	
	public Map<String, List<AggregatedReference>> getReferencesForIdentifierList(List<String> identifierList){
		Map<String, List<AggregatedReference>> referenceMap = new HashMap<String, List<AggregatedReference>>();
		for (String identifier : identifierList) {
			CVECore cve = new CVECore();
			cve.parseIdentifierString(identifier);
			List<AggregatedReference> references = getAggregatedReferenceForCVEYearAndCVEID(cve.getCveYear(), cve.getCveID());
			referenceMap.put(identifier, references);
		}
		return referenceMap;
	}
	
	

}
