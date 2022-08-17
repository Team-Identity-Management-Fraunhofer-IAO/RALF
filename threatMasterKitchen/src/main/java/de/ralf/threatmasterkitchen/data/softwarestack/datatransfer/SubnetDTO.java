package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

public class SubnetDTO {
	
	private String ip;
	private String vectorNetworkMin;
	private String vectorNetworkMax;
	private String vectorAdjacentMin;
	private String vectorAdjacentMax;
	private String vectorLocalMin;
	private String vectorLocalMax;
	private String vectorPhysicalMin;
	private String vectorPhysicalMax;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getVectorNetworkMin() {
		return vectorNetworkMin;
	}
	public void setVectorNetworkMin(String vectorNetworkMin) {
		this.vectorNetworkMin = vectorNetworkMin;
	}
	public String getVectorNetworkMax() {
		return vectorNetworkMax;
	}
	public void setVectorNetworkMax(String vectorNetworkMax) {
		this.vectorNetworkMax = vectorNetworkMax;
	}
	public String getVectorAdjacentMin() {
		return vectorAdjacentMin;
	}
	public void setVectorAdjacentMin(String vectorAdjacentMin) {
		this.vectorAdjacentMin = vectorAdjacentMin;
	}
	public String getVectorAdjacentMax() {
		return vectorAdjacentMax;
	}
	public void setVectorAdjacentMax(String vectorAdjacentMax) {
		this.vectorAdjacentMax = vectorAdjacentMax;
	}
	public String getVectorLocalMin() {
		return vectorLocalMin;
	}
	public void setVectorLocalMin(String vectorLocalMin) {
		this.vectorLocalMin = vectorLocalMin;
	}
	public String getVectorLocalMax() {
		return vectorLocalMax;
	}
	public void setVectorLocalMax(String vectorLocalMax) {
		this.vectorLocalMax = vectorLocalMax;
	}
	public String getVectorPhysicalMin() {
		return vectorPhysicalMin;
	}
	public void setVectorPhysicalMin(String vectorPhysicalMin) {
		this.vectorPhysicalMin = vectorPhysicalMin;
	}
	public String getVectorPhysicalMax() {
		return vectorPhysicalMax;
	}
	public void setVectorPhysicalMax(String vectorPhysicalMax) {
		this.vectorPhysicalMax = vectorPhysicalMax;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
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
		SubnetDTO other = (SubnetDTO) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		return true;
	}

	
}
