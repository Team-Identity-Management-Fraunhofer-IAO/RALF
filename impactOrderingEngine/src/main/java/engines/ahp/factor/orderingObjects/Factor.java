package engines.ahp.factor.orderingObjects;

public class Factor {
	private int factor_id;
	private String factor_name;
	private String factor_description;
	
	public Factor() {
		
	}

	public int getFactor_id() {
		return factor_id;
	}

	public void setFactor_id(int factor_id) {
		this.factor_id = factor_id;
	}

	public String getFactor_name() {
		return factor_name;
	}

	public void setFactor_name(String factor_name) {
		this.factor_name = factor_name;
	}

	public String getFactor_description() {
		return factor_description;
	}

	public void setFactor_description(String factor_description) {
		this.factor_description = factor_description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + factor_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factor other = (Factor) obj;
		if (factor_id != other.factor_id)
			return false;
		return true;
	}
	
}
