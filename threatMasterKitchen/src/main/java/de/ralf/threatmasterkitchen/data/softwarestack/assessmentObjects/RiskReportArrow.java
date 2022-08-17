package de.ralf.threatmasterkitchen.data.softwarestack.assessmentObjects;

public class RiskReportArrow{
	private int x0;
	private int y0;
	private int x1;
	private int y1;
	private int threat_id;
	private int suc_threat_id;
	private String kill_chain_name;
	private int kill_chain_index;
	
	public RiskReportArrow() {
		
	}
	
	public int getKill_chain_index() {
		return kill_chain_index;
	}



	public void setKill_chain_index(int kill_chain_index) {
		this.kill_chain_index = kill_chain_index;
	}



	public String getKill_chain_name() {
		return kill_chain_name;
	}

	public void setKill_chain_name(String kill_chain_name) {
		this.kill_chain_name = kill_chain_name;
	}

	public int getThreat_id() {
		return threat_id;
	}

	public void setThreat_id(int threat_id) {
		this.threat_id = threat_id;
	}

	public int getSuc_threat_id() {
		return suc_threat_id;
	}



	public void setSuc_threat_id(int suc_threat_id) {
		this.suc_threat_id = suc_threat_id;
	}



	public int getX0() {
		return x0;
	}
	public void setX0(int x0) {
		this.x0 = x0;
	}
	public int getY0() {
		return y0;
	}
	public void setY0(int y0) {
		this.y0 = y0;
	}
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x0;
		result = prime * result + x1;
		result = prime * result + y0;
		result = prime * result + y1;
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
		RiskReportArrow other = (RiskReportArrow) obj;
		if (x0 != other.x0)
			return false;
		if (x1 != other.x1)
			return false;
		if (y0 != other.y0)
			return false;
		if (y1 != other.y1)
			return false;
		return true;
	}
	
	
}
