package com.sql.data.objects.aggregations;

import java.util.List;

import com.sql.data.objects.persistence.vulnerabilites.references.Reference;
import com.sql.data.objects.persistence.vulnerabilites.references.ReferenceTag;

public class AggregatedReference { 
	private Reference reference;
	private List<ReferenceTag> referenceTags;
	
	public Reference getReference() {
		return reference;
	}
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	public List<ReferenceTag> getReferenceTags() {
		return referenceTags;
	}
	public void setReferenceTags(List<ReferenceTag> referenceTags) {
		this.referenceTags = referenceTags;
	}
	
}
